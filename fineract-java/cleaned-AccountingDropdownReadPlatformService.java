
package org.apache.fineract.accounting.common;
import java.util.List;
import java.util.Map;
import org.apache.fineract.accounting.glaccount.data.GLAccountData;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
public interface AccountingDropdownReadPlatformService {
    List<EnumOptionData> retrieveGLAccountTypeOptions();
    List<EnumOptionData> retrieveGLAccountUsageOptions();
    List<EnumOptionData> retrieveJournalEntryTypeOptions();
    List<EnumOptionData> retrieveAccountingRuleTypeOptions();
    Map<String, List<GLAccountData>> retrieveAccountMappingOptionsForLoanProducts();
    Map<String, List<GLAccountData>> retrieveAccountMappingOptionsForSavingsProducts();
    Map<String, List<GLAccountData>> retrieveAccountMappingOptionsForCharges();
    Map<String, List<GLAccountData>> retrieveAccountMappingOptions();
    Map<String, List<GLAccountData>> retrieveAccountMappingOptionsForShareProducts();
    List<GLAccountData> retrieveExpenseAccountOptions();
    List<GLAccountData> retrieveAssetAccountOptions();
}
