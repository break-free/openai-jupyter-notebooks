
package org.apache.fineract.portfolio.loanaccount.rescheduleloan.data;
import java.time.LocalDate;
import java.util.Collection;
import org.apache.fineract.infrastructure.codes.data.CodeValueData;
import org.apache.fineract.portfolio.loanaccount.data.LoanTermVariationsData;
public final class LoanRescheduleRequestData {
    private final Long id;
    private final Long loanId;
    private final Long clientId;
    private final String clientName;
    private final String loanAccountNumber;
    private final LoanRescheduleRequestStatusEnumData statusEnum;
    private final Integer rescheduleFromInstallment;
    private final LocalDate rescheduleFromDate;
    private final Boolean recalculateInterest;
    private final CodeValueData rescheduleReasonCodeValue;
    private final LoanRescheduleRequestTimelineData timeline;
    private final String rescheduleReasonComment;
    @SuppressWarnings("unused")
    private final Collection<CodeValueData> rescheduleReasons;
    @SuppressWarnings("unused")
    private final Collection<LoanTermVariationsData> loanTermVariationsData;
    private LoanRescheduleRequestData(Long id, Long loanId, LoanRescheduleRequestStatusEnumData statusEnum,
            Integer rescheduleFromInstallment, LocalDate rescheduleFromDate, CodeValueData rescheduleReasonCodeValue,
            String rescheduleReasonComment, LoanRescheduleRequestTimelineData timeline, final String clientName,
            final String loanAccountNumber, final Long clientId, final Boolean recalculateInterest,
            Collection<CodeValueData> rescheduleReasons, final Collection<LoanTermVariationsData> loanTermVariationsData) {
        this.id = id;
        this.loanId = loanId;
        this.statusEnum = statusEnum;
        this.rescheduleFromInstallment = rescheduleFromInstallment;
        this.rescheduleFromDate = rescheduleFromDate;
        this.rescheduleReasonCodeValue = rescheduleReasonCodeValue;
        this.rescheduleReasonComment = rescheduleReasonComment;
        this.timeline = timeline;
        this.clientName = clientName;
        this.loanAccountNumber = loanAccountNumber;
        this.clientId = clientId;
        this.recalculateInterest = recalculateInterest;
        this.rescheduleReasons = rescheduleReasons;
        this.loanTermVariationsData = loanTermVariationsData;
    }
    public static LoanRescheduleRequestData instance(Long id, Long loanId, LoanRescheduleRequestStatusEnumData statusEnum,
            Integer rescheduleFromInstallment, LocalDate rescheduleFromDate, CodeValueData rescheduleReasonCodeValue,
            String rescheduleReasonComment, LoanRescheduleRequestTimelineData timeline, final String clientName,
            final String loanAccountNumber, final Long clientId, final Boolean recalculateInterest,
            Collection<CodeValueData> rescheduleReasons, final Collection<LoanTermVariationsData> loanTermVariationsData) {
        return new LoanRescheduleRequestData(id, loanId, statusEnum, rescheduleFromInstallment, rescheduleFromDate,
                rescheduleReasonCodeValue, rescheduleReasonComment, timeline, clientName, loanAccountNumber, clientId, recalculateInterest,
                rescheduleReasons, loanTermVariationsData);
    }
    private LoanRescheduleRequestData(Long id, Long loanId, LoanRescheduleRequestStatusEnumData statusEnum, final String clientName,
            final String loanAccountNumber, final Long clientId, final LocalDate rescheduleFromDate,
            final CodeValueData rescheduleReasonCodeValue) {
        this.id = id;
        this.loanId = loanId;
        this.statusEnum = statusEnum;
        this.clientName = clientName;
        this.loanAccountNumber = loanAccountNumber;
        this.clientId = clientId;
        this.rescheduleFromDate = rescheduleFromDate;
        this.rescheduleReasonCodeValue = rescheduleReasonCodeValue;
        this.rescheduleFromInstallment = null;
        this.rescheduleReasonComment = null;
        this.timeline = null;
        this.recalculateInterest = null;
        this.rescheduleReasons = null;
        this.loanTermVariationsData = null;
    }
    public static LoanRescheduleRequestData instance(Long id, Long loanId, LoanRescheduleRequestStatusEnumData statusEnum,
            final String clientName, final String loanAccountNumber, final Long clientId, final LocalDate rescheduleFromDate,
            final CodeValueData rescheduleReasonCodeValue) {
        return new LoanRescheduleRequestData(id, loanId, statusEnum, clientName, loanAccountNumber, clientId, rescheduleFromDate,
                rescheduleReasonCodeValue);
    }
    public Long getId() {
        return id;
    }
    public Long getLoanId() {
        return loanId;
    }
    public LoanRescheduleRequestStatusEnumData getStatusEnum() {
        return statusEnum;
    }
    public Integer getRescheduleFromInstallment() {
        return rescheduleFromInstallment;
    }
    public LocalDate getRescheduleFromDate() {
        return rescheduleFromDate;
    }
    public CodeValueData getRescheduleReasonCodeValueId() {
        return rescheduleReasonCodeValue;
    }
    public String getRescheduleReasonComment() {
        return rescheduleReasonComment;
    }
    public LoanRescheduleRequestTimelineData getTimeline() {
        return this.timeline;
    }
    public String getClientName() {
        return clientName;
    }
    public String getLoanAccountNumber() {
        return loanAccountNumber;
    }
    public Long getClientId() {
        return clientId;
    }
    public Boolean getRecalculateInterest() {
        boolean value = false;
        if (recalculateInterest != null) {
            value = recalculateInterest;
        }
        return value;
    }
}
