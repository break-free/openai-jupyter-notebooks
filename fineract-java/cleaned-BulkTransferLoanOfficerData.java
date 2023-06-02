
package org.apache.fineract.organisation.staff.data;
import java.time.LocalDate;
import java.util.Collection;
import org.apache.fineract.organisation.office.data.OfficeData;
public final class BulkTransferLoanOfficerData {
    @SuppressWarnings("unused")
    private final Long officeId;
    @SuppressWarnings("unused")
    private final Long fromLoanOfficerId;
    @SuppressWarnings("unused")
    private final LocalDate assignmentDate;
    @SuppressWarnings("unused")
    private final Collection<OfficeData> officeOptions;
    @SuppressWarnings("unused")
    private final Collection<StaffData> loanOfficerOptions;
    @SuppressWarnings("unused")
    private final StaffAccountSummaryCollectionData accountSummaryCollection;
    public static BulkTransferLoanOfficerData templateForBulk(final Long officeId, final Long fromLoanOfficerId,
            final LocalDate assignmentDate, final Collection<OfficeData> officeOptions, final Collection<StaffData> loanOfficerOptions,
            final StaffAccountSummaryCollectionData accountSummaryCollection) {
        return new BulkTransferLoanOfficerData(officeId, fromLoanOfficerId, assignmentDate, officeOptions, loanOfficerOptions,
                accountSummaryCollection);
    }
    public static BulkTransferLoanOfficerData template(final Long fromLoanOfficerId, final Collection<StaffData> loanOfficerOptions,
            final LocalDate assignmentDate) {
        return new BulkTransferLoanOfficerData(null, fromLoanOfficerId, assignmentDate, null, loanOfficerOptions, null);
    }
    private BulkTransferLoanOfficerData(final Long officeId, final Long fromLoanOfficerId, final LocalDate assignmentDate,
            final Collection<OfficeData> officeOptions, final Collection<StaffData> loanOfficerOptions,
            final StaffAccountSummaryCollectionData accountSummaryCollection) {
        this.officeId = officeId;
        this.fromLoanOfficerId = fromLoanOfficerId;
        this.assignmentDate = assignmentDate;
        this.officeOptions = officeOptions;
        this.loanOfficerOptions = loanOfficerOptions;
        this.accountSummaryCollection = accountSummaryCollection;
    }
}
