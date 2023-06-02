
package org.apache.fineract.integrationtests.common;
import com.google.gson.Gson;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.ArrayList;
import java.util.HashMap;
public class ExternalServicesConfigurationHelper {
    private final RequestSpecification requestSpec;
    private final ResponseSpecification responseSpec;
    public ExternalServicesConfigurationHelper(final RequestSpecification requestSpec, final ResponseSpecification responseSpec) {
        this.requestSpec = requestSpec;
        this.responseSpec = responseSpec;
    }
    public static ArrayList<HashMap> getExternalServicesConfigurationByServiceName(final RequestSpecification requestSpec,
            final ResponseSpecification responseSpec, final String serviceName) {
        final String GET_EXTERNAL_SERVICES_CONFIG_BY_SERVICE_NAME_URL = "/fineract-provider/api/v1/externalservice/" + serviceName + "?"
                + Utils.TENANT_IDENTIFIER;
        return Utils.performServerGet(requestSpec, responseSpec, GET_EXTERNAL_SERVICES_CONFIG_BY_SERVICE_NAME_URL, "");
    }
    public static HashMap updateValueForExternaServicesConfiguration(final RequestSpecification requestSpec,
            final ResponseSpecification responseSpec, final String serviceName, final String name, final String value) {
        final String EXTERNAL_SERVICES_CONFIG_UPDATE_URL = "/fineract-provider/api/v1/externalservice/" + serviceName + "?"
                + Utils.TENANT_IDENTIFIER;
        HashMap map = Utils.performServerPut(requestSpec, responseSpec, EXTERNAL_SERVICES_CONFIG_UPDATE_URL,
                updateExternalServicesConfigUpdateValueAsJSON(name, value), "");
        return (HashMap) map.get("changes");
    }
    public static String updateExternalServicesConfigUpdateValueAsJSON(final String name, final String value) {
        final HashMap<String, String> map = new HashMap<>();
        map.put(name, value);
        return new Gson().toJson(map);
    }
}
