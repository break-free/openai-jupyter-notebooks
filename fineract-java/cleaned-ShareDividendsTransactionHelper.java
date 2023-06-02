
package org.apache.fineract.integrationtests.common.shares;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.Map;
import org.apache.fineract.integrationtests.common.Utils;
public final class ShareDividendsTransactionHelper {
    private ShareDividendsTransactionHelper() {
    }
    private static final String SHARE_PRODUCT_URL = "/fineract-provider/api/v1/shareproduct";
    private static final String DIVIDEND = "dividend";
    public static Integer createShareProductDividends(final Integer productId, final String dividendJson,
            final RequestSpecification requestSpec, final ResponseSpecification responseSpec) {
        String url = SHARE_PRODUCT_URL + "/" + productId + "/" + DIVIDEND + "?" + Utils.TENANT_IDENTIFIER;
        return Utils.performServerPost(requestSpec, responseSpec, url, dividendJson, "subResourceId");
    }
    public static Integer postCommand(final String command, final Integer productId, final Integer dividendId, String jsonBody,
            final RequestSpecification requestSpec, final ResponseSpecification responseSpec) {
        String url = SHARE_PRODUCT_URL + "/" + productId + "/" + DIVIDEND + "/" + dividendId + "?command=" + command + "&"
                + Utils.TENANT_IDENTIFIER;
        return Utils.performServerPut(requestSpec, responseSpec, url, jsonBody, "resourceId");
    }
    public static Map<String, Object> retrieveDividendDetails(final Integer productId, final Integer dividendId,
            final RequestSpecification requestSpec, final ResponseSpecification responseSpec) {
        String url = SHARE_PRODUCT_URL + "/" + productId + "/" + DIVIDEND + "/" + dividendId + "?" + Utils.TENANT_IDENTIFIER;
        return Utils.performServerGet(requestSpec, responseSpec, url, "");
    }
    public static Map<String, Object> retrieveAllDividends(final Integer productId, final RequestSpecification requestSpec,
            final ResponseSpecification responseSpec) {
        String url = SHARE_PRODUCT_URL + "/" + productId + "/" + DIVIDEND + "?" + Utils.TENANT_IDENTIFIER;
        return Utils.performServerGet(requestSpec, responseSpec, url, "");
    }
}
