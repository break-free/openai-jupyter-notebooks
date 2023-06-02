
package org.apache.fineract.accounting.journalentry.domain;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
@Repository
public class JournalEntryRepositoryImpl implements JournalEntryRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    @SuppressWarnings("unchecked")
    public List<JournalEntry> findFirstJournalEntryForAccount(final long glAccountId) {
        final List<JournalEntry> journalEntries = this.entityManager
                .createQuery("SELECT journalEntry FROM JournalEntry journalEntry where journalEntry.glAccount.id= :glAccountId")
                .setParameter("glAccountId", glAccountId).setFirstResult(0).setMaxResults(1).getResultList();
        return journalEntries;
    }
}
