
package org.apache.fineract.portfolio.loanaccount.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
@Entity
@Table(name = "m_loan_overdue_installment_charge")
public class LoanOverdueInstallmentCharge extends AbstractPersistableCustom {
    @ManyToOne(optional = false)
    @JoinColumn(name = "loan_charge_id", referencedColumnName = "id", nullable = false)
    private LoanCharge loancharge;
    @ManyToOne(optional = false)
    @JoinColumn(name = "loan_schedule_id", referencedColumnName = "id", nullable = false)
    private LoanRepaymentScheduleInstallment installment;
    @Column(name = "frequency_number")
    private Integer frequencyNumber;
    public LoanOverdueInstallmentCharge() {
    }
    public LoanOverdueInstallmentCharge(final LoanCharge loancharge, final LoanRepaymentScheduleInstallment installment,
            final Integer frequencyNumber) {
        this.loancharge = loancharge;
        this.installment = installment;
        this.frequencyNumber = frequencyNumber;
    }
    public void updateLoanRepaymentScheduleInstallment(LoanRepaymentScheduleInstallment loanRepaymentScheduleInstallment) {
        this.installment = loanRepaymentScheduleInstallment;
    }
    public LoanRepaymentScheduleInstallment getInstallment() {
        return this.installment;
    }
}
