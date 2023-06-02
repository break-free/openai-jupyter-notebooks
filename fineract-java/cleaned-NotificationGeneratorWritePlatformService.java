
package org.apache.fineract.notification.service;
import org.apache.fineract.notification.domain.Notification;
public interface NotificationGeneratorWritePlatformService {
    Long create(Notification notification);
}
