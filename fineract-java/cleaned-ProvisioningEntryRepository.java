
package org.apache.fineract.accounting.provisioning.domain;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface ProvisioningEntryRepository extends JpaRepository<ProvisioningEntry, Long>, JpaSpecificationExecutor<ProvisioningEntry> {
    @Query("select entry1 from ProvisioningEntry entry1 where entry1.createdDate = :createdDate")
    ProvisioningEntry findByProvisioningEntryDate(@Param("createdDate") LocalDate createdDate);
    @Query("select entry1 from ProvisioningEntry entry1 where entry1.createdDate = (select max(entry2.createdDate) from ProvisioningEntry entry2 where entry2.isJournalEntryCreated=true)")
    ProvisioningEntry findExistingProvisioningEntryWithJournalEntries();
}
