
package org.apache.fineract.portfolio.note.data;
import java.time.ZonedDateTime;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
public class NoteData {
    @SuppressWarnings("unused")
    private final Long id;
    @SuppressWarnings("unused")
    private final Long clientId;
    @SuppressWarnings("unused")
    private final Long groupId;
    @SuppressWarnings("unused")
    private final Long loanId;
    @SuppressWarnings("unused")
    private final Long loanTransactionId;
    @SuppressWarnings("unused")
    private final Long depositAccountId;
    @SuppressWarnings("unused")
    private final Long savingAccountId;
    @SuppressWarnings("unused")
    private final EnumOptionData noteType;
    @SuppressWarnings("unused")
    private final String note;
    @SuppressWarnings("unused")
    private final Long createdById;
    @SuppressWarnings("unused")
    private final String createdByUsername;
    @SuppressWarnings("unused")
    private final ZonedDateTime createdOn;
    @SuppressWarnings("unused")
    private final Long updatedById;
    @SuppressWarnings("unused")
    private final String updatedByUsername;
    @SuppressWarnings("unused")
    private final ZonedDateTime updatedOn;
    public NoteData(final Long id, final Long clientId, final Long groupId, final Long loanId, final Long transactionId,
            final Long depositAccountId, final Long savingAccountId, final EnumOptionData noteType, final String note,
            final ZonedDateTime createdDate, final Long createdById, final String createdByUsername, final ZonedDateTime lastModifiedDate,
            final Long lastModifiedById, final String updatedByUsername) {
        this.id = id;
        this.clientId = clientId;
        this.groupId = groupId;
        this.loanId = loanId;
        this.loanTransactionId = transactionId;
        this.depositAccountId = depositAccountId;
        this.savingAccountId = savingAccountId;
        this.noteType = noteType;
        this.note = note;
        this.createdOn = createdDate;
        this.createdById = createdById;
        this.createdByUsername = createdByUsername;
        this.updatedOn = lastModifiedDate;
        this.updatedById = lastModifiedById;
        this.updatedByUsername = updatedByUsername;
    }
}
