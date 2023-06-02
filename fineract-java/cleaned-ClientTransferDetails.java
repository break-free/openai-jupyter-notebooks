
package org.apache.fineract.portfolio.client.domain;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
@SuppressWarnings("serial")
@Entity
@Table(name = "m_client_transfer_details")
public class ClientTransferDetails extends AbstractPersistableCustom {
    @Column(name = "client_id", length = 20, unique = true, nullable = false)
    private Long clientId;
    @Column(name = "from_office_id", nullable = false)
    private Long fromOfficeId;
    @Column(name = "to_office_id", nullable = false)
    private Long toOfficeId;
    @Column(name = "proposed_transfer_date", nullable = true)
    private LocalDate proposedTransferDate;
    @Column(name = "transfer_type", nullable = false)
    private Integer transferEventType;
    @Column(name = "submitted_on", nullable = false)
    private LocalDate submittedOn;
    @Column(name = "submitted_by", nullable = false)
    private Long submittedBy;
    protected ClientTransferDetails() {}
    private ClientTransferDetails(final Long clientId, final Long fromOfficeId, final Long toOfficeId, final LocalDate proposedTransferDate,
            final Integer transferEventType, final LocalDate submittedOn, final Long submittedBy) {
        this.clientId = clientId;
        this.fromOfficeId = fromOfficeId;
        this.toOfficeId = toOfficeId;
        this.proposedTransferDate = proposedTransferDate;
        this.transferEventType = transferEventType;
        this.submittedOn = submittedOn;
        this.submittedBy = submittedBy;
    }
    public static ClientTransferDetails instance(final Long clientId, final Long fromOfficeId, final Long toOfficeId,
            final LocalDate proposedTransferDate, final Integer transferEventType, final LocalDate submittedOn, final Long submittedBy) {
        return new ClientTransferDetails(clientId, fromOfficeId, toOfficeId, proposedTransferDate, transferEventType, submittedOn,
                submittedBy);
    }
}
