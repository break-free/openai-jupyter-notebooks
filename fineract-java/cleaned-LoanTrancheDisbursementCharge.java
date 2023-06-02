
package org.apache.fineract.portfolio.loanaccount.domain;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
@Entity
@Table(name = "m_loan_tranche_disbursement_charge")
public class LoanTrancheDisbursementCharge extends AbstractPersistableCustom {
    @ManyToOne
    @JoinColumn(name = "loan_charge_id", referencedColumnName = "id", nullable = false)
    private LoanCharge loancharge;
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "disbursement_detail_id", referencedColumnName = "id", nullable = false)
    private LoanDisbursementDetails loanDisbursementDetails;
    public LoanTrancheDisbursementCharge() {
    }
    public LoanTrancheDisbursementCharge(final LoanCharge loancharge, final LoanDisbursementDetails loanDisbursementDetails) {
        this.loancharge = loancharge;
        this.loanDisbursementDetails = loanDisbursementDetails;
    }
    public LoanDisbursementDetails getloanDisbursementDetails() {
        return this.loanDisbursementDetails;
    }
}
