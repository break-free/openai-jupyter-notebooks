
package org.apache.fineract.infrastructure.sms.scheduler;
import java.util.Collection;
import java.util.Map;
import org.apache.fineract.infrastructure.campaigns.sms.domain.SmsCampaign;
import org.apache.fineract.infrastructure.sms.domain.SmsMessage;
public interface SmsMessageScheduledJobService {
    void sendMessagesToGateway();
    void sendTriggeredMessages(Map<SmsCampaign, Collection<SmsMessage>> smsDataMap);
    void sendTriggeredMessage(Collection<SmsMessage> smsMessage, long providerId);
    void getDeliveryReports();
}
