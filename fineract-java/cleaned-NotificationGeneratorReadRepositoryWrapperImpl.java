
package org.apache.fineract.notification.service;
import java.util.List;
import org.apache.fineract.notification.domain.Notification;
import org.apache.fineract.notification.domain.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class NotificationGeneratorReadRepositoryWrapperImpl implements NotificationGeneratorReadRepositoryWrapper {
    private final NotificationRepository notificationRepository;
    @Autowired
    public NotificationGeneratorReadRepositoryWrapperImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
    @Override
    public Notification findById(Long id) {
        return this.notificationRepository.findById(id).orElse(null);
    }
    @Override
    public List<Notification> fetchAllNotifications() {
        return this.notificationRepository.findAll();
    }
    @Override
    public void delete(Long id) {
        this.notificationRepository.deleteById(id);
    }
}
