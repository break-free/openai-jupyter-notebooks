
package org.apache.fineract.portfolio.loanaccount.domain;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.portfolio.charge.domain.Charge;
@Entity
@Table(name = "m_loan_tranche_charges")
public class LoanTrancheCharge extends AbstractPersistableCustom {
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "charge_id", nullable = false)
    private Charge charge;
    LoanTrancheCharge() {
    }
    LoanTrancheCharge(Charge chargeDefinition) {
        this.charge = chargeDefinition;
    }
    public LoanTrancheCharge(Charge charge, Loan loan) {
        this.charge = charge;
        this.loan = loan;
    }
    public static LoanTrancheCharge createLoanTrancheCharge(Charge chargeDefinition) {
        return new LoanTrancheCharge(chargeDefinition);
    }
    public static LoanTrancheCharge createLoanTrancheChargeWithLoan(Charge chargeDefinition, Loan loan) {
        return new LoanTrancheCharge(chargeDefinition, loan);
    }
    public Charge getCharge() {
        return charge;
    }
}
