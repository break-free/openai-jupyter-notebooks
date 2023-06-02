
package org.apache.fineract.notification.data;
import java.io.Serializable;
import java.util.Set;
import lombok.Data;
@Data
public class NotificationData implements Serializable {
    private Long id;
    private String objectType;
    private Long objectId;
    private String action;
    private Long actorId;
    private String content;
    private boolean isRead;
    private boolean isSystemGenerated;
    private String tenantIdentifier;
    private String createdAt;
    private Long officeId;
    private Set<Long> userIds;
    public NotificationData() {}
    public NotificationData(String objectType, Long objectId, String action, Long actorId, String content, boolean isSystemGenerated,
            boolean isRead, String tenantIdentifier, Long officeId, Set<Long> userIds) {
        this.objectType = objectType;
        this.objectId = objectId;
        this.action = action;
        this.actorId = actorId;
        this.content = content;
        this.isRead = isRead;
        this.isSystemGenerated = isSystemGenerated;
        this.tenantIdentifier = tenantIdentifier;
        this.officeId = officeId;
        this.userIds = userIds;
    }
}
