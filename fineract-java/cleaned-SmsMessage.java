
package org.apache.fineract.infrastructure.sms.domain;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.infrastructure.campaigns.sms.domain.SmsCampaign;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.infrastructure.core.service.DateUtils;
import org.apache.fineract.infrastructure.sms.SmsApiConstants;
import org.apache.fineract.organisation.staff.domain.Staff;
import org.apache.fineract.portfolio.client.domain.Client;
import org.apache.fineract.portfolio.group.domain.Group;
@Entity
@Table(name = "sms_messages_outbound")
public class SmsMessage extends AbstractPersistableCustom {
    @Column(name = "external_id", nullable = true)
    private String externalId;
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = true)
    private Group group;
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = true)
    private Client client;
    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = true)
    private Staff staff;
    @ManyToOne
    @JoinColumn(name = "campaign_id", nullable = true)
    private SmsCampaign smsCampaign;
    @Column(name = "status_enum", nullable = false)
    private Integer statusType;
    @Column(name = "mobile_no", nullable = true, length = 50)
    private String mobileNo;
    @Column(name = "message", nullable = false)
    private String message;
    @Column(name = "submittedon_date", nullable = true)
    private LocalDate submittedOnDate;
    @Column(name = "delivered_on_date", nullable = true)
    private LocalDateTime deliveredOnDate;
    @Column(name = "is_notification", nullable = true)
    private boolean isNotification;
    public static SmsMessage pendingSms(final String externalId, final Group group, final Client client, final Staff staff,
            final String message, final String mobileNo, final SmsCampaign smsCampaign, final boolean isNotification) {
        return new SmsMessage(externalId, group, client, staff, SmsMessageStatusType.PENDING, message, mobileNo, smsCampaign,
                isNotification);
    }
    public static SmsMessage sentSms(final String externalId, final Group group, final Client client, final Staff staff,
            final String message, final String mobileNo, final SmsCampaign smsCampaign, final boolean isNotification) {
        return new SmsMessage(externalId, group, client, staff, SmsMessageStatusType.WAITING_FOR_DELIVERY_REPORT, message, mobileNo,
                smsCampaign, isNotification);
    }
    public static SmsMessage instance(String externalId, final Group group, final Client client, final Staff staff,
            final SmsMessageStatusType statusType, final String message, final String mobileNo, final SmsCampaign smsCampaign,
            final boolean isNotification) {
        return new SmsMessage(externalId, group, client, staff, statusType, message, mobileNo, smsCampaign, isNotification);
    }
    protected SmsMessage() {
    }
    private SmsMessage(String externalId, final Group group, final Client client, final Staff staff, final SmsMessageStatusType statusType,
            final String message, final String mobileNo, final SmsCampaign smsCampaign, final boolean isNotification) {
        this.externalId = externalId;
        this.group = group;
        this.client = client;
        this.staff = staff;
        this.statusType = statusType.getValue();
        this.mobileNo = mobileNo;
        this.message = message;
        this.smsCampaign = smsCampaign;
        this.submittedOnDate = DateUtils.getBusinessLocalDate();
        this.isNotification = isNotification;
    }
    public Map<String, Object> update(final JsonCommand command) {
        final Map<String, Object> actualChanges = new LinkedHashMap<>(1);
        if (command.isChangeInStringParameterNamed(SmsApiConstants.messageParamName, this.message)) {
            final String newValue = command.stringValueOfParameterNamed(SmsApiConstants.messageParamName);
            actualChanges.put(SmsApiConstants.messageParamName, newValue);
            this.message = StringUtils.defaultIfEmpty(newValue, null);
        }
        return actualChanges;
    }
    public String getExternalId() {
        return this.externalId;
    }
    public SmsCampaign getSmsCampaign() {
        return this.smsCampaign;
    }
    public Group getGroup() {
        return group;
    }
    public Client getClient() {
        return client;
    }
    public Staff getStaff() {
        return staff;
    }
    public Integer getStatusType() {
        return statusType;
    }
    public String getMobileNo() {
        return mobileNo;
    }
    public String getMessage() {
        return message;
    }
    public void setExternalId(final String externalId) {
        this.externalId = externalId;
    }
    public void setStatusType(final Integer statusType) {
        this.statusType = statusType;
    }
    public LocalDate getSubmittedOnDate() {
        return this.submittedOnDate;
    }
    public LocalDateTime getDeliveredOnDate() {
        return this.deliveredOnDate;
    }
    public void setDeliveredOnDate(final LocalDateTime deliveredOnDate) {
        this.deliveredOnDate = deliveredOnDate;
    }
    public boolean isNotification() {
        return this.isNotification;
    }
    public void setNotification(boolean isNotification) {
        this.isNotification = isNotification;
    }
}
