
package org.apache.fineract.notification.service;
import java.util.List;
import org.apache.fineract.notification.domain.NotificationMapper;
import org.apache.fineract.notification.domain.NotificationMapperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class NotificationMapperReadRepositoryWrapperImpl implements NotificationMapperReadRepositoryWrapper {
    private final NotificationMapperRepository notificationMapperRepository;
    @Autowired
    public NotificationMapperReadRepositoryWrapperImpl(NotificationMapperRepository notificationMapperRepository) {
        this.notificationMapperRepository = notificationMapperRepository;
    }
    @Override
    public NotificationMapper findById(Long id) {
        return this.notificationMapperRepository.findById(id).orElse(null);
    }
    @Override
    public List<NotificationMapper> fetchAllNotifications() {
        return this.notificationMapperRepository.findAll();
    }
    @Override
    public void delete(Long id) {
        this.notificationMapperRepository.deleteById(id);
    }
}
