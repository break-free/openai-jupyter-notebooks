
package org.apache.fineract.accounting.glaccount.service;
import java.util.List;
import org.apache.fineract.accounting.glaccount.data.GLAccountData;
import org.apache.fineract.accounting.glaccount.data.GLAccountDataForLookup;
import org.apache.fineract.accounting.glaccount.domain.GLAccountType;
import org.apache.fineract.accounting.journalentry.data.JournalEntryAssociationParametersData;
public interface GLAccountReadPlatformService {
    List<GLAccountData> retrieveAllGLAccounts(Integer accountClassification, String searchParam, Integer usage,
            Boolean manualTransactionsAllowed, Boolean disabled, JournalEntryAssociationParametersData associationParametersData);
    GLAccountData retrieveGLAccountById(long glAccountId, JournalEntryAssociationParametersData associationParametersData);
    List<GLAccountData> retrieveAllEnabledDetailGLAccounts();
    List<GLAccountData> retrieveAllEnabledDetailGLAccounts(GLAccountType accountType);
    List<GLAccountData> retrieveAllEnabledHeaderGLAccounts(GLAccountType accountType);
    GLAccountData retrieveNewGLAccountDetails(Integer type);
    List<GLAccountDataForLookup> retrieveAccountsByTagId(Long ruleId, Integer transactionType);
}
