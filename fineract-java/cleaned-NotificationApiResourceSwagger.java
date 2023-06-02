
package org.apache.fineract.notification.api;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
final class NotificationApiResourceSwagger {
    private NotificationApiResourceSwagger() {}
    @Schema(description = "GetNotificationsResponse")
    public static final class GetNotificationsResponse {
        private GetNotificationsResponse() {}
        static final class GetNotification {
            private GetNotification() {}
            @Schema(example = "1")
            public Long id;
            @Schema(example = "a")
            public String objectType;
            @Schema(example = "1")
            public Long objectId;
            @Schema(example = "a")
            public String action;
            @Schema(example = "1")
            public Long actorId;
            @Schema(example = "a")
            public String content;
            @Schema(example = "true")
            public boolean isRead;
            @Schema(example = "true")
            public boolean isSystemGenerated;
            @Schema(example = "a")
            public String tenantIdentifier;
            @Schema(example = "a")
            public String createdAt;
            @Schema(example = "1")
            public Long officeId;
            @Schema(example = "[]")
            public List<Long> userIds;
        }
        @Schema(example = "10")
        public int totalFilteredRecords;
        public List<GetNotification> pageItems;
    }
}
