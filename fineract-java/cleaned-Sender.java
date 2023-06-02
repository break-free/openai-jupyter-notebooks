
package org.apache.fineract.infrastructure.gcm.domain;
import static org.apache.fineract.infrastructure.gcm.GcmConstants.JSON_CANONICAL_IDS;
import static org.apache.fineract.infrastructure.gcm.GcmConstants.JSON_ERROR;
import static org.apache.fineract.infrastructure.gcm.GcmConstants.JSON_FAILURE;
import static org.apache.fineract.infrastructure.gcm.GcmConstants.JSON_MESSAGE_ID;
import static org.apache.fineract.infrastructure.gcm.GcmConstants.JSON_MULTICAST_ID;
import static org.apache.fineract.infrastructure.gcm.GcmConstants.JSON_NOTIFICATION;
import static org.apache.fineract.infrastructure.gcm.GcmConstants.JSON_NOTIFICATION_BADGE;
import static org.apache.fineract.infrastructure.gcm.GcmConstants.JSON_NOTIFICATION_BODY;
import static org.apache.fineract.infrastructure.gcm.GcmConstants.JSON_NOTIFICATION_BODY_LOC_ARGS;
import static org.apache.fineract.infrastructure.gcm.GcmConstants.JSON_NOTIFICATION_BODY_LOC_KEY;
import static org.apache.fineract.infrastructure.gcm.GcmConstants.JSON_NOTIFICATION_CLICK_ACTION;
import static org.apache.fineract.infrastructure.gcm.GcmConstants.JSON_NOTIFICATION_COLOR;
import static org.apache.fineract.infrastructure.gcm.GcmConstants.JSON_NOTIFICATION_ICON;
import static org.apache.fineract.infrastructure.gcm.GcmConstants.JSON_NOTIFICATION_SOUND;
import static org.apache.fineract.infrastructure.gcm.GcmConstants.JSON_NOTIFICATION_TAG;
import static org.apache.fineract.infrastructure.gcm.GcmConstants.JSON_NOTIFICATION_TITLE;
import static org.apache.fineract.infrastructure.gcm.GcmConstants.JSON_NOTIFICATION_TITLE_LOC_ARGS;
import static org.apache.fineract.infrastructure.gcm.GcmConstants.JSON_NOTIFICATION_TITLE_LOC_KEY;
import static org.apache.fineract.infrastructure.gcm.GcmConstants.JSON_PAYLOAD;
import static org.apache.fineract.infrastructure.gcm.GcmConstants.JSON_REGISTRATION_IDS;
import static org.apache.fineract.infrastructure.gcm.GcmConstants.JSON_RESULTS;
import static org.apache.fineract.infrastructure.gcm.GcmConstants.JSON_SUCCESS;
import static org.apache.fineract.infrastructure.gcm.GcmConstants.JSON_TO;
import static org.apache.fineract.infrastructure.gcm.GcmConstants.PARAM_COLLAPSE_KEY;
import static org.apache.fineract.infrastructure.gcm.GcmConstants.PARAM_CONTENT_AVAILABLE;
import static org.apache.fineract.infrastructure.gcm.GcmConstants.PARAM_DELAY_WHILE_IDLE;
import static org.apache.fineract.infrastructure.gcm.GcmConstants.PARAM_DRY_RUN;
import static org.apache.fineract.infrastructure.gcm.GcmConstants.PARAM_PRIORITY;
import static org.apache.fineract.infrastructure.gcm.GcmConstants.PARAM_RESTRICTED_PACKAGE_NAME;
import static org.apache.fineract.infrastructure.gcm.GcmConstants.PARAM_TIME_TO_LIVE;
import static org.apache.fineract.infrastructure.gcm.GcmConstants.TOKEN_CANONICAL_REG_ID;
import static org.apache.fineract.infrastructure.gcm.GcmConstants.TOPIC_PREFIX;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.fineract.infrastructure.gcm.GcmConstants;
import org.apache.fineract.infrastructure.gcm.exception.InvalidRequestException;
public class Sender {
    protected static final int BACKOFF_INITIAL_DELAY = 1000;
    protected static final int MAX_BACKOFF_DELAY = 1024000;
    private static final SecureRandom random = new SecureRandom();
    protected static final Logger LOG = Logger.getLogger(Sender.class.getName());
    private final String key;
    private String endpoint;
    private int connectTimeout;
    private int readTimeout;
    public Sender(String key, String endpoint) {
        this.key = nonNull(key);
        this.endpoint = nonNull(endpoint);
    }
    public String getEndpoint() {
        return endpoint;
    }
    public final void setConnectTimeout(int connectTimeout) {
        if (connectTimeout < 0) {
            throw new IllegalArgumentException("timeout can not be negative");
        }
        this.connectTimeout = connectTimeout;
    }
    public final void setReadTimeout(int readTimeout) {
        if (readTimeout < 0) {
            throw new IllegalArgumentException("timeout can not be negative");
        }
        this.readTimeout = readTimeout;
    }
    @SuppressFBWarnings(value = {
            "DMI_RANDOM_USED_ONLY_ONCE" }, justification = "False positive for random object created and used only once")
    public Result send(Message message, String to, int retries) throws IOException {
        int attempt = 0;
        Result result;
        int backoff = BACKOFF_INITIAL_DELAY;
        boolean tryAgain;
        do {
            attempt++;
            if (LOG.isLoggable(Level.FINE)) {
                LOG.fine("Attempt #" + attempt + " to send message " + message + " to regIds " + to);
            }
            result = sendNoRetry(message, to);
            tryAgain = result == null && attempt <= retries;
            if (tryAgain) {
                int sleepTime = backoff / 2 + random.nextInt(backoff);
                sleep(sleepTime);
                if (2 * backoff < MAX_BACKOFF_DELAY) {
                    backoff *= 2;
                }
            }
        } while (tryAgain);
        if (result == null) {
            throw new IOException("Could not send message after " + attempt + " attempts");
        }
        return result;
    }
    public Result sendNoRetry(Message message, String to) throws IOException {
        nonNull(to);
        Map<Object, Object> jsonRequest = new HashMap<>();
        messageToMap(message, jsonRequest);
        jsonRequest.put(JSON_TO, to);
        Map<String, Object> responseMap = makeGcmHttpRequest(jsonRequest);
        String responseBody = null;
        if (responseMap.get("responseBody") != null) {
            responseBody = (String) responseMap.get("responseBody");
        }
        int status = (int) responseMap.get("status");
        if (responseBody == null) {
            return null;
        }
        JsonObject jsonResponse;
        try {
            jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
            Result.Builder resultBuilder = new Result.Builder();
            if (jsonResponse.has("results")) {
                JsonArray jsonResults = (JsonArray) jsonResponse.get("results");
                if (jsonResults.size() == 1) {
                    JsonObject jsonResult = (JsonObject) jsonResults.get(0);
                    String messageId = null;
                    String canonicalRegId = null;
                    String error = null;
                    if (jsonResult.has(JSON_MESSAGE_ID)) {
                        messageId = jsonResult.get(JSON_MESSAGE_ID).getAsString();
                    }
                    if (jsonResult.has(TOKEN_CANONICAL_REG_ID)) {
                        canonicalRegId = jsonResult.get(TOKEN_CANONICAL_REG_ID).getAsString();
                    }
                    if (jsonResult.has(JSON_ERROR)) {
                        error = jsonResult.get(JSON_ERROR).getAsString();
                    }
                    int success = 0;
                    int failure = 0;
                    if (jsonResponse.get("success") != null) {
                        success = Integer.parseInt(jsonResponse.get("success").toString());
                    }
                    if (jsonResponse.get("failure") != null) {
                        failure = Integer.parseInt(jsonResponse.get("failure").toString());
                    }
                    resultBuilder.messageId(messageId).canonicalRegistrationId(canonicalRegId).success(success).failure(failure)
                            .status(status).errorCode(error);
                } else {
                    LOG.log(Level.WARNING, "Found null or " + jsonResults.size() + " results, expected one");
                    return null;
                }
            } else if (to.startsWith(TOPIC_PREFIX)) {
                if (jsonResponse.has(JSON_MESSAGE_ID)) {
                    Long messageId = jsonResponse.get(JSON_MESSAGE_ID).getAsLong();
                    resultBuilder.messageId(messageId.toString());
                } else if (jsonResponse.has(JSON_ERROR)) {
                    String error = jsonResponse.get(JSON_ERROR).getAsString();
                    resultBuilder.errorCode(error);
                } else {
                    LOG.log(Level.WARNING, "Expected " + JSON_MESSAGE_ID + " or " + JSON_ERROR + " found: " + responseBody);
                    return null;
                }
            } else if (jsonResponse.has(JSON_SUCCESS) && jsonResponse.has(JSON_FAILURE)) {
                int success = getNumber(responseMap, JSON_SUCCESS).intValue();
                int failure = getNumber(responseMap, JSON_FAILURE).intValue();
                List<String> failedIds = null;
                if (jsonResponse.has("failed_registration_ids")) {
                    JsonArray jFailedIds = jsonResponse.get("failed_registration_ids").getAsJsonArray();
                    failedIds = new ArrayList<>();
                    for (int i = 0; i < jFailedIds.size(); i++) {
                        failedIds.add(jFailedIds.get(i).getAsString());
                    }
                }
                resultBuilder.success(success).failure(failure).failedRegistrationIds(failedIds);
            } else {
                LOG.warning("Unrecognized response: " + responseBody);
                throw newIoException(responseBody, new Exception("Unrecognized response."));
            }
            return resultBuilder.build();
        } catch (CustomParserException e) {
            throw newIoException(responseBody, e);
        }
    }
    @SuppressFBWarnings(value = {
            "DMI_RANDOM_USED_ONLY_ONCE" }, justification = "False positive for random object created and used only once")
    public MulticastResult send(Message message, List<String> regIds, int retries) throws IOException {
        int attempt = 0;
        MulticastResult multicastResult;
        int backoff = BACKOFF_INITIAL_DELAY;
        Map<String, Result> results = new HashMap<>();
        List<String> unsentRegIds = new ArrayList<>(regIds);
        boolean tryAgain;
        List<Long> multicastIds = new ArrayList<>();
        do {
            multicastResult = null;
            attempt++;
            if (LOG.isLoggable(Level.FINE)) {
                LOG.fine("Attempt #" + attempt + " to send message " + message + " to regIds " + unsentRegIds);
            }
            try {
                multicastResult = sendNoRetry(message, unsentRegIds);
            } catch (IOException e) {
                LOG.log(Level.FINEST, "IOException on attempt " + attempt, e);
            }
            if (multicastResult != null) {
                long multicastId = multicastResult.getMulticastId();
                LOG.fine("multicast_id on attempt # " + attempt + ": " + multicastId);
                multicastIds.add(multicastId);
                unsentRegIds = updateStatus(unsentRegIds, results, multicastResult);
                tryAgain = !unsentRegIds.isEmpty() && attempt <= retries;
            } else {
                tryAgain = attempt <= retries;
            }
            if (tryAgain) {
                int sleepTime = backoff / 2 + random.nextInt(backoff);
                sleep(sleepTime);
                if (2 * backoff < MAX_BACKOFF_DELAY) {
                    backoff *= 2;
                }
            }
        } while (tryAgain);
        if (multicastIds.isEmpty()) {
            throw new IOException("Could not post JSON requests to GCM after " + attempt + " attempts");
        }
        int success = 0;
        int failure = 0;
        int canonicalIds = 0;
        for (Result result : results.values()) {
            if (result.getMessageId() != null) {
                success++;
                if (result.getCanonicalRegistrationId() != null) {
                    canonicalIds++;
                }
            } else {
                failure++;
            }
        }
        long multicastId = multicastIds.remove(0);
        MulticastResult.Builder builder = new MulticastResult.Builder(success, failure, canonicalIds, multicastId)
                .retryMulticastIds(multicastIds);
        for (String regId : regIds) {
            Result result = results.get(regId);
            builder.addResult(result);
        }
        return builder.build();
    }
    private List<String> updateStatus(List<String> unsentRegIds, Map<String, Result> allResults, MulticastResult multicastResult) {
        List<Result> results = multicastResult.getResults();
        if (results.size() != unsentRegIds.size()) {
            throw new RuntimeException(
                    "Internal error: sizes do not match. " + "currentResults: " + results + "; unsentRegIds: " + unsentRegIds);
        }
        List<String> newUnsentRegIds = new ArrayList<>();
        for (int i = 0; i < unsentRegIds.size(); i++) {
            String regId = unsentRegIds.get(i);
            Result result = results.get(i);
            allResults.put(regId, result);
            String error = result.getErrorCodeName();
            if (error != null && (error.equals(GcmConstants.ERROR_UNAVAILABLE) || error.equals(GcmConstants.ERROR_INTERNAL_SERVER_ERROR))) {
                newUnsentRegIds.add(regId);
            }
        }
        return newUnsentRegIds;
    }
    public MulticastResult sendNoRetry(Message message, List<String> registrationIds) throws IOException {
        if (nonNull(registrationIds).isEmpty()) {
            throw new IllegalArgumentException("registrationIds cannot be empty");
        }
        Map<Object, Object> jsonRequest = new HashMap<>();
        messageToMap(message, jsonRequest);
        jsonRequest.put(JSON_REGISTRATION_IDS, registrationIds);
        Map<String, Object> responseMap = makeGcmHttpRequest(jsonRequest);
        String responseBody = null;
        if (responseMap.get("responseBody") != null) {
            responseBody = (String) responseMap.get("responseBody");
        }
        if (responseBody == null) {
            return null;
        }
        JsonObject jsonResponse;
        try {
            jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
            int success = getNumber(responseMap, JSON_SUCCESS).intValue();
            int failure = getNumber(responseMap, JSON_FAILURE).intValue();
            int canonicalIds = getNumber(responseMap, JSON_CANONICAL_IDS).intValue();
            long multicastId = getNumber(responseMap, JSON_MULTICAST_ID).longValue();
            MulticastResult.Builder builder = new MulticastResult.Builder(success, failure, canonicalIds, multicastId);
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> results = (List<Map<String, Object>>) jsonResponse.get(JSON_RESULTS);
            if (results != null) {
                for (Map<String, Object> jsonResult : results) {
                    String messageId = (String) jsonResult.get(JSON_MESSAGE_ID);
                    String canonicalRegId = (String) jsonResult.get(TOKEN_CANONICAL_REG_ID);
                    String error = (String) jsonResult.get(JSON_ERROR);
                    Result result = new Result.Builder().messageId(messageId).canonicalRegistrationId(canonicalRegId).errorCode(error)
                            .build();
                    builder.addResult(result);
                }
            }
            return builder.build();
        } catch (CustomParserException e) {
            throw newIoException(responseBody, e);
        }
    }
    private Map<String, Object> makeGcmHttpRequest(Map<Object, Object> jsonRequest) throws InvalidRequestException {
        String requestBody = new Gson().toJson(jsonRequest);
        LOG.finest("JSON request: " + requestBody);
        HttpURLConnection conn;
        int status;
        try {
            conn = post(getEndpoint(), "application/json", requestBody);
            status = conn.getResponseCode();
        } catch (IOException e) {
            LOG.log(Level.FINE, "IOException posting to GCM", e);
            return null;
        }
        String responseBody;
        if (status != 200) {
            try {
                responseBody = getAndClose(conn.getErrorStream());
                LOG.finest("JSON error response: " + responseBody);
            } catch (IOException e) {
                responseBody = "N/A";
                LOG.log(Level.FINE, "Exception reading response: ", e);
            }
            throw new InvalidRequestException(status, responseBody);
        }
        try {
            responseBody = getAndClose(conn.getInputStream());
        } catch (IOException e) {
            LOG.log(Level.WARNING, "IOException reading response", e);
            return null;
        }
        LOG.finest("JSON response: " + responseBody);
        Map<String, Object> map = new HashMap<>();
        map.put("responseBody", responseBody);
        map.put("status", status);
        return map;
    }
    private void messageToMap(Message message, Map<Object, Object> mapRequest) {
        if (message == null || mapRequest == null) {
            return;
        }
        setJsonField(mapRequest, PARAM_PRIORITY, message.getPriority());
        setJsonField(mapRequest, PARAM_CONTENT_AVAILABLE, message.getContentAvailable());
        setJsonField(mapRequest, PARAM_TIME_TO_LIVE, message.getTimeToLive());
        setJsonField(mapRequest, PARAM_COLLAPSE_KEY, message.getCollapseKey());
        setJsonField(mapRequest, PARAM_RESTRICTED_PACKAGE_NAME, message.getRestrictedPackageName());
        setJsonField(mapRequest, PARAM_DELAY_WHILE_IDLE, message.isDelayWhileIdle());
        setJsonField(mapRequest, PARAM_DRY_RUN, message.isDryRun());
        Map<String, String> payload = message.getData();
        if (!payload.isEmpty()) {
            mapRequest.put(JSON_PAYLOAD, payload);
        }
        if (message.getNotification() != null) {
            Notification notification = message.getNotification();
            Map<Object, Object> nMap = new HashMap<>();
            if (notification.getBadge() != null) {
                setJsonField(nMap, JSON_NOTIFICATION_BADGE, notification.getBadge().toString());
            }
            setJsonField(nMap, JSON_NOTIFICATION_BODY, notification.getBody());
            setJsonField(nMap, JSON_NOTIFICATION_BODY_LOC_ARGS, notification.getBodyLocArgs());
            setJsonField(nMap, JSON_NOTIFICATION_BODY_LOC_KEY, notification.getBodyLocKey());
            setJsonField(nMap, JSON_NOTIFICATION_CLICK_ACTION, notification.getClickAction());
            setJsonField(nMap, JSON_NOTIFICATION_COLOR, notification.getColor());
            setJsonField(nMap, JSON_NOTIFICATION_ICON, notification.getIcon());
            setJsonField(nMap, JSON_NOTIFICATION_SOUND, notification.getSound());
            setJsonField(nMap, JSON_NOTIFICATION_TAG, notification.getTag());
            setJsonField(nMap, JSON_NOTIFICATION_TITLE, notification.getTitle());
            setJsonField(nMap, JSON_NOTIFICATION_TITLE_LOC_ARGS, notification.getTitleLocArgs());
            setJsonField(nMap, JSON_NOTIFICATION_TITLE_LOC_KEY, notification.getTitleLocKey());
            mapRequest.put(JSON_NOTIFICATION, nMap);
        }
    }
    private IOException newIoException(String responseBody, Exception e) {
        String msg = "Error parsing JSON response (" + responseBody + ")";
        LOG.log(Level.WARNING, msg, e);
        return new IOException(msg + ":" + e);
    }
    private static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                LOG.log(Level.FINEST, "IOException closing stream", e);
            }
        }
    }
    private void setJsonField(Map<Object, Object> json, String field, Object value) {
        if (value != null) {
            json.put(field, value);
        }
    }
    private Number getNumber(Map<?, ?> json, String field) {
        Object value = json.get(field);
        if (value == null) {
            throw new CustomParserException("Missing field: " + field);
        }
        if (!(value instanceof Number)) {
            throw new CustomParserException("Field " + field + " does not contain a number: " + value);
        }
        return (Number) value;
    }
    static class CustomParserException extends RuntimeException {
        CustomParserException(String message) {
            super(message);
        }
    }
    protected HttpURLConnection post(String url, String body) throws IOException {
        return post(url, "application/x-www-form-urlencoded;charset=UTF-8", body);
    }
    protected HttpURLConnection post(String url, String contentType, String body) throws IOException {
        if (url == null || contentType == null || body == null) {
            throw new IllegalArgumentException("arguments cannot be null");
        }
        if (!url.startsWith("https:
            LOG.warning("URL does not use https: " + url);
        }
        LOG.fine("Sending POST to " + url);
        LOG.finest("POST body: " + body);
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        HttpURLConnection conn = getConnection(url);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setFixedLengthStreamingMode(Long.valueOf(bytes.length));
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", contentType);
        conn.setRequestProperty("Authorization", "key=" + key);
        OutputStream out = conn.getOutputStream();
        try {
            out.write(bytes);
        } finally {
            close(out);
        }
        return conn;
    }
    protected static final Map<String, String> newKeyValues(String key, String value) {
        Map<String, String> keyValues = new HashMap<>(1);
        keyValues.put(nonNull(key), nonNull(value));
        return keyValues;
    }
    protected static StringBuilder newBody(String name, String value) {
        return new StringBuilder(nonNull(name)).append('=').append(nonNull(value));
    }
    protected static void addParameter(StringBuilder body, String name, String value) {
        nonNull(body).append('&').append(nonNull(name)).append('=').append(nonNull(value));
    }
    protected HttpURLConnection getConnection(String url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setConnectTimeout(connectTimeout);
        conn.setReadTimeout(readTimeout);
        return conn;
    }
    protected static String getString(InputStream stream) throws IOException {
        if (stream == null) {
            return "";
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        StringBuilder content = new StringBuilder();
        String newLine;
        do {
            newLine = reader.readLine();
            if (newLine != null) {
                content.append(newLine).append('\n');
            }
        } while (newLine != null);
        if (content.length() > 0) {
            content.setLength(content.length() - 1);
        }
        return content.toString();
    }
    private static String getAndClose(InputStream stream) throws IOException {
        try {
            return getString(stream);
        } finally {
            if (stream != null) {
                close(stream);
            }
        }
    }
    static <T> T nonNull(T argument) {
        if (argument == null) {
            throw new IllegalArgumentException("argument cannot be null");
        }
        return argument;
    }
    void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
