
package org.apache.fineract.accounting.provisioning.api;
import io.swagger.v3.oas.annotations.media.Schema;
final class ProvisioningEntriesApiResourceSwagger {
    private ProvisioningEntriesApiResourceSwagger() {
    }
    @Schema(description = "PostProvisioningEntriesRequest")
    public static final class PostProvisioningEntriesRequest {
        private PostProvisioningEntriesRequest() {
        }
        @Schema(example = "16 October 2015")
        public String date;
        @Schema(example = "en")
        public String locale;
        @Schema(example = "dd MMMM yyyy")
        public String dateFormat;
        @Schema(example = "true")
        public String createjournalentries;
        public String provisioningentry;
        public String entries;
    }
    @Schema(description = "PostProvisioningEntriesResponse")
    public static final class PostProvisioningEntriesResponse {
        private PostProvisioningEntriesResponse() {
        }
        @Schema(example = "1")
        public Long resourceId;
    }
    @Schema(description = "PutProvisioningEntriesRequest")
    public static final class PutProvisioningEntriesRequest {
        private PutProvisioningEntriesRequest() {
        }
        @Schema(example = "recreateprovisioningentry")
        public String command;
    }
    @Schema(description = "PutProvisioningEntriesResponse")
    public static final class PutProvisioningEntriesResponse {
        private PutProvisioningEntriesResponse() {
        }
        @Schema(example = "1")
        public Long resourceId;
    }
}
