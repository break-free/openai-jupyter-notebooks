
package org.apache.fineract.accounting.journalentry.service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.accounting.closure.domain.GLClosure;
import org.apache.fineract.accounting.common.AccountingConstants.CashAccountsForLoan;
import org.apache.fineract.accounting.common.AccountingConstants.FinancialActivity;
import org.apache.fineract.accounting.journalentry.data.ChargePaymentDTO;
import org.apache.fineract.accounting.journalentry.data.LoanDTO;
import org.apache.fineract.accounting.journalentry.data.LoanTransactionDTO;
import org.apache.fineract.organisation.office.domain.Office;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class CashBasedAccountingProcessorForLoan implements AccountingProcessorForLoan {
    private final AccountingProcessorHelper helper;
    @Override
    public void createJournalEntriesForLoan(final LoanDTO loanDTO) {
        final GLClosure latestGLClosure = this.helper.getLatestClosureByBranch(loanDTO.getOfficeId());
        final Long loanProductId = loanDTO.getLoanProductId();
        final String currencyCode = loanDTO.getCurrencyCode();
        for (final LoanTransactionDTO loanTransactionDTO : loanDTO.getNewLoanTransactions()) {
            final LocalDate transactionDate = loanTransactionDTO.getTransactionDate();
            final String transactionId = loanTransactionDTO.getTransactionId();
            final Office office = this.helper.getOfficeById(loanTransactionDTO.getOfficeId());
            final Long paymentTypeId = loanTransactionDTO.getPaymentTypeId();
            final Long loanId = loanDTO.getLoanId();
            this.helper.checkForBranchClosures(latestGLClosure, transactionDate);
            if (loanTransactionDTO.getTransactionType().isDisbursement()) {
                createJournalEntriesForDisbursements(loanDTO, loanTransactionDTO, office);
            }
            else if (loanTransactionDTO.getTransactionType().isRepaymentType()
                    || loanTransactionDTO.getTransactionType().isRepaymentAtDisbursement()
                    || loanTransactionDTO.getTransactionType().isChargePayment()) {
                createJournalEntriesForRepayments(loanDTO, loanTransactionDTO, office);
            }
            else if (loanTransactionDTO.getTransactionType().isRecoveryRepayment()) {
                createJournalEntriesForRecoveryRepayments(loanDTO, loanTransactionDTO, office);
            }
            else if (loanTransactionDTO.getTransactionType().isRefund()) {
                createJournalEntriesForRefund(loanDTO, loanTransactionDTO, office);
            }
            else if (loanTransactionDTO.getTransactionType().isCreditBalanceRefund()) {
                createJournalEntriesForCreditBalanceRefund(loanDTO, loanTransactionDTO, office);
            }
            else if (loanTransactionDTO.getTransactionType().isWriteOff()) {
                final BigDecimal principalAmount = loanTransactionDTO.getPrincipal();
                if (principalAmount != null && !(principalAmount.compareTo(BigDecimal.ZERO) == 0)) {
                    this.helper.createCashBasedJournalEntriesAndReversalsForLoan(office, currencyCode,
                            CashAccountsForLoan.LOSSES_WRITTEN_OFF.getValue(), CashAccountsForLoan.LOAN_PORTFOLIO.getValue(), loanProductId,
                            paymentTypeId, loanId, transactionId, transactionDate, principalAmount, loanTransactionDTO.isReversed());
                }
            } else if (loanTransactionDTO.getTransactionType().isInitiateTransfer()
                    || loanTransactionDTO.getTransactionType().isApproveTransfer()
                    || loanTransactionDTO.getTransactionType().isWithdrawTransfer()) {
                createJournalEntriesForTransfers(loanDTO, loanTransactionDTO, office);
            }
            else if (loanTransactionDTO.getTransactionType().isRefundForActiveLoans()) {
                createJournalEntriesForRefundForActiveLoan(loanDTO, loanTransactionDTO, office);
            }
        }
    }
    private void createJournalEntriesForDisbursements(final LoanDTO loanDTO, final LoanTransactionDTO loanTransactionDTO,
            final Office office) {
        final Long loanProductId = loanDTO.getLoanProductId();
        final Long loanId = loanDTO.getLoanId();
        final String currencyCode = loanDTO.getCurrencyCode();
        final String transactionId = loanTransactionDTO.getTransactionId();
        final LocalDate transactionDate = loanTransactionDTO.getTransactionDate();
        final BigDecimal disbursalAmount = loanTransactionDTO.getAmount();
        final boolean isReversal = loanTransactionDTO.isReversed();
        final Long paymentTypeId = loanTransactionDTO.getPaymentTypeId();
        if (loanTransactionDTO.isLoanToLoanTransfer()) {
            this.helper.createCashBasedJournalEntriesAndReversalsForLoan(office, currencyCode,
                    CashAccountsForLoan.LOAN_PORTFOLIO.getValue(), FinancialActivity.ASSET_TRANSFER.getValue(), loanProductId,
                    paymentTypeId, loanId, transactionId, transactionDate, disbursalAmount, isReversal);
        } else if (loanTransactionDTO.isAccountTransfer()) {
            this.helper.createCashBasedJournalEntriesAndReversalsForLoan(office, currencyCode,
                    CashAccountsForLoan.LOAN_PORTFOLIO.getValue(), FinancialActivity.LIABILITY_TRANSFER.getValue(), loanProductId,
                    paymentTypeId, loanId, transactionId, transactionDate, disbursalAmount, isReversal);
        } else {
            this.helper.createCashBasedJournalEntriesAndReversalsForLoan(office, currencyCode,
                    CashAccountsForLoan.LOAN_PORTFOLIO.getValue(), CashAccountsForLoan.FUND_SOURCE.getValue(), loanProductId, paymentTypeId,
                    loanId, transactionId, transactionDate, disbursalAmount, isReversal);
        }
    }
    private void createJournalEntriesForRefund(final LoanDTO loanDTO, final LoanTransactionDTO loanTransactionDTO, final Office office) {
        final Long loanProductId = loanDTO.getLoanProductId();
        final Long loanId = loanDTO.getLoanId();
        final String currencyCode = loanDTO.getCurrencyCode();
        final String transactionId = loanTransactionDTO.getTransactionId();
        final LocalDate transactionDate = loanTransactionDTO.getTransactionDate();
        final BigDecimal refundAmount = loanTransactionDTO.getAmount();
        final boolean isReversal = loanTransactionDTO.isReversed();
        final Long paymentTypeId = loanTransactionDTO.getPaymentTypeId();
        if (loanTransactionDTO.isAccountTransfer()) {
            this.helper.createCashBasedJournalEntriesAndReversalsForLoan(office, currencyCode, CashAccountsForLoan.OVERPAYMENT.getValue(),
                    FinancialActivity.LIABILITY_TRANSFER.getValue(), loanProductId, paymentTypeId, loanId, transactionId, transactionDate,
                    refundAmount, isReversal);
        } else {
            this.helper.createCashBasedJournalEntriesAndReversalsForLoan(office, currencyCode, CashAccountsForLoan.OVERPAYMENT.getValue(),
                    CashAccountsForLoan.FUND_SOURCE.getValue(), loanProductId, paymentTypeId, loanId, transactionId, transactionDate,
                    refundAmount, isReversal);
        }
    }
    private void createJournalEntriesForRepayments(final LoanDTO loanDTO, final LoanTransactionDTO loanTransactionDTO,
            final Office office) {
        final Long loanProductId = loanDTO.getLoanProductId();
        final Long loanId = loanDTO.getLoanId();
        final String currencyCode = loanDTO.getCurrencyCode();
        final String transactionId = loanTransactionDTO.getTransactionId();
        final LocalDate transactionDate = loanTransactionDTO.getTransactionDate();
        final BigDecimal principalAmount = loanTransactionDTO.getPrincipal();
        final BigDecimal interestAmount = loanTransactionDTO.getInterest();
        final BigDecimal feesAmount = loanTransactionDTO.getFees();
        final BigDecimal penaltiesAmount = loanTransactionDTO.getPenalties();
        final BigDecimal overPaymentAmount = loanTransactionDTO.getOverPayment();
        final boolean isReversal = loanTransactionDTO.isReversed();
        final Long paymentTypeId = loanTransactionDTO.getPaymentTypeId();
        BigDecimal totalDebitAmount = new BigDecimal(0);
        if (principalAmount != null && !(principalAmount.compareTo(BigDecimal.ZERO) == 0)) {
            totalDebitAmount = totalDebitAmount.add(principalAmount);
            this.helper.createCreditJournalEntryOrReversalForLoan(office, currencyCode, CashAccountsForLoan.LOAN_PORTFOLIO, loanProductId,
                    paymentTypeId, loanId, transactionId, transactionDate, principalAmount, isReversal);
        }
        if (interestAmount != null && !(interestAmount.compareTo(BigDecimal.ZERO) == 0)) {
            totalDebitAmount = totalDebitAmount.add(interestAmount);
            this.helper.createCreditJournalEntryOrReversalForLoan(office, currencyCode, CashAccountsForLoan.INTEREST_ON_LOANS,
                    loanProductId, paymentTypeId, loanId, transactionId, transactionDate, interestAmount, isReversal);
        }
        if (feesAmount != null && !(feesAmount.compareTo(BigDecimal.ZERO) == 0)) {
            totalDebitAmount = totalDebitAmount.add(feesAmount);
            this.helper.createCreditJournalEntryOrReversalForLoanCharges(office, currencyCode,
                    CashAccountsForLoan.INCOME_FROM_FEES.getValue(), loanProductId, loanId, transactionId, transactionDate, feesAmount,
                    isReversal, loanTransactionDTO.getFeePayments());
        }
        if (penaltiesAmount != null && !(penaltiesAmount.compareTo(BigDecimal.ZERO) == 0)) {
            totalDebitAmount = totalDebitAmount.add(penaltiesAmount);
            this.helper.createCreditJournalEntryOrReversalForLoanCharges(office, currencyCode,
                    CashAccountsForLoan.INCOME_FROM_PENALTIES.getValue(), loanProductId, loanId, transactionId, transactionDate,
                    penaltiesAmount, isReversal, loanTransactionDTO.getPenaltyPayments());
        }
        if (overPaymentAmount != null && !(overPaymentAmount.compareTo(BigDecimal.ZERO) == 0)) {
            totalDebitAmount = totalDebitAmount.add(overPaymentAmount);
            this.helper.createCreditJournalEntryOrReversalForLoan(office, currencyCode, CashAccountsForLoan.OVERPAYMENT, loanProductId,
                    paymentTypeId, loanId, transactionId, transactionDate, overPaymentAmount, isReversal);
        }
        if (loanTransactionDTO.isLoanToLoanTransfer()) {
            this.helper.createDebitJournalEntryOrReversalForLoan(office, currencyCode, FinancialActivity.ASSET_TRANSFER.getValue(),
                    loanProductId, paymentTypeId, loanId, transactionId, transactionDate, totalDebitAmount, isReversal);
        } else if (loanTransactionDTO.isAccountTransfer()) {
            this.helper.createDebitJournalEntryOrReversalForLoan(office, currencyCode, FinancialActivity.LIABILITY_TRANSFER.getValue(),
                    loanProductId, paymentTypeId, loanId, transactionId, transactionDate, totalDebitAmount, isReversal);
        } else {
            if (loanTransactionDTO.getTransactionType().isGoodwillCredit()) {
                this.helper.createDebitJournalEntryOrReversalForLoan(office, currencyCode, CashAccountsForLoan.GOODWILL_CREDIT.getValue(),
                        loanProductId, paymentTypeId, loanId, transactionId, transactionDate, totalDebitAmount, isReversal);
            } else {
                this.helper.createDebitJournalEntryOrReversalForLoan(office, currencyCode, CashAccountsForLoan.FUND_SOURCE.getValue(),
                        loanProductId, paymentTypeId, loanId, transactionId, transactionDate, totalDebitAmount, isReversal);
            }
        }
    }
    private void createJournalEntriesForRecoveryRepayments(final LoanDTO loanDTO, final LoanTransactionDTO loanTransactionDTO,
            final Office office) {
        final Long loanProductId = loanDTO.getLoanProductId();
        final Long loanId = loanDTO.getLoanId();
        final String currencyCode = loanDTO.getCurrencyCode();
        final String transactionId = loanTransactionDTO.getTransactionId();
        final LocalDate transactionDate = loanTransactionDTO.getTransactionDate();
        final BigDecimal amount = loanTransactionDTO.getAmount();
        final boolean isReversal = loanTransactionDTO.isReversed();
        final Long paymentTypeId = loanTransactionDTO.getPaymentTypeId();
        this.helper.createCashBasedJournalEntriesAndReversalsForLoan(office, currencyCode, CashAccountsForLoan.FUND_SOURCE.getValue(),
                CashAccountsForLoan.INCOME_FROM_RECOVERY.getValue(), loanProductId, paymentTypeId, loanId, transactionId, transactionDate,
                amount, isReversal);
    }
    private void createJournalEntriesForTransfers(final LoanDTO loanDTO, final LoanTransactionDTO loanTransactionDTO, final Office office) {
        final Long loanProductId = loanDTO.getLoanProductId();
        final Long loanId = loanDTO.getLoanId();
        final String currencyCode = loanDTO.getCurrencyCode();
        final String transactionId = loanTransactionDTO.getTransactionId();
        final LocalDate transactionDate = loanTransactionDTO.getTransactionDate();
        final BigDecimal principalAmount = loanTransactionDTO.getPrincipal();
        final boolean isReversal = loanTransactionDTO.isReversed();
        if (loanTransactionDTO.getTransactionType().isInitiateTransfer()) {
            this.helper.createCashBasedJournalEntriesAndReversalsForLoan(office, currencyCode,
                    CashAccountsForLoan.TRANSFERS_SUSPENSE.getValue(), CashAccountsForLoan.LOAN_PORTFOLIO.getValue(), loanProductId, null,
                    loanId, transactionId, transactionDate, principalAmount, isReversal);
        } else if (loanTransactionDTO.getTransactionType().isApproveTransfer()
                || loanTransactionDTO.getTransactionType().isWithdrawTransfer()) {
            this.helper.createCashBasedJournalEntriesAndReversalsForLoan(office, currencyCode,
                    CashAccountsForLoan.LOAN_PORTFOLIO.getValue(), CashAccountsForLoan.TRANSFERS_SUSPENSE.getValue(), loanProductId, null,
                    loanId, transactionId, transactionDate, principalAmount, isReversal);
        }
    }
    private void createJournalEntriesForCreditBalanceRefund(final LoanDTO loanDTO, final LoanTransactionDTO loanTransactionDTO,
            final Office office) {
        final Long loanProductId = loanDTO.getLoanProductId();
        final Long loanId = loanDTO.getLoanId();
        final String currencyCode = loanDTO.getCurrencyCode();
        final String transactionId = loanTransactionDTO.getTransactionId();
        final LocalDate transactionDate = loanTransactionDTO.getTransactionDate();
        final BigDecimal refundAmount = loanTransactionDTO.getAmount();
        final boolean isReversal = loanTransactionDTO.isReversed();
        final Long paymentTypeId = loanTransactionDTO.getPaymentTypeId();
        this.helper.createCashBasedJournalEntriesAndReversalsForLoan(office, currencyCode, CashAccountsForLoan.LOAN_PORTFOLIO.getValue(),
                CashAccountsForLoan.OVERPAYMENT.getValue(), loanProductId, paymentTypeId, loanId, transactionId, transactionDate,
                refundAmount, isReversal);
    }
    private void createJournalEntriesForRefundForActiveLoan(LoanDTO loanDTO, LoanTransactionDTO loanTransactionDTO, Office office) {
        final Long loanProductId = loanDTO.getLoanProductId();
        final Long loanId = loanDTO.getLoanId();
        final String currencyCode = loanDTO.getCurrencyCode();
        final String transactionId = loanTransactionDTO.getTransactionId();
        final LocalDate transactionDate = loanTransactionDTO.getTransactionDate();
        final BigDecimal principalAmount = loanTransactionDTO.getPrincipal();
        final BigDecimal interestAmount = loanTransactionDTO.getInterest();
        final BigDecimal feesAmount = loanTransactionDTO.getFees();
        final BigDecimal penaltiesAmount = loanTransactionDTO.getPenalties();
        final BigDecimal overPaymentAmount = loanTransactionDTO.getOverPayment();
        final boolean isReversal = loanTransactionDTO.isReversed();
        final Long paymentTypeId = loanTransactionDTO.getPaymentTypeId();
        BigDecimal totalDebitAmount = new BigDecimal(0);
        if (principalAmount != null && !(principalAmount.compareTo(BigDecimal.ZERO) == 0)) {
            totalDebitAmount = totalDebitAmount.add(principalAmount);
            this.helper.createCreditJournalEntryOrReversalForLoan(office, currencyCode, CashAccountsForLoan.LOAN_PORTFOLIO, loanProductId,
                    paymentTypeId, loanId, transactionId, transactionDate, principalAmount, !isReversal);
        }
        if (interestAmount != null && !(interestAmount.compareTo(BigDecimal.ZERO) == 0)) {
            totalDebitAmount = totalDebitAmount.add(interestAmount);
            this.helper.createCreditJournalEntryOrReversalForLoan(office, currencyCode, CashAccountsForLoan.INTEREST_ON_LOANS,
                    loanProductId, paymentTypeId, loanId, transactionId, transactionDate, interestAmount, !isReversal);
        }
        if (feesAmount != null && !(feesAmount.compareTo(BigDecimal.ZERO) == 0)) {
            totalDebitAmount = totalDebitAmount.add(feesAmount);
            List<ChargePaymentDTO> chargePaymentDTOs = new ArrayList<>();
            for (ChargePaymentDTO chargePaymentDTO : loanTransactionDTO.getFeePayments()) {
                chargePaymentDTOs.add(new ChargePaymentDTO(chargePaymentDTO.getChargeId(),
                        chargePaymentDTO.getAmount().floatValue() < 0 ? chargePaymentDTO.getAmount().multiply(new BigDecimal(-1))
                                : chargePaymentDTO.getAmount(),
                        chargePaymentDTO.getLoanChargeId()));
            }
            this.helper.createCreditJournalEntryOrReversalForLoanCharges(office, currencyCode,
                    CashAccountsForLoan.INCOME_FROM_FEES.getValue(), loanProductId, loanId, transactionId, transactionDate, feesAmount,
                    !isReversal, chargePaymentDTOs);
        }
        if (penaltiesAmount != null && !(penaltiesAmount.compareTo(BigDecimal.ZERO) == 0)) {
            totalDebitAmount = totalDebitAmount.add(penaltiesAmount);
            List<ChargePaymentDTO> chargePaymentDTOs = new ArrayList<>();
            for (ChargePaymentDTO chargePaymentDTO : loanTransactionDTO.getPenaltyPayments()) {
                chargePaymentDTOs.add(new ChargePaymentDTO(chargePaymentDTO.getChargeId(),
                        chargePaymentDTO.getAmount().floatValue() < 0 ? chargePaymentDTO.getAmount().multiply(new BigDecimal(-1))
                                : chargePaymentDTO.getAmount(),
                        chargePaymentDTO.getLoanChargeId()));
            }
            this.helper.createCreditJournalEntryOrReversalForLoanCharges(office, currencyCode,
                    CashAccountsForLoan.INCOME_FROM_PENALTIES.getValue(), loanProductId, loanId, transactionId, transactionDate,
                    penaltiesAmount, !isReversal, chargePaymentDTOs);
        }
        if (overPaymentAmount != null && !(overPaymentAmount.compareTo(BigDecimal.ZERO) == 0)) {
            totalDebitAmount = totalDebitAmount.add(overPaymentAmount);
            this.helper.createCreditJournalEntryOrReversalForLoan(office, currencyCode, CashAccountsForLoan.OVERPAYMENT, loanProductId,
                    paymentTypeId, loanId, transactionId, transactionDate, overPaymentAmount, !isReversal);
        }
        this.helper.createDebitJournalEntryOrReversalForLoan(office, currencyCode, CashAccountsForLoan.FUND_SOURCE.getValue(),
                loanProductId, paymentTypeId, loanId, transactionId, transactionDate, totalDebitAmount, !isReversal);
    }
}
