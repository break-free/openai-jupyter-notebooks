
package org.apache.fineract.portfolio.loanaccount.data;
import java.math.BigDecimal;
public class LoanChargePaidByData {
    private final Long id;
    private final BigDecimal amount;
    private final Integer installmentNumber;
    private final Long chargeId;
    private final Long transactionId;
    private final String name;
    public LoanChargePaidByData(final Long id, final BigDecimal amount, final Integer installmentNumber, final Long chargeId,
            final Long transactionId, String name) {
        this.id = id;
        this.amount = amount;
        this.installmentNumber = installmentNumber;
        this.chargeId = chargeId;
        this.transactionId = transactionId;
        this.name = name;
    }
    public LoanChargePaidByData(final Long id, final BigDecimal amount, final Integer installmentNumber, final Long chargeId,
            final Long transactionId) {
        this.id = id;
        this.amount = amount;
        this.installmentNumber = installmentNumber;
        this.chargeId = chargeId;
        this.transactionId = transactionId;
        this.name = null;
    }
    public Long getId() {
        return this.id;
    }
    public BigDecimal getAmount() {
        return this.amount;
    }
    public Integer getInstallmentNumber() {
        return this.installmentNumber;
    }
    public Long getChargeId() {
        return this.chargeId;
    }
    public Long getTransactionId() {
        return this.transactionId;
    }
    public String getName() {
        return name;
    }
}
