
package org.apache.fineract.notification.eventandlistener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.infrastructure.core.config.EnableFineractEventListenerCondition;
import org.apache.fineract.notification.data.NotificationData;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;
@Component
@Profile("activeMqEnabled")
@Conditional(EnableFineractEventListenerCondition.class)
@RequiredArgsConstructor
public class ActiveMQNotificationEventListener implements SessionAwareMessageListener {
    private final NotificationEventListener notificationEventListener;
    @Override
    public void onMessage(Message message, Session session) throws JMSException {
        if (message instanceof ObjectMessage) {
            NotificationData notificationData = (NotificationData) ((ObjectMessage) message).getObject();
            notificationEventListener.receive(notificationData);
        }
    }
}
