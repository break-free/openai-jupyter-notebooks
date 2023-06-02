
package org.apache.fineract.infrastructure.core.serialization;
import java.util.Set;
public class ApiRequestJsonSerializationSettings {
    private final boolean prettyPrint;
    private final Set<String> parametersForPartialResponse;
    private final boolean template;
    private final boolean makerCheckerable;
    private final boolean includeJson;
    public ApiRequestJsonSerializationSettings(final boolean prettyPrint, final Set<String> parametersForPartialResponse,
            final boolean template, final boolean makerCheckerable, final boolean includeJson) {
        this.prettyPrint = prettyPrint;
        this.parametersForPartialResponse = parametersForPartialResponse;
        this.template = template;
        this.makerCheckerable = makerCheckerable;
        this.includeJson = includeJson;
    }
    public static ApiRequestJsonSerializationSettings from(final boolean prettyPrint, final Set<String> parametersForPartialResponse,
            final boolean template, final boolean makerCheckerable, final boolean includeJson) {
        return new ApiRequestJsonSerializationSettings(prettyPrint, parametersForPartialResponse, template, makerCheckerable, includeJson);
    }
    public boolean isPrettyPrint() {
        return this.prettyPrint;
    }
    public boolean isTemplate() {
        return this.template;
    }
    public boolean isMakerCheckerable() {
        return this.makerCheckerable;
    }
    public boolean isIncludeJson() {
        return this.includeJson;
    }
    public Set<String> getParametersForPartialResponse() {
        return this.parametersForPartialResponse;
    }
    public boolean isPartialResponseRequired() {
        return !this.parametersForPartialResponse.isEmpty();
    }
}
