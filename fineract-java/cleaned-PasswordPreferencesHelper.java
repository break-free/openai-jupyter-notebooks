
package org.apache.fineract.integrationtests.common;
import com.google.gson.Gson;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.HashMap;
public final class PasswordPreferencesHelper {
    private PasswordPreferencesHelper() {
    }
    private static final String PASSWORD_PREFERENCES_URL = "/fineract-provider/api/v1/passwordpreferences";
    public static Object updatePasswordPreferences(final RequestSpecification requestSpec, final ResponseSpecification responseSpec,
            String validationPolicyId) {
        final String UPDATE_PASSWORD_PREFERENCES_URL = PASSWORD_PREFERENCES_URL + "?" + Utils.TENANT_IDENTIFIER;
        return Utils.performServerPut(requestSpec, responseSpec, UPDATE_PASSWORD_PREFERENCES_URL,
                updatePreferencesAsJson(validationPolicyId), "");
    }
    public static Object updateWithInvalidValidationPolicyId(final RequestSpecification requestSpec,
            final ResponseSpecification responseSpec, String invalidValidationPolicyId, String jsonAttributeToGetback) {
        final String UPDATE_PASSWORD_PREFERENCES_URL = PASSWORD_PREFERENCES_URL + "?" + Utils.TENANT_IDENTIFIER;
        return Utils.performServerPut(requestSpec, responseSpec, UPDATE_PASSWORD_PREFERENCES_URL,
                updatePreferencesWithInvalidId(invalidValidationPolicyId), jsonAttributeToGetback);
    }
    public static String updatePreferencesAsJson(String validationPolicyId) {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("validationPolicyId", validationPolicyId);
        return new Gson().toJson(map);
    }
    public static String updatePreferencesWithInvalidId(String invalidValidationPolicyId) {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("validationPolicyId", invalidValidationPolicyId);
        return new Gson().toJson(map);
    }
    public static int getActivePasswordPreference(final RequestSpecification requestSpec, final ResponseSpecification responseSpec) {
        return Utils.performServerGet(requestSpec, responseSpec, PASSWORD_PREFERENCES_URL + "?" + Utils.TENANT_IDENTIFIER, "id");
    }
    public static HashMap<String, Object> getAllPreferences(final RequestSpecification requestSpec,
            final ResponseSpecification responseSpec) {
        return Utils.performServerGet(requestSpec, responseSpec, PASSWORD_PREFERENCES_URL + "/template" + "?" + Utils.TENANT_IDENTIFIER,
                "");
    }
}
