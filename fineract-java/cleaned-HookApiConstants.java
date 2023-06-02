
package org.apache.fineract.infrastructure.hooks.api;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
public final class HookApiConstants {
    private HookApiConstants() {}
    public static final String HOOK_RESOURCE_NAME = "HOOK";
    public static final String nameParamName = "name";
    public static final String displayNameParamName = "displayName";
    public static final String isActiveParamName = "isActive";
    public static final String webTemplateName = "Web";
    public static final String elasticSearchTemplateName = "Elastic Search";
    public static final String httpSMSTemplateName = "Message Gateway";
    public static final String smsTemplateName = "SMS Bridge";
    public static final String payloadURLName = "Payload URL";
    public static final String contentTypeName = "Content Type";
    public static final String smsProviderName = "SMS Provider";
    public static final String smsProviderAccountIdName = "SMS Provider Account Id";
    public static final String smsProviderTokenIdName = "SMS Provider Token";
    public static final String phoneNumberName = "Phone Number";
    public static final String apiKeyName = "Api Key";
    public static final String configParamName = "config";
    public static final String eventsParamName = "events";
    public static final String entityNameParamName = "entityName";
    public static final String actionNameParamName = "actionName";
    public static final String templateIdParamName = "templateId";
    public static final String templateNameParamName = "templateName";
    public static final String SMSProviderIdParamName = "SMS Provider Id";
    public static final Set<String> RESPONSE_DATA_PARAMETERS = new HashSet<>(Arrays.asList(nameParamName, displayNameParamName,
            templateIdParamName, isActiveParamName, configParamName, eventsParamName, templateNameParamName));
    public static final Set<String> UPDATE_REQUEST_DATA_PARAMETERS = new HashSet<>(Arrays.asList(nameParamName, displayNameParamName,
            templateIdParamName, isActiveParamName, configParamName, eventsParamName, templateNameParamName));
}
