
package org.apache.fineract.portfolio.loanaccount.domain;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.portfolio.loanaccount.data.HolidayDetailDTO;
import org.apache.fineract.portfolio.paymentdetail.domain.PaymentDetail;
public interface LoanAccountDomainService {
    LoanTransaction makeRepayment(LoanTransactionType repaymentTransactionType, Loan loan, CommandProcessingResultBuilder builderResult,
            LocalDate transactionDate, BigDecimal transactionAmount, PaymentDetail paymentDetail, String noteText, String txnExternalId,
            boolean isRecoveryRepayment, boolean isAccountTransfer, HolidayDetailDTO holidatDetailDto, Boolean isHolidayValidationDone);
    LoanTransaction makeRefund(Long accountId, CommandProcessingResultBuilder builderResult, LocalDate transactionDate,
            BigDecimal transactionAmount, PaymentDetail paymentDetail, String noteText, String txnExternalId);
    LoanTransaction makeDisburseTransaction(Long loanId, LocalDate transactionDate, BigDecimal transactionAmount,
            PaymentDetail paymentDetail, String noteText, String txnExternalId, boolean isLoanToLoanTransfer);
    void reverseTransfer(LoanTransaction loanTransaction);
    LoanTransaction makeChargePayment(Loan loan, Long chargeId, LocalDate transactionDate, BigDecimal transactionAmount,
            PaymentDetail paymentDetail, String noteText, String txnExternalId, Integer transactionType, Integer installmentNumber);
    LoanTransaction makeDisburseTransaction(Long loanId, LocalDate transactionDate, BigDecimal transactionAmount,
            PaymentDetail paymentDetail, String noteText, String txnExternalId);
    LoanTransaction makeRefundForActiveLoan(Long accountId, CommandProcessingResultBuilder builderResult, LocalDate transactionDate,
            BigDecimal transactionAmount, PaymentDetail paymentDetail, String noteText, String txnExternalId);
    void updateLoanCollateralTransaction(Set<LoanCollateralManagement> loanCollateralManagementList);
    void updateLoanCollateralStatus(Set<LoanCollateralManagement> loanCollateralManagementSet, boolean isReleased);
    void recalculateAccruals(Loan loan);
    LoanTransaction makeRepayment(LoanTransactionType repaymentTransactionType, Loan loan, CommandProcessingResultBuilder builderResult,
            LocalDate transactionDate, BigDecimal transactionAmount, PaymentDetail paymentDetail, String noteText, String txnExternalId,
            boolean isRecoveryRepayment, boolean isAccountTransfer, HolidayDetailDTO holidayDetailDto, Boolean isHolidayValidationDone,
            boolean isLoanToLoanTransfer);
    void saveLoanWithDataIntegrityViolationChecks(Loan loan);
    Map<String, Object> foreCloseLoan(Loan loan, LocalDate foreClourseDate, String noteText);
    void disableStandingInstructionsLinkedToClosedLoan(Loan loan);
    void recalculateAccruals(Loan loan, boolean isInterestCalcualtionHappened);
    CommandProcessingResultBuilder creditBalanceRefund(Long loanId, LocalDate transactionDate, BigDecimal transactionAmount,
            String noteText, String externalId);
}
