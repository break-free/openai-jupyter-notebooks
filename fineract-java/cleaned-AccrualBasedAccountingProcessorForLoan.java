
package org.apache.fineract.accounting.journalentry.service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.accounting.closure.domain.GLClosure;
import org.apache.fineract.accounting.common.AccountingConstants.AccrualAccountsForLoan;
import org.apache.fineract.accounting.common.AccountingConstants.CashAccountsForLoan;
import org.apache.fineract.accounting.common.AccountingConstants.FinancialActivity;
import org.apache.fineract.accounting.glaccount.domain.GLAccount;
import org.apache.fineract.accounting.journalentry.data.ChargePaymentDTO;
import org.apache.fineract.accounting.journalentry.data.LoanDTO;
import org.apache.fineract.accounting.journalentry.data.LoanTransactionDTO;
import org.apache.fineract.organisation.office.domain.Office;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class AccrualBasedAccountingProcessorForLoan implements AccountingProcessorForLoan {
    private final AccountingProcessorHelper helper;
    @Override
    public void createJournalEntriesForLoan(final LoanDTO loanDTO) {
        final GLClosure latestGLClosure = this.helper.getLatestClosureByBranch(loanDTO.getOfficeId());
        final Office office = this.helper.getOfficeById(loanDTO.getOfficeId());
        for (final LoanTransactionDTO loanTransactionDTO : loanDTO.getNewLoanTransactions()) {
            final LocalDate transactionDate = loanTransactionDTO.getTransactionDate();
            this.helper.checkForBranchClosures(latestGLClosure, transactionDate);
            if (loanTransactionDTO.getTransactionType().isDisbursement()) {
                createJournalEntriesForDisbursements(loanDTO, loanTransactionDTO, office);
            }
            if (loanTransactionDTO.getTransactionType().isAccrual()) {
                createJournalEntriesForAccruals(loanDTO, loanTransactionDTO, office);
            }
            else if (loanTransactionDTO.getTransactionType().isRepaymentType()
                    || loanTransactionDTO.getTransactionType().isRepaymentAtDisbursement()
                    || loanTransactionDTO.getTransactionType().isChargePayment()) {
                createJournalEntriesForRepaymentsAndWriteOffs(loanDTO, loanTransactionDTO, office, false,
                        loanTransactionDTO.getTransactionType().isRepaymentAtDisbursement());
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
            else if ((loanTransactionDTO.getTransactionType().isWriteOff() || loanTransactionDTO.getTransactionType().isWaiveInterest()
                    || loanTransactionDTO.getTransactionType().isWaiveCharges())) {
                createJournalEntriesForRepaymentsAndWriteOffs(loanDTO, loanTransactionDTO, office, true, false);
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
        final boolean isReversed = loanTransactionDTO.isReversed();
        final Long paymentTypeId = loanTransactionDTO.getPaymentTypeId();
        if (loanTransactionDTO.isLoanToLoanTransfer()) {
            this.helper.createAccrualBasedJournalEntriesAndReversalsForLoan(office, currencyCode,
                    AccrualAccountsForLoan.LOAN_PORTFOLIO.getValue(), FinancialActivity.ASSET_TRANSFER.getValue(), loanProductId,
                    paymentTypeId, loanId, transactionId, transactionDate, disbursalAmount, isReversed);
        } else if (loanTransactionDTO.isAccountTransfer()) {
            this.helper.createAccrualBasedJournalEntriesAndReversalsForLoan(office, currencyCode,
                    AccrualAccountsForLoan.LOAN_PORTFOLIO.getValue(), FinancialActivity.LIABILITY_TRANSFER.getValue(), loanProductId,
                    paymentTypeId, loanId, transactionId, transactionDate, disbursalAmount, isReversed);
        } else {
            this.helper.createAccrualBasedJournalEntriesAndReversalsForLoan(office, currencyCode,
                    AccrualAccountsForLoan.LOAN_PORTFOLIO.getValue(), AccrualAccountsForLoan.FUND_SOURCE.getValue(), loanProductId,
                    paymentTypeId, loanId, transactionId, transactionDate, disbursalAmount, isReversed);
        }
    }
    private void createJournalEntriesForRepaymentsAndWriteOffs(final LoanDTO loanDTO, final LoanTransactionDTO loanTransactionDTO,
            final Office office, final boolean writeOff, final boolean isIncomeFromFee) {
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
        final Long paymentTypeId = loanTransactionDTO.getPaymentTypeId();
        final boolean isReversal = loanTransactionDTO.isReversed();
        BigDecimal totalDebitAmount = new BigDecimal(0);
        Map<GLAccount, BigDecimal> accountMap = new HashMap<>();
        if (principalAmount != null && !(principalAmount.compareTo(BigDecimal.ZERO) == 0)) {
            totalDebitAmount = totalDebitAmount.add(principalAmount);
            GLAccount account = this.helper.getLinkedGLAccountForLoanProduct(loanProductId,
                    AccrualAccountsForLoan.LOAN_PORTFOLIO.getValue(), paymentTypeId);
            accountMap.put(account, principalAmount);
        }
        if (interestAmount != null && !(interestAmount.compareTo(BigDecimal.ZERO) == 0)) {
            totalDebitAmount = totalDebitAmount.add(interestAmount);
            GLAccount account = this.helper.getLinkedGLAccountForLoanProduct(loanProductId,
                    AccrualAccountsForLoan.INTEREST_RECEIVABLE.getValue(), paymentTypeId);
            if (accountMap.containsKey(account)) {
                BigDecimal amount = accountMap.get(account).add(interestAmount);
                accountMap.put(account, amount);
            } else {
                accountMap.put(account, interestAmount);
            }
        }
        if (feesAmount != null && !(feesAmount.compareTo(BigDecimal.ZERO) == 0)) {
            totalDebitAmount = totalDebitAmount.add(feesAmount);
            if (isIncomeFromFee) {
                this.helper.createCreditJournalEntryOrReversalForLoanCharges(office, currencyCode,
                        AccrualAccountsForLoan.INCOME_FROM_FEES.getValue(), loanProductId, loanId, transactionId, transactionDate,
                        feesAmount, isReversal, loanTransactionDTO.getFeePayments());
            } else {
                GLAccount account = this.helper.getLinkedGLAccountForLoanProduct(loanProductId,
                        AccrualAccountsForLoan.FEES_RECEIVABLE.getValue(), paymentTypeId);
                if (accountMap.containsKey(account)) {
                    BigDecimal amount = accountMap.get(account).add(feesAmount);
                    accountMap.put(account, amount);
                } else {
                    accountMap.put(account, feesAmount);
                }
            }
        }
        if (penaltiesAmount != null && !(penaltiesAmount.compareTo(BigDecimal.ZERO) == 0)) {
            totalDebitAmount = totalDebitAmount.add(penaltiesAmount);
            if (isIncomeFromFee) {
                GLAccount account = this.helper.getLinkedGLAccountForLoanProduct(loanProductId,
                        AccrualAccountsForLoan.INCOME_FROM_PENALTIES.getValue(), paymentTypeId);
                if (accountMap.containsKey(account)) {
                    BigDecimal amount = accountMap.get(account).add(penaltiesAmount);
                    accountMap.put(account, amount);
                } else {
                    accountMap.put(account, penaltiesAmount);
                }
            } else {
                GLAccount account = this.helper.getLinkedGLAccountForLoanProduct(loanProductId,
                        AccrualAccountsForLoan.PENALTIES_RECEIVABLE.getValue(), paymentTypeId);
                if (accountMap.containsKey(account)) {
                    BigDecimal amount = accountMap.get(account).add(penaltiesAmount);
                    accountMap.put(account, amount);
                } else {
                    accountMap.put(account, penaltiesAmount);
                }
            }
        }
        if (overPaymentAmount != null && !(overPaymentAmount.compareTo(BigDecimal.ZERO) == 0)) {
            totalDebitAmount = totalDebitAmount.add(overPaymentAmount);
            GLAccount account = this.helper.getLinkedGLAccountForLoanProduct(loanProductId, AccrualAccountsForLoan.OVERPAYMENT.getValue(),
                    paymentTypeId);
            if (accountMap.containsKey(account)) {
                BigDecimal amount = accountMap.get(account).add(overPaymentAmount);
                accountMap.put(account, amount);
            } else {
                accountMap.put(account, overPaymentAmount);
            }
        }
        for (Map.Entry<GLAccount, BigDecimal> entry : accountMap.entrySet()) {
            this.helper.createCreditJournalEntryOrReversalForLoan(office, currencyCode, loanId, transactionId, transactionDate,
                    entry.getValue(), isReversal, entry.getKey());
        }
        if (!(totalDebitAmount.compareTo(BigDecimal.ZERO) == 0)) {
            if (writeOff) {
                this.helper.createDebitJournalEntryOrReversalForLoan(office, currencyCode,
                        AccrualAccountsForLoan.LOSSES_WRITTEN_OFF.getValue(), loanProductId, paymentTypeId, loanId, transactionId,
                        transactionDate, totalDebitAmount, isReversal);
            } else {
                if (loanTransactionDTO.isLoanToLoanTransfer()) {
                    this.helper.createDebitJournalEntryOrReversalForLoan(office, currencyCode, FinancialActivity.ASSET_TRANSFER.getValue(),
                            loanProductId, paymentTypeId, loanId, transactionId, transactionDate, totalDebitAmount, isReversal);
                } else if (loanTransactionDTO.isAccountTransfer()) {
                    this.helper.createDebitJournalEntryOrReversalForLoan(office, currencyCode,
                            FinancialActivity.LIABILITY_TRANSFER.getValue(), loanProductId, paymentTypeId, loanId, transactionId,
                            transactionDate, totalDebitAmount, isReversal);
                } else {
                    if (loanTransactionDTO.getTransactionType().isGoodwillCredit()) {
                        this.helper.createDebitJournalEntryOrReversalForLoan(office, currencyCode,
                                AccrualAccountsForLoan.GOODWILL_CREDIT.getValue(), loanProductId, paymentTypeId, loanId, transactionId,
                                transactionDate, totalDebitAmount, isReversal);
                    } else {
                        this.helper.createDebitJournalEntryOrReversalForLoan(office, currencyCode,
                                AccrualAccountsForLoan.FUND_SOURCE.getValue(), loanProductId, paymentTypeId, loanId, transactionId,
                                transactionDate, totalDebitAmount, isReversal);
                    }
                }
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
        this.helper.createAccrualBasedJournalEntriesAndReversalsForLoan(office, currencyCode, AccrualAccountsForLoan.FUND_SOURCE.getValue(),
                AccrualAccountsForLoan.INCOME_FROM_RECOVERY.getValue(), loanProductId, paymentTypeId, loanId, transactionId,
                transactionDate, amount, isReversal);
    }
    private void createJournalEntriesForAccruals(final LoanDTO loanDTO, final LoanTransactionDTO loanTransactionDTO, final Office office) {
        final Long loanProductId = loanDTO.getLoanProductId();
        final Long loanId = loanDTO.getLoanId();
        final String currencyCode = loanDTO.getCurrencyCode();
        final String transactionId = loanTransactionDTO.getTransactionId();
        final LocalDate transactionDate = loanTransactionDTO.getTransactionDate();
        final BigDecimal interestAmount = loanTransactionDTO.getInterest();
        final BigDecimal feesAmount = loanTransactionDTO.getFees();
        final BigDecimal penaltiesAmount = loanTransactionDTO.getPenalties();
        final boolean isReversed = loanTransactionDTO.isReversed();
        final Long paymentTypeId = loanTransactionDTO.getPaymentTypeId();
        if (interestAmount != null && !(interestAmount.compareTo(BigDecimal.ZERO) == 0)) {
            this.helper.createAccrualBasedJournalEntriesAndReversalsForLoan(office, currencyCode,
                    AccrualAccountsForLoan.INTEREST_RECEIVABLE.getValue(), AccrualAccountsForLoan.INTEREST_ON_LOANS.getValue(),
                    loanProductId, paymentTypeId, loanId, transactionId, transactionDate, interestAmount, isReversed);
        }
        if (feesAmount != null && !(feesAmount.compareTo(BigDecimal.ZERO) == 0)) {
            this.helper.createAccrualBasedJournalEntriesAndReversalsForLoanCharges(office, currencyCode,
                    AccrualAccountsForLoan.FEES_RECEIVABLE.getValue(), AccrualAccountsForLoan.INCOME_FROM_FEES.getValue(), loanProductId,
                    loanId, transactionId, transactionDate, feesAmount, isReversed, loanTransactionDTO.getFeePayments());
        }
        if (penaltiesAmount != null && !(penaltiesAmount.compareTo(BigDecimal.ZERO) == 0)) {
            this.helper.createAccrualBasedJournalEntriesAndReversalsForLoanCharges(office, currencyCode,
                    AccrualAccountsForLoan.PENALTIES_RECEIVABLE.getValue(), AccrualAccountsForLoan.INCOME_FROM_PENALTIES.getValue(),
                    loanProductId, loanId, transactionId, transactionDate, penaltiesAmount, isReversed,
                    loanTransactionDTO.getPenaltyPayments());
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
        this.helper.createAccrualBasedJournalEntriesAndReversalsForLoan(office, currencyCode,
                AccrualAccountsForLoan.LOAN_PORTFOLIO.getValue(), AccrualAccountsForLoan.OVERPAYMENT.getValue(), loanProductId,
                paymentTypeId, loanId, transactionId, transactionDate, refundAmount, isReversal);
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
