
package org.apache.fineract.accounting.producttoaccountmapping.service;
import java.util.Map;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.portfolio.savings.DepositAccountType;
public interface ProductToGLAccountMappingWritePlatformService {
    void createLoanProductToGLAccountMapping(Long loanProductId, JsonCommand command);
    void createSavingProductToGLAccountMapping(Long savingProductId, JsonCommand command, DepositAccountType accountType);
    Map<String, Object> updateLoanProductToGLAccountMapping(Long loanProductId, JsonCommand command, boolean accountingRuleChanged,
            int accountingRuleTypeId);
    Map<String, Object> updateSavingsProductToGLAccountMapping(Long savingsProductId, JsonCommand command, boolean accountingRuleChanged,
            int accountingRuleTypeId, DepositAccountType accountType);
    void createShareProductToGLAccountMapping(Long shareProductId, JsonCommand command);
    Map<String, Object> updateShareProductToGLAccountMapping(Long shareProductId, JsonCommand command, boolean accountingRuleChanged,
            int accountingRuleTypeId);
}
