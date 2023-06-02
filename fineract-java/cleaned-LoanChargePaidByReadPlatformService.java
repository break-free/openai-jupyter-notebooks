
package org.apache.fineract.portfolio.loanaccount.service;
import java.util.List;
import org.apache.fineract.portfolio.loanaccount.data.LoanChargePaidByData;
public interface LoanChargePaidByReadPlatformService {
    List<LoanChargePaidByData> getLoanChargesPaidByTransactionId(Long id);
}
