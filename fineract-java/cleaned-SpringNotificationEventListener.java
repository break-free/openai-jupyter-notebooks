
package org.apache.fineract.notification.eventandlistener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.fineract.infrastructure.core.service.ThreadLocalContextUtil;
import org.apache.fineract.notification.data.NotificationData;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
@Component
@Profile("!activeMqEnabled")
@RequiredArgsConstructor
@Slf4j
public class SpringNotificationEventListener implements ApplicationListener<NotificationEvent> {
    private final NotificationEventListener notificationEventListener;
    @Override
    public void onApplicationEvent(NotificationEvent event) {
        log.debug("Processing Spring notification event {}", event);
        ThreadLocalContextUtil.init(event.getContext());
        NotificationData notificationData = event.getNotificationData();
        notificationEventListener.receive(notificationData);
    }
}
