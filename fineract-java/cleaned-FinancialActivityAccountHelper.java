
package org.apache.fineract.integrationtests.common.accounting;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.HashMap;
import java.util.List;
import org.apache.fineract.integrationtests.common.Utils;
@SuppressWarnings("rawtypes")
public class FinancialActivityAccountHelper {
    private static final String FINANCIAL_ACTIVITY_ACCOUNT_MAPPING_URL = "/fineract-provider/api/v1/financialactivityaccounts";
    private final RequestSpecification requestSpec;
    public FinancialActivityAccountHelper(final RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }
    public Object createFinancialActivityAccount(Integer financialActivityId, Integer glAccountId,
            final ResponseSpecification responseSpecification, String jsonBack) {
        String json = FinancialActivityAccountsMappingBuilder.build(financialActivityId, glAccountId);
        return Utils.performServerPost(this.requestSpec, responseSpecification,
                FINANCIAL_ACTIVITY_ACCOUNT_MAPPING_URL + "?" + Utils.TENANT_IDENTIFIER, json, jsonBack);
    }
    public Object updateFinancialActivityAccount(Integer financialActivityAccountId, Integer financialActivityId, Integer glAccountId,
            final ResponseSpecification responseSpecification, String jsonBack) {
        String json = FinancialActivityAccountsMappingBuilder.build(financialActivityId, glAccountId);
        return Utils.performServerPut(this.requestSpec, responseSpecification,
                FINANCIAL_ACTIVITY_ACCOUNT_MAPPING_URL + "/" + financialActivityAccountId + "?" + Utils.TENANT_IDENTIFIER, json, jsonBack);
    }
    public HashMap getFinancialActivityAccount(final Integer financialActivityAccountId,
            final ResponseSpecification responseSpecification) {
        final String url = FINANCIAL_ACTIVITY_ACCOUNT_MAPPING_URL + "/" + financialActivityAccountId + "?" + Utils.TENANT_IDENTIFIER;
        return Utils.performServerGet(requestSpec, responseSpecification, url, "");
    }
    public List<HashMap> getAllFinancialActivityAccounts(final ResponseSpecification responseSpecification) {
        final String url = FINANCIAL_ACTIVITY_ACCOUNT_MAPPING_URL + "?" + Utils.TENANT_IDENTIFIER;
        return Utils.performServerGet(this.requestSpec, responseSpecification, url, "");
    }
    public Integer deleteFinancialActivityAccount(final Integer financialActivityAccountId,
            final ResponseSpecification responseSpecification, String jsonBack) {
        final String url = FINANCIAL_ACTIVITY_ACCOUNT_MAPPING_URL + "/" + financialActivityAccountId + "?" + Utils.TENANT_IDENTIFIER;
        return Utils.performServerDelete(this.requestSpec, responseSpecification, url, jsonBack);
    }
}
