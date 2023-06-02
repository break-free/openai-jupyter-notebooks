
package org.apache.fineract.portfolio.loanaccount.rescheduleloan.domain;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.codes.domain.CodeValue;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRescheduleRequestToTermVariationMapping;
import org.apache.fineract.portfolio.loanaccount.domain.LoanStatus;
import org.apache.fineract.portfolio.loanaccount.domain.LoanTermVariations;
import org.apache.fineract.useradministration.domain.AppUser;
@Entity
@Table(name = "m_loan_reschedule_request")
public class LoanRescheduleRequest extends AbstractPersistableCustom {
    @ManyToOne
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;
    @Column(name = "status_enum", nullable = false)
    private Integer statusEnum;
    @Column(name = "reschedule_from_installment")
    private Integer rescheduleFromInstallment;
    @Column(name = "reschedule_from_date")
    private LocalDate rescheduleFromDate;
    @Column(name = "recalculate_interest")
    private Boolean recalculateInterest;
    @ManyToOne
    @JoinColumn(name = "reschedule_reason_cv_id")
    private CodeValue rescheduleReasonCodeValue;
    @Column(name = "reschedule_reason_comment")
    private String rescheduleReasonComment;
    @Column(name = "submitted_on_date")
    private LocalDate submittedOnDate;
    @ManyToOne
    @JoinColumn(name = "submitted_by_user_id")
    private AppUser submittedByUser;
    @Column(name = "approved_on_date")
    private LocalDate approvedOnDate;
    @ManyToOne
    @JoinColumn(name = "approved_by_user_id")
    private AppUser approvedByUser;
    @Column(name = "rejected_on_date")
    private LocalDate rejectedOnDate;
    @ManyToOne
    @JoinColumn(name = "rejected_by_user_id")
    private AppUser rejectedByUser;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "loanRescheduleRequest")
    private Set<LoanRescheduleRequestToTermVariationMapping> loanRescheduleRequestToTermVariationMappings = new HashSet<>();
    protected LoanRescheduleRequest() {}
    private LoanRescheduleRequest(final Loan loan, final Integer statusEnum, final Integer rescheduleFromInstallment,
            final LocalDate rescheduleFromDate, final Boolean recalculateInterest, final CodeValue rescheduleReasonCodeValue,
            final String rescheduleReasonComment, final LocalDate submittedOnDate, final AppUser submittedByUser,
            final LocalDate approvedOnDate, final AppUser approvedByUser, final LocalDate rejectedOnDate, AppUser rejectedByUser) {
        this.loan = loan;
        this.statusEnum = statusEnum;
        this.rescheduleFromInstallment = rescheduleFromInstallment;
        this.rescheduleFromDate = rescheduleFromDate;
        this.rescheduleReasonCodeValue = rescheduleReasonCodeValue;
        this.rescheduleReasonComment = rescheduleReasonComment;
        this.submittedOnDate = submittedOnDate;
        this.submittedByUser = submittedByUser;
        this.approvedOnDate = approvedOnDate;
        this.approvedByUser = approvedByUser;
        this.rejectedOnDate = rejectedOnDate;
        this.rejectedByUser = rejectedByUser;
        this.recalculateInterest = recalculateInterest;
    }
    public static LoanRescheduleRequest instance(final Loan loan, final Integer statusEnum, final Integer rescheduleFromInstallment,
            final LocalDate rescheduleFromDate, final Boolean recalculateInterest, final CodeValue rescheduleReasonCodeValue,
            final String rescheduleReasonComment, final LocalDate submittedOnDate, final AppUser submittedByUser,
            final LocalDate approvedOnDate, final AppUser approvedByUser, final LocalDate rejectedOnDate, AppUser rejectedByUser) {
        return new LoanRescheduleRequest(loan, statusEnum, rescheduleFromInstallment, rescheduleFromDate, recalculateInterest,
                rescheduleReasonCodeValue, rescheduleReasonComment, submittedOnDate, submittedByUser, approvedOnDate, approvedByUser,
                rejectedOnDate, rejectedByUser);
    }
    public Loan getLoan() {
        return this.loan;
    }
    public Integer getStatusEnum() {
        return this.statusEnum;
    }
    public Integer getRescheduleFromInstallment() {
        return this.rescheduleFromInstallment;
    }
    public LocalDate getRescheduleFromDate() {
        return this.rescheduleFromDate;
    }
    public CodeValue getRescheduleReasonCodeValue() {
        return this.rescheduleReasonCodeValue;
    }
    public String getRescheduleReasonComment() {
        return this.rescheduleReasonComment;
    }
    public LocalDate getSubmittedOnDate() {
        return this.submittedOnDate;
    }
    public AppUser getSubmittedByUser() {
        return this.submittedByUser;
    }
    public LocalDate getApprovedOnDate() {
        return this.approvedOnDate;
    }
    public AppUser getApprovedByUser() {
        return this.approvedByUser;
    }
    public LocalDate getRejectedOnDate() {
        return this.rejectedOnDate;
    }
    public Boolean getRecalculateInterest() {
        boolean recalculateInterest = false;
        if (this.recalculateInterest != null) {
            recalculateInterest = this.recalculateInterest;
        }
        return recalculateInterest;
    }
    public AppUser getRejectedByUser() {
        return this.rejectedByUser;
    }
    public void approve(final AppUser approvedByUser, final LocalDate approvedOnDate) {
        if (approvedOnDate != null) {
            this.approvedByUser = approvedByUser;
            this.approvedOnDate = approvedOnDate;
            this.statusEnum = LoanStatus.APPROVED.getValue();
        }
    }
    public void reject(final AppUser approvedByUser, final LocalDate approvedOnDate) {
        if (approvedOnDate != null) {
            this.rejectedByUser = approvedByUser;
            this.rejectedOnDate = approvedOnDate;
            this.statusEnum = LoanStatus.REJECTED.getValue();
        }
    }
    public void updateLoanRescheduleRequestToTermVariationMappings(final List<LoanRescheduleRequestToTermVariationMapping> mapping) {
        this.loanRescheduleRequestToTermVariationMappings.addAll(mapping);
    }
    public Set<LoanRescheduleRequestToTermVariationMapping> getLoanRescheduleRequestToTermVariationMappings() {
        return this.loanRescheduleRequestToTermVariationMappings;
    }
    public LoanTermVariations getDueDateTermVariationIfExists() {
        if (this.loanRescheduleRequestToTermVariationMappings != null && this.loanRescheduleRequestToTermVariationMappings.size() > 0) {
            for (LoanRescheduleRequestToTermVariationMapping mapping : this.loanRescheduleRequestToTermVariationMappings) {
                if (mapping.getLoanTermVariations().getTermType().isDueDateVariation()) {
                    return mapping.getLoanTermVariations();
                }
            }
        }
        return null;
    }
}
