
package org.apache.fineract.portfolio.loanaccount.domain;
import org.apache.fineract.portfolio.loanaccount.domain.transactionprocessor.LoanRepaymentScheduleTransactionProcessor;
import org.apache.fineract.portfolio.loanaccount.domain.transactionprocessor.impl.CreocoreLoanRepaymentScheduleTransactionProcessor;
import org.apache.fineract.portfolio.loanaccount.domain.transactionprocessor.impl.EarlyPaymentLoanRepaymentScheduleTransactionProcessor;
import org.apache.fineract.portfolio.loanaccount.domain.transactionprocessor.impl.FineractStyleLoanRepaymentScheduleTransactionProcessor;
import org.apache.fineract.portfolio.loanaccount.domain.transactionprocessor.impl.HeavensFamilyLoanRepaymentScheduleTransactionProcessor;
import org.apache.fineract.portfolio.loanaccount.domain.transactionprocessor.impl.InterestPrincipalPenaltyFeesOrderLoanRepaymentScheduleTransactionProcessor;
import org.apache.fineract.portfolio.loanaccount.domain.transactionprocessor.impl.PrincipalInterestPenaltyFeesOrderLoanRepaymentScheduleTransactionProcessor;
import org.apache.fineract.portfolio.loanaccount.domain.transactionprocessor.impl.RBILoanRepaymentScheduleTransactionProcessor;
import org.apache.fineract.portfolio.loanproduct.domain.LoanTransactionProcessingStrategy;
import org.springframework.stereotype.Component;
@Component
public class LoanRepaymentScheduleTransactionProcessorFactory {
    public LoanRepaymentScheduleTransactionProcessor determineProcessor(
            final LoanTransactionProcessingStrategy transactionProcessingStrategy) {
        LoanRepaymentScheduleTransactionProcessor processor = new PrincipalInterestPenaltyFeesOrderLoanRepaymentScheduleTransactionProcessor();
        if (transactionProcessingStrategy != null) {
            if (transactionProcessingStrategy.isStandardStrategy()) {
                processor = new FineractStyleLoanRepaymentScheduleTransactionProcessor();
            }
            if (transactionProcessingStrategy.isHeavensfamilyStrategy()) {
                processor = new HeavensFamilyLoanRepaymentScheduleTransactionProcessor();
            }
            if (transactionProcessingStrategy.isEarlyPaymentStrategy()) {
                processor = new EarlyPaymentLoanRepaymentScheduleTransactionProcessor();
            }
            if (transactionProcessingStrategy.isCreocoreStrategy()) {
                processor = new CreocoreLoanRepaymentScheduleTransactionProcessor();
            }
            if (transactionProcessingStrategy.isIndianRBIStrategy()) {
                processor = new RBILoanRepaymentScheduleTransactionProcessor();
            }
            if (transactionProcessingStrategy.isPrincipalInterestPenaltiesFeesOrderStrategy()) {
                processor = new PrincipalInterestPenaltyFeesOrderLoanRepaymentScheduleTransactionProcessor();
            }
            if (transactionProcessingStrategy.isInterestPrincipalPenaltiesFeesOrderStrategy()) {
                processor = new InterestPrincipalPenaltyFeesOrderLoanRepaymentScheduleTransactionProcessor();
            }
        }
        return processor;
    }
}
