
package org.apache.fineract.commands.api;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import org.apache.fineract.commands.data.ProcessingResultLookup;
import org.apache.fineract.useradministration.data.AppUserData;
final class MakercheckersApiResourceSwagger {
    private MakercheckersApiResourceSwagger() {
    }
    @Schema(description = "GetMakerCheckerResponse")
    public static final class GetMakerCheckerResponse {
        private GetMakerCheckerResponse() {
        }
        public Long id;
        public String actionName;
        public String entityName;
        public Long resourceId;
        public Long subresourceId;
        public String maker;
        public ZonedDateTime madeOnDate;
        public String checker;
        public ZonedDateTime checkedOnDate;
        public String processingResult;
        public String commandAsJson;
        public String officeName;
        public String groupLevelName;
        public String groupName;
        public String clientName;
        public String loanAccountNo;
        public String savingsAccountNo;
        public Long clientId;
        public Long loanId;
        public String url;
    }
    @Schema(description = "GetMakerCheckersSearchTemplateResponse")
    public static final class GetMakerCheckersSearchTemplateResponse {
        private GetMakerCheckersSearchTemplateResponse() {
        }
        public Collection<AppUserData> appUsers;
        public List<String> actionNames;
        public List<String> entityNames;
        public Collection<ProcessingResultLookup> processingResults;
    }
    @Schema(description = "PostMakerCheckersResponse")
    public static final class PostMakerCheckersResponse {
        private PostMakerCheckersResponse() {
        }
        @Schema(example = "1")
        public Long auditId;
    }
}
