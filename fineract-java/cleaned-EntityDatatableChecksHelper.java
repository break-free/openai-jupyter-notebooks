
package org.apache.fineract.integrationtests.common.organisation;
import com.google.gson.Gson;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.HashMap;
import org.apache.fineract.integrationtests.common.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class EntityDatatableChecksHelper {
    private static final Logger LOG = LoggerFactory.getLogger(EntityDatatableChecksHelper.class);
    private final RequestSpecification requestSpec;
    private final ResponseSpecification responseSpec;
    private static final String DATATABLE_CHECK_URL = "/fineract-provider/api/v1/entityDatatableChecks";
    public EntityDatatableChecksHelper(final RequestSpecification requestSpec, final ResponseSpecification responseSpec) {
        this.requestSpec = requestSpec;
        this.responseSpec = responseSpec;
    }
    public Integer createEntityDatatableCheck(final String apptableName, final String datatableName, final int status,
            final Integer productId) {
        return Utils.performServerPost(this.requestSpec, this.responseSpec, DATATABLE_CHECK_URL + "?" + Utils.TENANT_IDENTIFIER,
                getTestEdcAsJSON(apptableName, datatableName, status, productId), "resourceId");
    }
    public Integer deleteEntityDatatableCheck(final Integer entityDatatableCheckId) {
        return Utils.performServerDelete(requestSpec, responseSpec,
                DATATABLE_CHECK_URL + "/" + entityDatatableCheckId + "?" + Utils.TENANT_IDENTIFIER, "resourceId");
    }
    public String retrieveEntityDatatableCheck() {
        return Utils.performServerGet(requestSpec, responseSpec, DATATABLE_CHECK_URL + "?" + Utils.TENANT_IDENTIFIER, null);
    }
    public static String getTestEdcAsJSON(final String apptableName, final String datatableName, final int status,
            final Integer productId) {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("entity", apptableName);
        map.put("status", status);
        map.put("datatableName", datatableName);
        if (productId != null) {
            map.put("productId", productId);
        }
        String requestJsonString = new Gson().toJson(map);
        LOG.info("map : {}", requestJsonString);
        return requestJsonString;
    }
}
