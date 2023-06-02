
package org.apache.fineract.integrationtests.common.accounting;
import com.google.gson.Gson;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.HashMap;
import org.apache.fineract.integrationtests.common.Utils;
public class PeriodicAccrualAccountingHelper {
    private static final String PERIODIC_ACCRUAL_URL = "/fineract-provider/api/v1/runaccruals";
    private final RequestSpecification requestSpec;
    private final ResponseSpecification responseSpec;
    public PeriodicAccrualAccountingHelper(final RequestSpecification requestSpec, final ResponseSpecification responseSpec) {
        this.requestSpec = requestSpec;
        this.responseSpec = responseSpec;
    }
    public Object runPeriodicAccrualAccounting(String date) {
        String json = getRunPeriodicAccrual(date);
        return Utils.performServerPost(this.requestSpec, this.responseSpec, PERIODIC_ACCRUAL_URL + "?" + Utils.TENANT_IDENTIFIER, json, "");
    }
    private String getRunPeriodicAccrual(String date) {
        final HashMap<String, String> map = new HashMap<>();
        map.put("dateFormat", "dd MMMM yyyy");
        map.put("locale", "en_GB");
        map.put("tillDate", date);
        return new Gson().toJson(map);
    }
}
