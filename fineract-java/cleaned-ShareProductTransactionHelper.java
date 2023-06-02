
package org.apache.fineract.integrationtests.common.shares;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.Map;
import org.apache.fineract.integrationtests.common.Utils;
public final class ShareProductTransactionHelper {
    private ShareProductTransactionHelper() {
    }
    private static final String SHARE_PRODUCT_URL = "/fineract-provider/api/v1/products/share";
    private static final String CREATE_SHARE_PRODUCT_URL = SHARE_PRODUCT_URL + "?" + Utils.TENANT_IDENTIFIER;
    public static Integer createShareProduct(final String savingsProductJSON, final RequestSpecification requestSpec,
            final ResponseSpecification responseSpec) {
        return Utils.performServerPost(requestSpec, responseSpec, CREATE_SHARE_PRODUCT_URL, savingsProductJSON, "resourceId");
    }
    public static Map<String, Object> retrieveShareProduct(final Integer shareProductId, final RequestSpecification requestSpec,
            final ResponseSpecification responseSpec) {
        String url = SHARE_PRODUCT_URL + "/" + shareProductId + "?" + Utils.TENANT_IDENTIFIER;
        return Utils.performServerGet(requestSpec, responseSpec, url, "");
    }
    public static Integer updateShareProduct(final Integer shareProductId, final String provsioningCriteriaJson,
            final RequestSpecification requestSpec, final ResponseSpecification responseSpec) {
        String url = SHARE_PRODUCT_URL + "/" + shareProductId + "?" + Utils.TENANT_IDENTIFIER;
        return Utils.performServerPut(requestSpec, responseSpec, url, provsioningCriteriaJson, "resourceId");
    }
}
