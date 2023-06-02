
package org.apache.fineract.portfolio.collectionsheet.data;
import java.math.BigDecimal;
import org.apache.fineract.organisation.monetary.data.CurrencyData;
public final class SavingsDueData {
    @SuppressWarnings("unused")
    private final Long savingsId;
    @SuppressWarnings("unused")
    private final String accountId;
    @SuppressWarnings("unused")
    private final Integer accountStatusId;
    private final String productName;
    private final Long productId;
    @SuppressWarnings("unused")
    private final CurrencyData currency;
    @SuppressWarnings("unused")
    private BigDecimal dueAmount = BigDecimal.ZERO;
    @SuppressWarnings("unused")
    private String depositAccountType;
    public static SavingsDueData instance(final Long savingsId, final String accountId, final Integer accountStatusId,
            final String productName, final Long productId, final CurrencyData currency, final BigDecimal dueAmount,
            final String depositAccountType) {
        return new SavingsDueData(savingsId, accountId, accountStatusId, productName, productId, currency, dueAmount, depositAccountType);
    }
    private SavingsDueData(final Long savingsId, final String accountId, final Integer accountStatusId, final String productName,
            final Long productId, final CurrencyData currency, final BigDecimal dueAmount, final String depositAccountType) {
        this.savingsId = savingsId;
        this.accountId = accountId;
        this.accountStatusId = accountStatusId;
        this.productName = productName;
        this.productId = productId;
        this.currency = currency;
        this.dueAmount = dueAmount;
        this.depositAccountType = depositAccountType;
    }
    public String productName() {
        return this.productName;
    }
    public Long productId() {
        return this.productId;
    }
    public String getDepositAccountType() {
        return depositAccountType;
    }
}
