
package org.apache.fineract.notification.config;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.fineract.infrastructure.core.config.EnableFineractEventsCondition;
import org.apache.fineract.notification.eventandlistener.NotificationEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
@Configuration
@Profile("activeMqEnabled")
@Conditional(EnableFineractEventsCondition.class)
public class MessagingConfiguration {
    @Autowired
    private Environment env;
    @Autowired
    private NotificationEventListener notificationEventListener;
    @Bean
    public Logger loggerBean() {
        return LoggerFactory.getLogger(MessagingConfiguration.class);
    }
    private static final String DEFAULT_BROKER_URL = "tcp:
    @Bean
    public ActiveMQConnectionFactory amqConnectionFactory() {
        ActiveMQConnectionFactory amqConnectionFactory = new ActiveMQConnectionFactory(); 
        try {
            amqConnectionFactory.setBrokerURL(DEFAULT_BROKER_URL);
        } catch (Exception e) {
            amqConnectionFactory.setBrokerURL(this.env.getProperty("brokerUrl"));
        }
        return amqConnectionFactory;
    }
    @Bean
    public CachingConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(amqConnectionFactory());
    }
    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate;
        jmsTemplate = new JmsTemplate(connectionFactory());
        jmsTemplate.setConnectionFactory(connectionFactory());
        return jmsTemplate;
    }
    @Bean
    public DefaultMessageListenerContainer messageListenerContainer() {
        DefaultMessageListenerContainer messageListenerContainer = new DefaultMessageListenerContainer();
        messageListenerContainer.setConnectionFactory(connectionFactory());
        messageListenerContainer.setDestinationName("NotificationQueue");
        messageListenerContainer.setMessageListener(notificationEventListener);
        messageListenerContainer.setExceptionListener(new ExceptionListener() {
            @Override
            public void onException(JMSException jmse) {
                loggerBean().error("Network Error: ActiveMQ Broker Unavailable.");
                messageListenerContainer.shutdown();
            }
        });
        return messageListenerContainer;
    }
}
