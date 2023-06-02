
package org.apache.fineract.twofactortests;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.MessagingException;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
public class TwoFactorAuthenticationTest {
    private ResponseSpecification responseSpec;
    private ResponseSpecification responseSpec403;
    private ResponseSpecification responseSpec401;
    private RequestSpecification requestSpecWithoutBasic;
    private RequestSpecification requestSpec;
    private String basicAuthenticationKey;
    public static final String TENANT_PARAM_NAME = "tenantIdentifier";
    public static final String DEFAULT_TENANT = "default";
    public static final String TENANT_IDENTIFIER = TENANT_PARAM_NAME + '=' + DEFAULT_TENANT;
    private static final String LOGIN_URL = "/fineract-provider/api/v1/authentication?" + TENANT_IDENTIFIER;
    private static final String HEALTH_URL = "/fineract-provider/actuator/health";
    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("support@cloudmicrofinance.com", "support81"))
            .withPerMethodLifecycle(true);
    @BeforeEach
    public void setup() throws InterruptedException {
        initializeRestAssured();
        this.requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        awaitSpringBootActuatorHealthyUp();
        String json = RestAssured.given().contentType(ContentType.JSON).body("{\"username\":\"mifos\", \"password\":\"password\"}").expect()
                .log().ifError().when().post(LOGIN_URL).asString();
        assertFalse(StringUtils.isBlank(json));
        this.basicAuthenticationKey = JsonPath.with(json).get("base64EncodedAuthenticationKey");
        assertFalse(StringUtils.isBlank(this.basicAuthenticationKey));
        this.requestSpec.header("Authorization", "Basic " + basicAuthenticationKey);
        this.requestSpecWithoutBasic = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        this.responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
        this.responseSpec403 = new ResponseSpecBuilder().expectStatusCode(403).build();
        this.responseSpec401 = new ResponseSpecBuilder().expectStatusCode(401).build();
    }
    @Test
    public void testActuatorAccess() {
        performServerGet(requestSpecWithoutBasic, responseSpec, "/fineract-provider/actuator/info", null);
    }
    @Test
    public void testApiDocsAccess() {
        performServerGet(requestSpecWithoutBasic, responseSpec, "/fineract-provider/legacy-docs/apiLive.htm", null);
    }
    @Test
    public void testAccessWithoutTwofactor() {
        performServerGet(requestSpec, responseSpec403, "/fineract-provider/api/v1/offices/1?" + TENANT_IDENTIFIER, "");
    }
    @Test
    public void testCheckTwofactorEnabled() {
        String json = RestAssured.given().contentType(ContentType.JSON).body("{\"username\":\"mifos\", \"password\":\"password\"}").expect()
                .log().ifError().when().post("/fineract-provider/api/v1/authentication?" + TENANT_IDENTIFIER).asString();
        assertFalse(StringUtils.isBlank(json));
        Boolean key = JsonPath.with(json).get("isTwoFactorAuthenticationRequired");
        assertEquals(true, key);
    }
    @Test
    public void testGetTwofactorMethods() {
        String json = RestAssured.given().spec(requestSpec).expect().log().ifError().when()
                .get("/fineract-provider/api/v1/twofactor?" + TENANT_IDENTIFIER).asString();
        assertFalse(StringUtils.isBlank(json));
        List<HashMap<String, Object>> twoFactorMethods = JsonPath.with(json).getList("$");
        assertEquals("email", twoFactorMethods.get(0).get("name"));
        assertEquals("demomfi@mifos.org", twoFactorMethods.get(0).get("target"));
    }
    @Test
    public void testTwofactorLogin() throws IOException, MessagingException {
        assertEquals(greenMail.getReceivedMessages().length, 0);
        performServerPost(requestSpec, responseSpec,
                "/fineract-provider/api/v1/twofactor?deliveryMethod=email&extendedToken=false&" + TENANT_IDENTIFIER, "", "");
        assertEquals(greenMail.getReceivedMessages().length, 1);
        Pattern p = Pattern.compile("token is (.+).");
        Matcher m = p.matcher((CharSequence) greenMail.getReceivedMessages()[0].getContent());
        String token = null;
        while (m.find()) {
            token = m.group(1);
        }
        assertNotNull(token);
        String tfaToken = performServerPost(requestSpec, responseSpec,
                "/fineract-provider/api/v1/twofactor/validate?token=" + token + "&" + TENANT_IDENTIFIER, "", "token");
        assertNotNull(tfaToken);
        RequestSpecification requestSpecWithTFA = new RequestSpecBuilder() 
                .setContentType(ContentType.JSON) 
                .addHeader("Fineract-Platform-TFA-Token", tfaToken) 
                .addHeader("Authorization", "Basic " + basicAuthenticationKey) 
                .build();
        performServerGet(requestSpecWithTFA, responseSpec, "/fineract-provider/api/v1/offices/1?" + TENANT_IDENTIFIER, "");
        performServerPost(requestSpecWithTFA, responseSpec, "/fineract-provider/api/v1/twofactor/invalidate?" + TENANT_IDENTIFIER,
                "{ \"token\": \"" + tfaToken + "\" }", "");
        performServerGet(requestSpecWithTFA, responseSpec401, "/fineract-provider/api/v1/offices/1?" + TENANT_IDENTIFIER, "");
    }
    @Test
    public void testTfaConfigSettings() throws IOException, MessagingException {
        assertEquals(greenMail.getReceivedMessages().length, 0);
        performServerPost(requestSpec, responseSpec,
                "/fineract-provider/api/v1/twofactor?deliveryMethod=email&extendedToken=false&" + TENANT_IDENTIFIER, "", "");
        assertEquals(greenMail.getReceivedMessages().length, 1);
        Pattern p = Pattern.compile("token is (.+).");
        Matcher m = p.matcher((CharSequence) greenMail.getReceivedMessages()[0].getContent());
        String token = null;
        while (m.find()) {
            token = m.group(1);
        }
        assertNotNull(token);
        String tfaToken = performServerPost(requestSpec, responseSpec,
                "/fineract-provider/api/v1/twofactor/validate?token=" + token + "&" + TENANT_IDENTIFIER, "", "token");
        assertNotNull(tfaToken);
        RequestSpecification requestSpecWithTFA = new RequestSpecBuilder() 
                .setContentType(ContentType.JSON) 
                .addHeader("Fineract-Platform-TFA-Token", tfaToken) 
                .addHeader("Authorization", "Basic " + basicAuthenticationKey) 
                .build();
        LinkedHashMap<String, Object> json = performServerGet(requestSpecWithTFA, responseSpec,
                "/fineract-provider/api/v1/twofactor/configure?" + TENANT_IDENTIFIER, "");
        assertEquals(json.get("otp-token-length"), token.length());
        performServerPut(requestSpecWithTFA, responseSpec, "/fineract-provider/api/v1/twofactor/configure?" + TENANT_IDENTIFIER,
                "{\"otp-token-length\": 10 }", "");
        performServerPost(requestSpecWithTFA, responseSpec, "/fineract-provider/api/v1/twofactor/invalidate?" + TENANT_IDENTIFIER,
                "{ \"token\": \"" + tfaToken + "\" }", "");
        performServerPost(requestSpec, responseSpec,
                "/fineract-provider/api/v1/twofactor?deliveryMethod=email&extendedToken=false&" + TENANT_IDENTIFIER, "", "");
        assertEquals(greenMail.getReceivedMessages().length, 2);
        Matcher m2 = p.matcher((CharSequence) greenMail.getReceivedMessages()[1].getContent());
        String token2 = null;
        while (m2.find()) {
            token2 = m2.group(1);
        }
        assertNotNull(token2);
        assertEquals(10, token2.length());
        tfaToken = performServerPost(requestSpec, responseSpec,
                "/fineract-provider/api/v1/twofactor/validate?token=" + token2 + "&" + TENANT_IDENTIFIER, "", "token");
        assertNotNull(tfaToken);
        requestSpecWithTFA = new RequestSpecBuilder() 
                .setContentType(ContentType.JSON) 
                .addHeader("Fineract-Platform-TFA-Token", tfaToken) 
                .addHeader("Authorization", "Basic " + basicAuthenticationKey) 
                .build();
        json = performServerGet(requestSpecWithTFA, responseSpec, "/fineract-provider/api/v1/twofactor/configure?" + TENANT_IDENTIFIER, "");
        assertEquals(json.get("otp-token-length"), token2.length());
        performServerPut(requestSpecWithTFA, responseSpec, "/fineract-provider/api/v1/twofactor/configure?" + TENANT_IDENTIFIER,
                "{\"otp-token-length\": " + token.length() + "}", "");
        json = performServerGet(requestSpecWithTFA, responseSpec, "/fineract-provider/api/v1/twofactor/configure?" + TENANT_IDENTIFIER, "");
        assertEquals(json.get("otp-token-length"), token.length());
        performServerPost(requestSpecWithTFA, responseSpec, "/fineract-provider/api/v1/twofactor/invalidate?" + TENANT_IDENTIFIER,
                "{ \"token\": \"" + tfaToken + "\" }", "");
    }
    private static void initializeRestAssured() {
        RestAssured.baseURI = "https:
        RestAssured.port = 8443;
        RestAssured.keyStore("src/main/resources/keystore.jks", "openmf");
        RestAssured.useRelaxedHTTPSValidation();
    }
    private static void awaitSpringBootActuatorHealthyUp() throws InterruptedException {
        int attempt = 0;
        final int max_attempts = 10;
        Response response = null;
        do {
            try {
                response = RestAssured.get(HEALTH_URL);
                if (response.statusCode() == 200) {
                    return;
                }
                Thread.sleep(3000);
            } catch (Exception e) {
                Thread.sleep(3000);
            }
        } while (attempt < max_attempts);
        fail(HEALTH_URL + " returned " + response.prettyPrint());
    }
    @SuppressWarnings("unchecked")
    private static <T> T performServerGet(final RequestSpecification requestSpec, final ResponseSpecification responseSpec,
            final String getURL, final String jsonAttributeToGetBack) {
        final String json = given().spec(requestSpec).expect().spec(responseSpec).log().ifError().when().get(getURL).andReturn().asString();
        if (jsonAttributeToGetBack == null) {
            return (T) json;
        }
        return (T) JsonPath.from(json).get(jsonAttributeToGetBack);
    }
    @SuppressWarnings("unchecked")
    private static <T> T performServerPost(final RequestSpecification requestSpec, final ResponseSpecification responseSpec,
            final String postURL, final String jsonBodyToSend, final String jsonAttributeToGetBack) {
        final String json = given().spec(requestSpec).body(jsonBodyToSend).expect().spec(responseSpec).log().ifError().when().post(postURL)
                .andReturn().asString();
        if (jsonAttributeToGetBack == null) {
            return (T) json;
        }
        return (T) JsonPath.from(json).get(jsonAttributeToGetBack);
    }
    @SuppressWarnings("unchecked")
    public static <T> T performServerPut(final RequestSpecification requestSpec, final ResponseSpecification responseSpec,
            final String putURL, final String jsonBodyToSend, final String jsonAttributeToGetBack) {
        final String json = given().spec(requestSpec).body(jsonBodyToSend).expect().spec(responseSpec).log().ifError().when().put(putURL)
                .andReturn().asString();
        return (T) JsonPath.from(json).get(jsonAttributeToGetBack);
    }
}
