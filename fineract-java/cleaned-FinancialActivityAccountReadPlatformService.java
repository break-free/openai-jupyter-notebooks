
package org.apache.fineract.accounting.financialactivityaccount.service;
import java.util.List;
import org.apache.fineract.accounting.financialactivityaccount.data.FinancialActivityAccountData;
public interface FinancialActivityAccountReadPlatformService {
    List<FinancialActivityAccountData> retrieveAll();
    FinancialActivityAccountData retrieve(Long mappingId);
    FinancialActivityAccountData addTemplateDetails(FinancialActivityAccountData financialActivityAccountData);
    FinancialActivityAccountData getFinancialActivityAccountTemplate();
}
