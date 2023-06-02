
package org.apache.fineract.integrationtests.variableinstallments;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.HashMap;
import java.util.Map;
import org.apache.fineract.integrationtests.common.Utils;
@SuppressWarnings("rawtypes")
public class VariableIntallmentsTransactionHelper {
    private static final String URL = "https:
    private final RequestSpecification requestSpec;
    private final ResponseSpecification responseSpec;
    public VariableIntallmentsTransactionHelper(final RequestSpecification requestSpec, final ResponseSpecification responseSpec) {
        this.requestSpec = requestSpec;
        this.responseSpec = responseSpec;
    }
    public Map retrieveSchedule(Integer loanId) {
        String url = URL + loanId + "?associations=repaymentSchedule&exclude=guarantors&" + Utils.TENANT_IDENTIFIER;
        return Utils.performServerGet(requestSpec, responseSpec, url, "");
    }
    public HashMap validateVariations(final String exceptions, Integer loanId) {
        String url = URL + loanId + "/schedule?command=calculateLoanSchedule&" + Utils.TENANT_IDENTIFIER;
        return Utils.performServerPost(this.requestSpec, this.responseSpec, url, exceptions, "");
    }
    public HashMap submitVariations(final String exceptions, Integer loanId) {
        String url = URL + loanId + "/schedule?command=addVariations&" + Utils.TENANT_IDENTIFIER;
        return Utils.performServerPost(this.requestSpec, this.responseSpec, url, exceptions, "");
    }
}
