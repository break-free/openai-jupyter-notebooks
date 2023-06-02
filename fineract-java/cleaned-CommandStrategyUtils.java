
package org.apache.fineract.batch.command;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.infrastructure.core.api.MutableUriInfo;
public final class CommandStrategyUtils {
    private CommandStrategyUtils() {
    }
    public static Map<String, String> getQueryParameters(final String relativeUrl) {
        final String queryParameterStr = StringUtils.substringAfter(relativeUrl, "?");
        final String[] queryParametersArray = StringUtils.split(queryParameterStr, "&");
        final Map<String, String> queryParametersMap = new HashMap<>();
        for (String parameterStr : queryParametersArray) {
            String[] keyValue = StringUtils.split(parameterStr, "=");
            queryParametersMap.put(keyValue[0], keyValue[1]);
        }
        return queryParametersMap;
    }
    public static void addQueryParametersToUriInfo(final MutableUriInfo uriInfo, final Map<String, String> queryParameters) {
        for (Map.Entry<String, String> entry : queryParameters.entrySet()) {
            uriInfo.addAdditionalQueryParameter(entry.getKey(), entry.getValue());
        }
    }
}
