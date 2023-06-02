
package org.apache.fineract.notification.eventandlistener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.fineract.infrastructure.core.service.ThreadLocalContextUtil;
import org.apache.fineract.notification.data.NotificationData;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
@Service
@Profile("!activeMqEnabled")
@RequiredArgsConstructor
@Slf4j
public class SpringNotificationEventPublisher implements NotificationEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;
    @Override
    public void broadcastNotification(final NotificationData notificationData) {
        log.debug("Sending Spring notification event: {}", notificationData);
        NotificationEvent event = new NotificationEvent(SpringNotificationEventPublisher.class, notificationData,
                ThreadLocalContextUtil.getContext());
        applicationEventPublisher.publishEvent(event);
    }
}
