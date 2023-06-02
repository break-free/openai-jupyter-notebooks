
package org.apache.fineract.notification.service;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.notification.domain.Notification;
import org.apache.fineract.notification.domain.NotificationRepository;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class NotificationGeneratorWritePlatformServiceImpl implements NotificationGeneratorWritePlatformService {
    private final NotificationRepository notificationRepository;
    @Override
    public Long create(Notification notification) {
        this.notificationRepository.saveAndFlush(notification);
        return notification.getId();
    }
}
