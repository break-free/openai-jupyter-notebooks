
package org.apache.fineract.integrationtests.common;
import com.google.gson.Gson;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.HashMap;
import org.apache.fineract.integrationtests.common.accounting.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public final class TaxComponentHelper {
    private TaxComponentHelper() {
    }
    private static final Logger LOG = LoggerFactory.getLogger(TaxComponentHelper.class);
    private static final String CREATE_TAX_COMPONENT_URL = "/fineract-provider/api/v1/taxes/component?" + Utils.TENANT_IDENTIFIER;
    public static Integer createTaxComponent(final RequestSpecification requestSpec, final ResponseSpecification responseSpec,
            final String percentage, final Integer liabilityAccountId) {
        LOG.info("---------------------------------CREATING A TAX COMPONENT---------------------------------------------");
        return Utils.performServerPost(requestSpec, responseSpec, CREATE_TAX_COMPONENT_URL,
                getTaxComponentAsJSON(percentage, liabilityAccountId), "resourceId");
    }
    public static String getTaxComponentAsJSON(final String percentage, final Integer creditAccountId) {
        final HashMap<String, String> map = getBasicTaxComponentMap(percentage);
        if (creditAccountId != null) {
            map.put("creditAccountType", Account.AccountType.LIABILITY.toString());
            map.put("creditAcountId", String.valueOf(creditAccountId));
        }
        LOG.info("map :  {}", map);
        return new Gson().toJson(map);
    }
    public static HashMap<String, String> getBasicTaxComponentMap(final String percentage) {
        final HashMap<String, String> map = new HashMap<>();
        map.put("name", randomNameGenerator("Tax_component_Name_", 5));
        map.put("dateFormat", "dd MMMM yyyy");
        map.put("locale", "en");
        map.put("percentage", percentage);
        map.put("startDate", "01 January 2013");
        return map;
    }
    public static String randomNameGenerator(final String prefix, final int lenOfRandomSuffix) {
        return Utils.randomStringGenerator(prefix, lenOfRandomSuffix);
    }
}
