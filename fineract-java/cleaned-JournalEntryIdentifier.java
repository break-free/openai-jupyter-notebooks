
package org.apache.fineract.accounting.journalentry.data;
public class JournalEntryIdentifier {
    private String entityId;
    @SuppressWarnings("unused")
    private Long makerCheckerId;
    public static JournalEntryIdentifier makerChecker(final Long makerCheckerId) {
        return new JournalEntryIdentifier(null, makerCheckerId);
    }
    public static JournalEntryIdentifier makerChecker(final String resourceId, final Long makerCheckerId) {
        return new JournalEntryIdentifier(resourceId, makerCheckerId);
    }
    public JournalEntryIdentifier() {
    }
    public JournalEntryIdentifier(final String entityId) {
        this.entityId = entityId;
    }
    private JournalEntryIdentifier(final String entityId, final Long makerCheckerId) {
        this.entityId = entityId;
        this.makerCheckerId = makerCheckerId;
    }
    public String getEntityId() {
        return this.entityId;
    }
    public void setEntityId(final String entityId) {
        this.entityId = entityId;
    }
}
