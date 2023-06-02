
package org.apache.fineract.portfolio.loanaccount.service;
import java.util.Collection;
import org.apache.fineract.portfolio.charge.data.ChargeData;
import org.apache.fineract.portfolio.loanaccount.data.LoanChargeData;
import org.apache.fineract.portfolio.loanaccount.data.LoanChargePaidByData;
import org.apache.fineract.portfolio.loanaccount.data.LoanInstallmentChargeData;
import org.apache.fineract.portfolio.loanaccount.domain.LoanTransactionType;
public interface LoanChargeReadPlatformService {
    ChargeData retrieveLoanChargeTemplate();
    Collection<LoanChargeData> retrieveLoanCharges(Long loanId);
    LoanChargeData retrieveLoanChargeDetails(Long loanChargeId, Long loanId);
    Collection<LoanChargeData> retrieveLoanChargesForFeePayment(Integer paymentMode, Integer loanStatus);
    Collection<LoanInstallmentChargeData> retrieveInstallmentLoanCharges(Long loanChargeId, boolean onlyPaymentPendingCharges);
    Collection<Integer> retrieveOverdueInstallmentChargeFrequencyNumber(Long loanId, Long chargeId, Integer periodNumber);
    Collection<LoanChargeData> retrieveLoanChargesForAccural(Long loanId);
    Collection<LoanChargePaidByData> retriveLoanChargesPaidBy(Long chargeId, LoanTransactionType transactionType,
            Integer installmentNumber);
}
