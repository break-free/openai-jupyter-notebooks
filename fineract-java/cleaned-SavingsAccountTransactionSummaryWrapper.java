
package org.apache.fineract.portfolio.savings.domain;
import java.math.BigDecimal;
import java.util.List;
import org.apache.fineract.organisation.monetary.data.CurrencyData;
import org.apache.fineract.organisation.monetary.domain.MonetaryCurrency;
import org.apache.fineract.organisation.monetary.domain.Money;
import org.apache.fineract.portfolio.savings.data.SavingsAccountTransactionData;
import org.springframework.stereotype.Component;
@Component
public final class SavingsAccountTransactionSummaryWrapper {
    public BigDecimal calculateTotalDeposits(final MonetaryCurrency currency, final List<SavingsAccountTransaction> transactions) {
        Money total = Money.zero(currency);
        for (final SavingsAccountTransaction transaction : transactions) {
            if ((transaction.isDepositAndNotReversed() || transaction.isDividendPayoutAndNotReversed())
                    && !transaction.isReversalTransaction()) {
                total = total.plus(transaction.getAmount(currency));
            }
        }
        return total.getAmountDefaultedToNullIfZero();
    }
    public BigDecimal calculateTotalDeposits(final CurrencyData currency, final List<SavingsAccountTransactionData> transactions) {
        Money total = Money.zero(currency);
        for (final SavingsAccountTransactionData transaction : transactions) {
            if (transaction.isDepositAndNotReversed() || transaction.isDividendPayoutAndNotReversed()) {
                total = total.plus(transaction.getAmount());
            }
        }
        return total.getAmountDefaultedToNullIfZero();
    }
    public BigDecimal calculateTotalWithdrawals(final MonetaryCurrency currency, final List<SavingsAccountTransaction> transactions) {
        Money total = Money.zero(currency);
        for (final SavingsAccountTransaction transaction : transactions) {
            if (transaction.isWithdrawal() && transaction.isNotReversed() && !transaction.isReversalTransaction()) {
                total = total.plus(transaction.getAmount(currency));
            }
        }
        return total.getAmountDefaultedToNullIfZero();
    }
    public BigDecimal calculateTotalWithdrawals(final CurrencyData currency, final List<SavingsAccountTransactionData> transactions) {
        Money total = Money.zero(currency);
        for (final SavingsAccountTransactionData transaction : transactions) {
            if (transaction.isWithdrawal() && transaction.isNotReversed() && !transaction.isReversalTransaction()) {
                total = total.plus(transaction.getAmount());
            }
        }
        return total.getAmountDefaultedToNullIfZero();
    }
    public BigDecimal calculateTotalInterestPosted(final MonetaryCurrency currency, final List<SavingsAccountTransaction> transactions) {
        Money total = Money.zero(currency);
        for (final SavingsAccountTransaction transaction : transactions) {
            if (transaction.isInterestPostingAndNotReversed() && transaction.isNotReversed() && !transaction.isReversalTransaction()) {
                total = total.plus(transaction.getAmount(currency));
            }
        }
        return total.getAmountDefaultedToNullIfZero();
    }
    public BigDecimal calculateTotalInterestPosted(final CurrencyData currency, final List<SavingsAccountTransactionData> transactions) {
        Money total = Money.zero(currency);
        for (final SavingsAccountTransactionData transaction : transactions) {
            if (transaction.isInterestPostingAndNotReversed() && transaction.isNotReversed() && !transaction.isReversalTransaction()) {
                total = total.plus(transaction.getAmount());
            }
        }
        return total.getAmountDefaultedToNullIfZero();
    }
    public BigDecimal calculateTotalInterestPosted(final MonetaryCurrency currency, final BigDecimal currentInterestPosted,
            final List<SavingsAccountTransaction> savingsAccountTransactions) {
        Money total = Money.of(currency, currentInterestPosted);
        for (final SavingsAccountTransaction transaction : savingsAccountTransactions) {
            if (transaction.isInterestPostingAndNotReversed() && transaction.isNotReversed() && !transaction.isReversalTransaction()) {
                total = total.plus(transaction.getAmount(currency));
            }
        }
        return total.getAmountDefaultedToNullIfZero();
    }
    public BigDecimal calculateTotalWithdrawalFees(final MonetaryCurrency currency, final List<SavingsAccountTransaction> transactions) {
        Money total = Money.zero(currency);
        for (final SavingsAccountTransaction transaction : transactions) {
            if (transaction.isWithdrawalFeeAndNotReversed() && transaction.isNotReversed() && !transaction.isReversalTransaction()) {
                total = total.plus(transaction.getAmount(currency));
            }
        }
        return total.getAmountDefaultedToNullIfZero();
    }
    public BigDecimal calculateTotalWithdrawalFees(final CurrencyData currency, final List<SavingsAccountTransactionData> transactions) {
        Money total = Money.zero(currency);
        for (final SavingsAccountTransactionData transaction : transactions) {
            if (transaction.isWithdrawalFeeAndNotReversed() && transaction.isNotReversed() && !transaction.isReversalTransaction()) {
                total = total.plus(transaction.getAmount());
            }
        }
        return total.getAmountDefaultedToNullIfZero();
    }
    public BigDecimal calculateTotalAnnualFees(final MonetaryCurrency currency, final List<SavingsAccountTransaction> transactions) {
        Money total = Money.zero(currency);
        for (final SavingsAccountTransaction transaction : transactions) {
            if (transaction.isAnnualFeeAndNotReversed() && transaction.isNotReversed() && !transaction.isReversalTransaction()) {
                total = total.plus(transaction.getAmount(currency));
            }
        }
        return total.getAmountDefaultedToNullIfZero();
    }
    public BigDecimal calculateTotalAnnualFees(final CurrencyData currency, final List<SavingsAccountTransactionData> transactions) {
        Money total = Money.zero(currency);
        for (final SavingsAccountTransactionData transaction : transactions) {
            if (transaction.isAnnualFeeAndNotReversed() && transaction.isNotReversed() && !transaction.isReversalTransaction()) {
                total = total.plus(transaction.getAmount());
            }
        }
        return total.getAmountDefaultedToNullIfZero();
    }
    public BigDecimal calculateTotalFeesCharge(final MonetaryCurrency currency, final List<SavingsAccountTransaction> transactions) {
        Money total = Money.zero(currency);
        for (final SavingsAccountTransaction transaction : transactions) {
            if (transaction.isFeeChargeAndNotReversed() && !transaction.isReversalTransaction()) {
                total = total.plus(transaction.getAmount(currency));
            }
        }
        return total.getAmountDefaultedToNullIfZero();
    }
    public BigDecimal calculateTotalFeesCharge(final CurrencyData currency, final List<SavingsAccountTransactionData> transactions) {
        Money total = Money.zero(currency);
        for (final SavingsAccountTransactionData transaction : transactions) {
            if (transaction.isFeeChargeAndNotReversed() && !transaction.isReversalTransaction()) {
                total = total.plus(transaction.getAmount());
            }
        }
        return total.getAmountDefaultedToNullIfZero();
    }
    public BigDecimal calculateTotalFeesChargeWaived(final MonetaryCurrency currency, final List<SavingsAccountTransaction> transactions) {
        Money total = Money.zero(currency);
        for (final SavingsAccountTransaction transaction : transactions) {
            if (transaction.isWaiveFeeChargeAndNotReversed() && !transaction.isReversalTransaction()) {
                total = total.plus(transaction.getAmount(currency));
            }
        }
        return total.getAmountDefaultedToNullIfZero();
    }
    public BigDecimal calculateTotalFeesChargeWaived(final CurrencyData currency, final List<SavingsAccountTransactionData> transactions) {
        Money total = Money.zero(currency);
        for (final SavingsAccountTransactionData transaction : transactions) {
            if (transaction.isWaiveFeeChargeAndNotReversed() && !transaction.isReversalTransaction()) {
                total = total.plus(transaction.getAmount());
            }
        }
        return total.getAmountDefaultedToNullIfZero();
    }
    public BigDecimal calculateTotalPenaltyCharge(final MonetaryCurrency currency, final List<SavingsAccountTransaction> transactions) {
        Money total = Money.zero(currency);
        for (final SavingsAccountTransaction transaction : transactions) {
            if (transaction.isPenaltyChargeAndNotReversed() && !transaction.isReversalTransaction()) {
                total = total.plus(transaction.getAmount(currency));
            }
        }
        return total.getAmountDefaultedToNullIfZero();
    }
    public BigDecimal calculateTotalPenaltyCharge(final CurrencyData currency, final List<SavingsAccountTransactionData> transactions) {
        Money total = Money.zero(currency);
        for (final SavingsAccountTransactionData transaction : transactions) {
            if (transaction.isPenaltyChargeAndNotReversed() && !transaction.isReversalTransaction()) {
                total = total.plus(transaction.getAmount());
            }
        }
        return total.getAmountDefaultedToNullIfZero();
    }
    public BigDecimal calculateTotalPenaltyChargeWaived(final MonetaryCurrency currency,
            final List<SavingsAccountTransaction> transactions) {
        Money total = Money.zero(currency);
        for (final SavingsAccountTransaction transaction : transactions) {
            if (transaction.isWaivePenaltyChargeAndNotReversed() && !transaction.isReversalTransaction()) {
                total = total.plus(transaction.getAmount(currency));
            }
        }
        return total.getAmountDefaultedToNullIfZero();
    }
    public BigDecimal calculateTotalPenaltyChargeWaived(final CurrencyData currency,
            final List<SavingsAccountTransactionData> transactions) {
        Money total = Money.zero(currency);
        for (final SavingsAccountTransactionData transaction : transactions) {
            if (transaction.isWaivePenaltyChargeAndNotReversed() && !transaction.isReversalTransaction()) {
                total = total.plus(transaction.getAmount());
            }
        }
        return total.getAmountDefaultedToNullIfZero();
    }
    public BigDecimal calculateTotalOverdraftInterest(MonetaryCurrency currency, BigDecimal overdraftPosted,
            List<SavingsAccountTransaction> transactions) {
        Money total = Money.of(currency, overdraftPosted);
        for (final SavingsAccountTransaction transaction : transactions) {
            if (transaction.isOverdraftInterestAndNotReversed() && !transaction.isReversalTransaction()) {
                total = total.plus(transaction.getAmount(currency));
            }
        }
        return total.getAmountDefaultedToNullIfZero();
    }
    public BigDecimal calculateTotalOverdraftInterest(MonetaryCurrency currency, List<SavingsAccountTransaction> transactions) {
        Money total = Money.zero(currency);
        for (final SavingsAccountTransaction transaction : transactions) {
            if (transaction.isOverdraftInterestAndNotReversed() && !transaction.isReversalTransaction()) {
                total = total.plus(transaction.getAmount(currency));
            }
        }
        return total.getAmountDefaultedToNullIfZero();
    }
    public BigDecimal calculateTotalOverdraftInterest(CurrencyData currency, List<SavingsAccountTransactionData> transactions) {
        Money total = Money.zero(currency);
        for (final SavingsAccountTransactionData transaction : transactions) {
            if (transaction.isOverdraftInterestAndNotReversed() && !transaction.isReversalTransaction()) {
                total = total.plus(transaction.getAmount());
            }
        }
        return total.getAmountDefaultedToNullIfZero();
    }
    public BigDecimal calculateTotalWithholdTaxWithdrawal(MonetaryCurrency currency, List<SavingsAccountTransaction> transactions) {
        Money total = Money.zero(currency);
        for (final SavingsAccountTransaction transaction : transactions) {
            if (transaction.isWithHoldTaxAndNotReversed() && !transaction.isReversalTransaction()) {
                total = total.plus(transaction.getAmount(currency));
            }
        }
        return total.getAmountDefaultedToNullIfZero();
    }
    public BigDecimal calculateTotalWithholdTaxWithdrawal(CurrencyData currency, List<SavingsAccountTransactionData> transactions) {
        Money total = Money.zero(currency);
        for (final SavingsAccountTransactionData transaction : transactions) {
            if (transaction.isWithHoldTaxAndNotReversed() && !transaction.isReversalTransaction()) {
                total = total.plus(transaction.getAmount());
            }
        }
        return total.getAmountDefaultedToNullIfZero();
    }
}
