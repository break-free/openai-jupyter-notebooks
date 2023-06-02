
package org.apache.fineract.notification.service;
import java.util.List;
import org.apache.fineract.notification.domain.NotificationMapper;
public interface NotificationMapperReadRepositoryWrapper {
    NotificationMapper findById(Long id);
    List<NotificationMapper> fetchAllNotifications();
    void delete(Long id);
}
