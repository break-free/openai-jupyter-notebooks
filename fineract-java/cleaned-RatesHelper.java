
package org.apache.fineract.integrationtests.common.rates;
import com.google.gson.Gson;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.fineract.integrationtests.common.CommonConstants;
import org.apache.fineract.integrationtests.common.Utils;
@SuppressWarnings({ "rawtypes", "unchecked" })
public final class RatesHelper {
    private RatesHelper() {
    }
    private static final String RATES_URL = "/fineract-provider/api/v1/rates";
    private static final String CREATE_RATES_URL = RATES_URL + "?" + Utils.TENANT_IDENTIFIER;
    private static final String PERCENTAGE = "10";
    private static final Integer PRODUCT_APPLY_LOAN = 1;
    private static final Boolean ACTIVE = true;
    public static ArrayList<HashMap> getRates(final RequestSpecification requestSpec, final ResponseSpecification responseSpec) {
        return (ArrayList) Utils.performServerGet(requestSpec, responseSpec, RATES_URL + "?" + Utils.TENANT_IDENTIFIER, "");
    }
    public static Integer createRates(final RequestSpecification requestSpec, final ResponseSpecification responseSpec,
            final String request) {
        return Utils.performServerPost(requestSpec, responseSpec, CREATE_RATES_URL, request, "resourceId");
    }
    public static HashMap getRateById(final RequestSpecification requestSpec, final ResponseSpecification responseSpec,
            final Integer rateId) {
        return Utils.performServerGet(requestSpec, responseSpec, RATES_URL + "/" + rateId + "?" + Utils.TENANT_IDENTIFIER, "");
    }
    public static HashMap updateRates(final RequestSpecification requestSpec, final ResponseSpecification responseSpec,
            final Integer rateId, final String request) {
        return Utils.performServerPut(requestSpec, responseSpec, RATES_URL + "/" + rateId + "?" + Utils.TENANT_IDENTIFIER, request,
                CommonConstants.RESPONSE_CHANGES);
    }
    public static String getLoanRateJSON() {
        return getLoanRateJSON(RatesHelper.PRODUCT_APPLY_LOAN, RatesHelper.PERCENTAGE);
    }
    public static String getLoanRateJSON(final Integer productApply, final String percentage) {
        final HashMap<String, Object> map = populateDefaultsForLoan();
        map.put("percentage", percentage);
        map.put("productApply", productApply);
        String crateRateJSON = new Gson().toJson(map);
        return crateRateJSON;
    }
    public static HashMap<String, Object> populateDefaultsForLoan() {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("active", RatesHelper.ACTIVE);
        map.put("percentage", RatesHelper.PERCENTAGE);
        map.put("locale", "en");
        map.put("productApply", RatesHelper.PRODUCT_APPLY_LOAN);
        map.put("name", Utils.randomNameGenerator("Rate_Loans_", 6));
        return map;
    }
    public static String getModifyRateJSON() {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("percentage", "15.0");
        map.put("locale", "en");
        String json = new Gson().toJson(map);
        return json;
    }
}
