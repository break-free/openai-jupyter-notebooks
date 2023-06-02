
package org.apache.fineract.infrastructure.core.api;
import java.util.Set;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.fineract.infrastructure.core.serialization.ApiRequestJsonSerializationSettings;
import org.springframework.stereotype.Component;
@Component
public class ApiRequestParameterHelper {
    public ApiRequestJsonSerializationSettings process(final MultivaluedMap<String, String> queryParameters,
            final Set<String> mandatoryResponseParameters) {
        final Set<String> responseParameters = ApiParameterHelper.extractFieldsForResponseIfProvided(queryParameters);
        if (!responseParameters.isEmpty()) {
            responseParameters.addAll(mandatoryResponseParameters);
        }
        final boolean prettyPrint = ApiParameterHelper.prettyPrint(queryParameters);
        final boolean template = ApiParameterHelper.template(queryParameters);
        final boolean makerCheckerable = ApiParameterHelper.makerCheckerable(queryParameters);
        final boolean includeJson = ApiParameterHelper.includeJson(queryParameters);
        return ApiRequestJsonSerializationSettings.from(prettyPrint, responseParameters, template, makerCheckerable, includeJson);
    }
    public ApiRequestJsonSerializationSettings process(final MultivaluedMap<String, String> queryParameters) {
        final Set<String> responseParameters = ApiParameterHelper.extractFieldsForResponseIfProvided(queryParameters);
        final boolean prettyPrint = ApiParameterHelper.prettyPrint(queryParameters);
        final boolean template = ApiParameterHelper.template(queryParameters);
        final boolean makerCheckerable = ApiParameterHelper.makerCheckerable(queryParameters);
        final boolean includeJson = ApiParameterHelper.includeJson(queryParameters);
        return ApiRequestJsonSerializationSettings.from(prettyPrint, responseParameters, template, makerCheckerable, includeJson);
    }
}
