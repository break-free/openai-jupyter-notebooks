
package org.apache.fineract.accounting.provisioning.data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
@SuppressWarnings("unused")
public class ProvisioningEntryData {
    private Long id;
    private Boolean journalEntry;
    private Long createdById;
    private String createdUser;
    LocalDate createdDate;
    Long modifiedById;
    private String modifiedUser;
    private BigDecimal reservedAmount;
    private Collection<LoanProductProvisioningEntryData> provisioningEntries;
    public ProvisioningEntryData(final Long id, final Collection<LoanProductProvisioningEntryData> provisioningEntries) {
        this.provisioningEntries = provisioningEntries;
        this.id = id;
    }
    public ProvisioningEntryData(Long id, Boolean journalEntry, Long createdById, String createdUser, LocalDate createdDate,
            Long modifiedById, String modifiedUser, BigDecimal totalReservedAmount) {
        this.id = id;
        this.journalEntry = journalEntry;
        this.createdById = createdById;
        this.createdUser = createdUser;
        this.modifiedById = modifiedById;
        this.modifiedUser = modifiedUser;
        this.createdDate = createdDate;
        this.reservedAmount = totalReservedAmount;
    }
    public void setEntries(Collection<LoanProductProvisioningEntryData> provisioningEntries) {
        this.provisioningEntries = provisioningEntries;
    }
    public Long getId() {
        return this.id;
    }
    public LocalDate getCreatedDate() {
        return this.createdDate;
    }
}
