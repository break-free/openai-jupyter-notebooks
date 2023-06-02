
package org.apache.fineract.integrationtests.common.shares;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.Map;
import org.apache.fineract.integrationtests.common.Utils;
public final class ShareAccountTransactionHelper {
    private ShareAccountTransactionHelper() {
    }
    private static final String SHARE_ACCOUNT_URL = "/fineract-provider/api/v1/accounts/share";
    private static final String CREATE_SHARE_ACCOUNT_URL = SHARE_ACCOUNT_URL + "?" + Utils.TENANT_IDENTIFIER;
    public static Integer createShareAccount(final String shareProductJSON, final RequestSpecification requestSpec,
            final ResponseSpecification responseSpec) {
        return Utils.performServerPost(requestSpec, responseSpec, CREATE_SHARE_ACCOUNT_URL, shareProductJSON, "resourceId");
    }
    public static Map<String, Object> retrieveShareAccount(final Integer shareProductId, final RequestSpecification requestSpec,
            final ResponseSpecification responseSpec) {
        String url = SHARE_ACCOUNT_URL + "/" + shareProductId + "?" + Utils.TENANT_IDENTIFIER;
        return Utils.performServerGet(requestSpec, responseSpec, url, "");
    }
    public static Integer updateShareAccount(final Integer shareAccountId, final String shareAccountJson,
            final RequestSpecification requestSpec, final ResponseSpecification responseSpec) {
        String url = SHARE_ACCOUNT_URL + "/" + shareAccountId + "?" + Utils.TENANT_IDENTIFIER;
        return Utils.performServerPut(requestSpec, responseSpec, url, shareAccountJson, "resourceId");
    }
    public static Integer postCommand(final String command, final Integer shareAccountId, String jsonBody,
            final RequestSpecification requestSpec, final ResponseSpecification responseSpec) {
        String url = SHARE_ACCOUNT_URL + "/" + shareAccountId + "?command=" + command + "&" + Utils.TENANT_IDENTIFIER;
        return Utils.performServerPost(requestSpec, responseSpec, url, jsonBody, "resourceId");
    }
}
