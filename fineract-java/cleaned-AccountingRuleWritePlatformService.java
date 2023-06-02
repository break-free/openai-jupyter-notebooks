
package org.apache.fineract.accounting.rule.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface AccountingRuleWritePlatformService {
    CommandProcessingResult createAccountingRule(JsonCommand command);
    CommandProcessingResult updateAccountingRule(Long accountingRuleId, JsonCommand command);
    CommandProcessingResult deleteAccountingRule(Long accountingRuleId);
}
