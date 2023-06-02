
package org.apache.fineract.accounting.journalentry.domain;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface JournalEntryRepository
        extends JpaRepository<JournalEntry, Long>, JpaSpecificationExecutor<JournalEntry>, JournalEntryRepositoryCustom {
    @Query("select journalEntry from JournalEntry journalEntry where journalEntry.transactionId= :transactionId and journalEntry.reversed=false and journalEntry.manualEntry=true")
    List<JournalEntry> findUnReversedManualJournalEntriesByTransactionId(@Param("transactionId") String transactionId);
    @Query("select DISTINCT j.transactionId from JournalEntry j where j.transactionId not in (select DISTINCT je.transactionId from JournalEntry je where je.glAccount.id = :contraId)")
    List<String> findNonContraTansactionIds(@Param("contraId") Long contraId);
    @Query("select DISTINCT j.transactionId from JournalEntry j where j.office.id = :officeId and j.glAccount.id = :contraId and j.reversed=false and j.transactionId not in (select DISTINCT je.reversalJournalEntry.transactionId from JournalEntry je where je.reversed=true)")
    List<String> findNonReversedContraTansactionIds(@Param("contraId") Long contraId, @Param("officeId") Long officeId);
    @Query("select journalEntry from JournalEntry journalEntry where journalEntry.entityId= :entityId and journalEntry.entityType = :entityType")
    List<JournalEntry> findProvisioningJournalEntriesByEntityId(@Param("entityId") Long entityId, @Param("entityType") Integer entityType);
    @Query("select journalEntry from JournalEntry journalEntry where journalEntry.transactionId= :transactionId and journalEntry.reversed=false and journalEntry.entityType = :entityType")
    List<JournalEntry> findJournalEntries(@Param("transactionId") String transactionId, @Param("entityType") Integer entityType);
}
