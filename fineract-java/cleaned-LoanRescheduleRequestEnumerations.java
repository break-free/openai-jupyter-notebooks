
package org.apache.fineract.portfolio.loanaccount.rescheduleloan.data;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.portfolio.loanaccount.domain.LoanStatus;
public final class LoanRescheduleRequestEnumerations {
    private LoanRescheduleRequestEnumerations() {
    }
    public static EnumOptionData status(final LoanRescheduleRequestStatusEnumData status) {
        Long id = status.id();
        String code = status.code();
        String value = status.value();
        return new EnumOptionData(id, code, value);
    }
    public static LoanRescheduleRequestStatusEnumData status(final Integer statusId) {
        return status(LoanStatus.fromInt(statusId));
    }
    public static LoanRescheduleRequestStatusEnumData status(final LoanStatus status) {
        LoanRescheduleRequestStatusEnumData enumData = new LoanRescheduleRequestStatusEnumData(
                LoanStatus.SUBMITTED_AND_PENDING_APPROVAL.getValue().longValue(), LoanStatus.SUBMITTED_AND_PENDING_APPROVAL.getCode(),
                "Submitted and pending approval");
        switch (status) {
            case SUBMITTED_AND_PENDING_APPROVAL:
                enumData = new LoanRescheduleRequestStatusEnumData(LoanStatus.SUBMITTED_AND_PENDING_APPROVAL.getValue().longValue(),
                        LoanStatus.SUBMITTED_AND_PENDING_APPROVAL.getCode(), "Submitted and pending approval");
            break;
            case APPROVED:
                enumData = new LoanRescheduleRequestStatusEnumData(LoanStatus.APPROVED.getValue().longValue(),
                        LoanStatus.APPROVED.getCode(), "Approved");
            break;
            case REJECTED:
                enumData = new LoanRescheduleRequestStatusEnumData(LoanStatus.REJECTED.getValue().longValue(),
                        LoanStatus.REJECTED.getCode(), "Rejected");
            break;
            default:
            break;
        }
        return enumData;
    }
}
