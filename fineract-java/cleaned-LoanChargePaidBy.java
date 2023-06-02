
package org.apache.fineract.portfolio.loanaccount.domain;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
@Entity
@Table(name = "m_loan_charge_paid_by")
public class LoanChargePaidBy extends AbstractPersistableCustom {
    @ManyToOne
    @JoinColumn(name = "loan_transaction_id", nullable = false)
    private LoanTransaction loanTransaction;
    @ManyToOne(optional = false)
    @JoinColumn(name = "loan_charge_id", nullable = false)
    private LoanCharge loanCharge;
    @Column(name = "amount", scale = 6, precision = 19, nullable = false)
    private BigDecimal amount;
    @Column(name = "installment_number", nullable = true)
    private Integer installmentNumber;
    protected LoanChargePaidBy() {
    }
    public LoanChargePaidBy(final LoanTransaction loanTransaction, final LoanCharge loanCharge, final BigDecimal amount,
            Integer installmentNumber) {
        this.loanTransaction = loanTransaction;
        this.loanCharge = loanCharge;
        this.amount = amount;
        this.installmentNumber = installmentNumber;
    }
    public LoanTransaction getLoanTransaction() {
        return this.loanTransaction;
    }
    public void setLoanTransaction(final LoanTransaction loanTransaction) {
        this.loanTransaction = loanTransaction;
    }
    public LoanCharge getLoanCharge() {
        return this.loanCharge;
    }
    public void setLoanCharge(final LoanCharge loanCharge) {
        this.loanCharge = loanCharge;
    }
    public BigDecimal getAmount() {
        return this.amount;
    }
    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }
    public Integer getInstallmentNumber() {
        return this.installmentNumber;
    }
}
