
package org.apache.fineract.notification.service;
import java.util.Collection;
public interface NotificationWritePlatformService {
    Long notify(Long userId, String objectType, Long objectId, String action, Long actorId, String notificationContent,
            boolean isSystemGenerated);
    Long notify(Collection<Long> userIds, String objectType, Long objectId, String action, Long actorId, String notificationContent,
            boolean isSystemGenerated);
}
