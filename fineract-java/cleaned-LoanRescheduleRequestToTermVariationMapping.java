
package org.apache.fineract.portfolio.loanaccount.domain;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.portfolio.loanaccount.rescheduleloan.domain.LoanRescheduleRequest;
@Entity
@Table(name = "m_loan_reschedule_request_term_variations_mapping")
public class LoanRescheduleRequestToTermVariationMapping extends AbstractPersistableCustom {
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "loan_reschedule_request_id", nullable = false)
    private LoanRescheduleRequest loanRescheduleRequest;
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "loan_term_variations_id", nullable = false)
    private LoanTermVariations loanTermVariations;
    protected LoanRescheduleRequestToTermVariationMapping() {
    }
    private LoanRescheduleRequestToTermVariationMapping(final LoanRescheduleRequest loanRescheduleRequest,
            final LoanTermVariations loanTermVariations) {
        this.loanRescheduleRequest = loanRescheduleRequest;
        this.loanTermVariations = loanTermVariations;
    }
    public static LoanRescheduleRequestToTermVariationMapping createNew(final LoanRescheduleRequest loanRescheduleRequest,
            final LoanTermVariations loanTermVariation) {
        return new LoanRescheduleRequestToTermVariationMapping(loanRescheduleRequest, loanTermVariation);
    }
    public LoanTermVariations getLoanTermVariations() {
        return this.loanTermVariations;
    }
    public LoanRescheduleRequest getLoanRescheduleRequest() {
        return loanRescheduleRequest;
    }
}
