
package org.apache.fineract.integrationtests.common.accounting;
import com.google.gson.Gson;
import java.util.HashMap;
import org.apache.fineract.integrationtests.common.Utils;
public class AccountingRuleBuilder {
    private Long officeId;
    private Integer accountToCreditId;
    private Integer accountToDebitId;
    private String name;
    private String description;
    public AccountingRuleBuilder() {
        name = Utils.randomStringGenerator("ACCOUNTRULE_NAME_", 5);
        description = name;
    }
    public String build() {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("officeId", officeId);
        map.put("accountToCredit", accountToCreditId);
        map.put("accountToDebit", accountToDebitId);
        map.put("description", description);
        return new Gson().toJson(map);
    }
    public AccountingRuleBuilder withGLAccounts(final Integer accountToCreditId, final Integer accountToDebitId) {
        this.accountToCreditId = accountToCreditId;
        this.accountToDebitId = accountToDebitId;
        return this;
    }
    public AccountingRuleBuilder withOffice(final Long officeId) {
        this.officeId = officeId;
        return this;
    }
}
