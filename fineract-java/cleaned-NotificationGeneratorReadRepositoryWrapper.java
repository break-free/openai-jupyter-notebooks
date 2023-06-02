
package org.apache.fineract.notification.service;
import java.util.List;
import org.apache.fineract.notification.domain.Notification;
public interface NotificationGeneratorReadRepositoryWrapper {
    Notification findById(Long id);
    List<Notification> fetchAllNotifications();
    void delete(Long id);
}
