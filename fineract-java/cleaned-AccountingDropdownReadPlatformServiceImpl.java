
package org.apache.fineract.accounting.common;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.accounting.glaccount.data.GLAccountData;
import org.apache.fineract.accounting.glaccount.domain.GLAccountType;
import org.apache.fineract.accounting.glaccount.domain.GLAccountUsage;
import org.apache.fineract.accounting.glaccount.service.GLAccountReadPlatformService;
import org.apache.fineract.accounting.journalentry.domain.JournalEntryType;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class AccountingDropdownReadPlatformServiceImpl implements AccountingDropdownReadPlatformService {
    private final GLAccountReadPlatformService accountReadPlatformService;
    @Override
    public List<EnumOptionData> retrieveGLAccountTypeOptions() {
        return AccountingEnumerations.gLAccountType(GLAccountType.values());
    }
    @Override
    public List<EnumOptionData> retrieveGLAccountUsageOptions() {
        return AccountingEnumerations.gLAccountUsage(GLAccountUsage.values());
    }
    @Override
    public List<EnumOptionData> retrieveJournalEntryTypeOptions() {
        return AccountingEnumerations.journalEntryTypes(JournalEntryType.values());
    }
    @Override
    public List<EnumOptionData> retrieveAccountingRuleTypeOptions() {
        return AccountingEnumerations.accountingRuleTypes(AccountingRuleType.values());
    }
    @Override
    public Map<String, List<GLAccountData>> retrieveAccountMappingOptionsForLoanProducts() {
        return retrieveAccountMappingOptions();
    }
    @Override
    public Map<String, List<GLAccountData>> retrieveAccountMappingOptionsForSavingsProducts() {
        return retrieveAccountMappingOptions();
    }
    @Override
    public Map<String, List<GLAccountData>> retrieveAccountMappingOptionsForCharges() {
        boolean includeIncomeAccounts = true;
        boolean includeLiabilityAccounts = true;
        boolean includeAssetAccounts = false;
        boolean includeExpenseAccounts = false;
        boolean includeEquityAccounts = false;
        return retrieveAccountMappingOptions(includeAssetAccounts, includeIncomeAccounts, includeExpenseAccounts, includeLiabilityAccounts,
                includeEquityAccounts);
    }
    @Override
    public Map<String, List<GLAccountData>> retrieveAccountMappingOptions() {
        boolean includeAssetAccounts = true;
        boolean includeIncomeAccounts = true;
        boolean includeExpenseAccounts = true;
        boolean includeLiabilityAccounts = true;
        boolean includeEquityAccounts = true;
        return retrieveAccountMappingOptions(includeAssetAccounts, includeIncomeAccounts, includeExpenseAccounts, includeLiabilityAccounts,
                includeEquityAccounts);
    }
    @Override
    public List<GLAccountData> retrieveExpenseAccountOptions() {
        return accountReadPlatformService.retrieveAllEnabledDetailGLAccounts(GLAccountType.EXPENSE);
    }
    @Override
    public List<GLAccountData> retrieveAssetAccountOptions() {
        return accountReadPlatformService.retrieveAllEnabledDetailGLAccounts(GLAccountType.ASSET);
    }
    private Map<String, List<GLAccountData>> retrieveAccountMappingOptions(boolean includeAssetAccounts, boolean includeIncomeAccounts,
            boolean includeExpenseAccounts, boolean includeLiabilityAccounts, boolean includeEquityAccounts) {
        final Map<String, List<GLAccountData>> accountOptions = new HashMap<>();
        if (includeAssetAccounts) {
            List<GLAccountData> assetAccountOptions = this.accountReadPlatformService
                    .retrieveAllEnabledDetailGLAccounts(GLAccountType.ASSET);
            if (assetAccountOptions.isEmpty()) {
                assetAccountOptions = null;
            }
            accountOptions.put("assetAccountOptions", assetAccountOptions);
        }
        if (includeIncomeAccounts) {
            List<GLAccountData> incomeAccountOptions = this.accountReadPlatformService
                    .retrieveAllEnabledDetailGLAccounts(GLAccountType.INCOME);
            if (incomeAccountOptions.isEmpty()) {
                incomeAccountOptions = null;
            }
            accountOptions.put("incomeAccountOptions", incomeAccountOptions);
        }
        if (includeExpenseAccounts) {
            List<GLAccountData> expenseAccountOptions = this.accountReadPlatformService
                    .retrieveAllEnabledDetailGLAccounts(GLAccountType.EXPENSE);
            if (expenseAccountOptions.isEmpty()) {
                expenseAccountOptions = null;
            }
            accountOptions.put("expenseAccountOptions", expenseAccountOptions);
        }
        if (includeLiabilityAccounts) {
            List<GLAccountData> liabilityAccountOptions = this.accountReadPlatformService
                    .retrieveAllEnabledDetailGLAccounts(GLAccountType.LIABILITY);
            if (liabilityAccountOptions.isEmpty()) {
                liabilityAccountOptions = null;
            }
            accountOptions.put("liabilityAccountOptions", liabilityAccountOptions);
        }
        if (includeEquityAccounts) {
            List<GLAccountData> equityAccountOptions = this.accountReadPlatformService
                    .retrieveAllEnabledDetailGLAccounts(GLAccountType.EQUITY);
            if (equityAccountOptions.isEmpty()) {
                equityAccountOptions = null;
            }
            accountOptions.put("equityAccountOptions", equityAccountOptions);
        }
        return accountOptions;
    }
    @Override
    public Map<String, List<GLAccountData>> retrieveAccountMappingOptionsForShareProducts() {
        boolean includeAssetAccounts = true;
        boolean includeIncomeAccounts = true;
        boolean includeExpenseAccounts = false;
        boolean includeLiabilityAccounts = true;
        boolean includeEquityAccounts = true;
        return retrieveAccountMappingOptions(includeAssetAccounts, includeIncomeAccounts, includeExpenseAccounts, includeLiabilityAccounts,
                includeEquityAccounts);
    }
}
