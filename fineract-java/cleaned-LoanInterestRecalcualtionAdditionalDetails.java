
package org.apache.fineract.portfolio.loanaccount.domain;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
@Entity
@Table(name = "m_loan_interest_recalculation_additional_details")
public class LoanInterestRecalcualtionAdditionalDetails extends AbstractPersistableCustom {
    @ManyToOne(optional = false)
    @JoinColumn(name = "loan_repayment_schedule_id", nullable = false)
    private LoanRepaymentScheduleInstallment loanRepaymentScheduleInstallment;
    @Column(name = "effective_date")
    private LocalDate effectiveDate;
    @Column(name = "amount", scale = 6, precision = 19, nullable = false)
    private BigDecimal amount;
    protected LoanInterestRecalcualtionAdditionalDetails() {
    }
    public LoanInterestRecalcualtionAdditionalDetails(final LocalDate effectiveDate, final BigDecimal amount) {
        if (effectiveDate != null) {
            this.effectiveDate = effectiveDate;
        }
        this.amount = amount;
    }
    public LocalDate getEffectiveDate() {
        return this.effectiveDate;
    }
    public BigDecimal getAmount() {
        return this.amount;
    }
    public LoanRepaymentScheduleInstallment getLoanRepaymentScheduleInstallment() {
        return loanRepaymentScheduleInstallment;
    }
    public void setLoanRepaymentScheduleInstallment(LoanRepaymentScheduleInstallment loanRepaymentScheduleInstallment) {
        this.loanRepaymentScheduleInstallment = loanRepaymentScheduleInstallment;
    }
}
