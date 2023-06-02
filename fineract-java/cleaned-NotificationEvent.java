
package org.apache.fineract.notification.eventandlistener;
import org.apache.fineract.infrastructure.core.domain.FineractContext;
import org.apache.fineract.infrastructure.core.domain.FineractEvent;
import org.apache.fineract.notification.data.NotificationData;
@SuppressWarnings("serial")
public class NotificationEvent extends FineractEvent {
    private NotificationData notificationData;
    public NotificationEvent(Object source, NotificationData notificationData, FineractContext context) {
        super(source, context);
        this.notificationData = notificationData;
    }
    public NotificationData getNotificationData() {
        return notificationData;
    }
}
