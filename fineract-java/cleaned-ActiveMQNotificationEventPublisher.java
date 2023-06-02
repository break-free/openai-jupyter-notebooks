
package org.apache.fineract.notification.eventandlistener;
import javax.jms.Queue;
import lombok.RequiredArgsConstructor;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.fineract.infrastructure.core.config.EnableFineractEventsCondition;
import org.apache.fineract.notification.data.NotificationData;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
@Service
@Profile("activeMqEnabled")
@Conditional(EnableFineractEventsCondition.class)
@RequiredArgsConstructor
public class ActiveMQNotificationEventPublisher implements NotificationEventPublisher {
    private final JmsTemplate jmsTemplate;
    @Override
    public void broadcastNotification(NotificationData notificationData) {
        Queue queue = new ActiveMQQueue("NotificationQueue");
        this.jmsTemplate.send(queue, session -> session.createObjectMessage(notificationData));
    }
}
