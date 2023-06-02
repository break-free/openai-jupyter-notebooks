
package org.apache.fineract.accounting.rule.service;
import java.util.List;
import org.apache.fineract.accounting.rule.data.AccountingRuleData;
public interface AccountingRuleReadPlatformService {
    List<AccountingRuleData> retrieveAllAccountingRules(String hierarchySearchString, boolean isAssociationParametersExists);
    AccountingRuleData retrieveAccountingRuleById(Long accountingRuleId);
}
