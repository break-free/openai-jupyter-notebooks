
package org.apache.fineract.accounting.journalentry.api;
import io.swagger.v3.oas.annotations.media.Schema;
final class JournalEntriesApiResourceSwagger {
    private JournalEntriesApiResourceSwagger() {}
    @Schema(description = "PostJournalEntriesResponse")
    public static final class PostJournalEntriesResponse {
        private PostJournalEntriesResponse() {
        }
        @Schema(description = "1")
        public Long officeId;
        @Schema(description = "RS9MCISID4WK1ZM")
        public String transactionId;
    }
    @Schema(description = "PostJournalEntriesTransactionIdRequest")
    public static final class PostJournalEntriesTransactionIdRequest {
        private PostJournalEntriesTransactionIdRequest() {
        }
        @Schema(description = "1")
        public Long officeId;
    }
    @Schema(description = "PostJournalEntriesTransactionIdResponse")
    public static final class PostJournalEntriesTransactionIdResponse {
        private PostJournalEntriesTransactionIdResponse() {
        }
        @Schema(description = "1")
        public Long officeId;
    }
}
