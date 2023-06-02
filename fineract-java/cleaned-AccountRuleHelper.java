
package org.apache.fineract.integrationtests.common.accounting;
import com.google.gson.Gson;
import com.linecorp.armeria.internal.shaded.guava.reflect.TypeToken;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.lang.reflect.Type;
import java.util.ArrayList;
import org.apache.fineract.client.models.GetAccountRulesResponse;
import org.apache.fineract.client.models.PostAccountingRulesResponse;
import org.apache.fineract.client.util.JSON;
import org.apache.fineract.integrationtests.common.Utils;
public class AccountRuleHelper {
    private static final Gson GSON = new JSON().getGson();
    private static final String ACCOUNTINGRULES_URL = "/fineract-provider/api/v1/accountingrules?" + Utils.TENANT_IDENTIFIER;
    private final RequestSpecification requestSpec;
    private final ResponseSpecification responseSpec;
    public AccountRuleHelper(final RequestSpecification requestSpec, final ResponseSpecification responseSpec) {
        this.requestSpec = requestSpec;
        this.responseSpec = responseSpec;
    }
    public ArrayList<GetAccountRulesResponse> getAccountingRules() {
        final String response = Utils.performServerGet(this.requestSpec, this.responseSpec, ACCOUNTINGRULES_URL);
        Type accountRuleListType = new TypeToken<ArrayList<GetAccountRulesResponse>>() {}.getType();
        return GSON.fromJson(response, accountRuleListType);
    }
    public PostAccountingRulesResponse createAccountRule(final Long officeId, final Account accountToCredit, final Account accountToDebit) {
        final String assetAccountJSON = new AccountingRuleBuilder()
                .withGLAccounts(accountToCredit.getAccountID(), accountToDebit.getAccountID()).withOffice(officeId).build();
        final String response = Utils.performServerPost(requestSpec, responseSpec, ACCOUNTINGRULES_URL, assetAccountJSON);
        return GSON.fromJson(response, PostAccountingRulesResponse.class);
    }
}
