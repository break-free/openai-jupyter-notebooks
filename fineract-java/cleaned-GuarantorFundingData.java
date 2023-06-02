
package org.apache.fineract.portfolio.loanaccount.guarantor.data;
import java.math.BigDecimal;
import java.util.Collection;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.portfolio.account.data.PortfolioAccountData;
public final class GuarantorFundingData {
    @SuppressWarnings("unused")
    private final Long id;
    @SuppressWarnings("unused")
    private final EnumOptionData status;
    @SuppressWarnings("unused")
    private final PortfolioAccountData savingsAccount;
    @SuppressWarnings("unused")
    private final BigDecimal amount;
    @SuppressWarnings("unused")
    private final BigDecimal amountReleased;
    @SuppressWarnings("unused")
    private final BigDecimal amountRemaining;
    @SuppressWarnings("unused")
    private final BigDecimal amountTransfered;
    @SuppressWarnings("unused")
    private final Collection<GuarantorTransactionData> guarantorTransactions;
    private GuarantorFundingData(final Long id, final EnumOptionData status, final PortfolioAccountData savingsAccount,
            final BigDecimal amount, final BigDecimal amountReleased, final BigDecimal amountRemaining, final BigDecimal amountTransfered,
            final Collection<GuarantorTransactionData> guarantorTransactions) {
        this.id = id;
        this.status = status;
        this.savingsAccount = savingsAccount;
        this.amount = amount;
        this.amountReleased = amountReleased;
        this.amountRemaining = amountRemaining;
        this.amountTransfered = amountTransfered;
        this.guarantorTransactions = guarantorTransactions;
    }
    public static GuarantorFundingData instance(final Long id, final EnumOptionData status, final PortfolioAccountData savingsAccount,
            final BigDecimal amount, final BigDecimal amountReleased, final BigDecimal amountRemaining, final BigDecimal amountTransfered,
            final Collection<GuarantorTransactionData> guarantorTransactions) {
        return new GuarantorFundingData(id, status, savingsAccount, amount, amountReleased, amountRemaining, amountTransfered,
                guarantorTransactions);
    }
}
