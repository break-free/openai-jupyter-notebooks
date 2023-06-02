
package org.apache.fineract.notification.eventandlistener;
import org.apache.fineract.notification.data.NotificationData;
public interface NotificationEventPublisher {
    void broadcastNotification(NotificationData notificationData);
}
