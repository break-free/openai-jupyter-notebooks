
package org.apache.fineract.infrastructure.sms.data;
import com.google.gson.Gson;
import java.util.Collection;
public class SmsMessageApiQueueResourceData {
    private Long internalId;
    private String tenantId;
    private String createdOnDate;
    private String sourceAddress;
    private String mobileNumber;
    private String message;
    private Long providerId;
    private SmsMessageApiQueueResourceData(Long internalId, String mifosTenantIdentifier, String createdOnDate, String sourceAddress,
            String mobileNumber, String message, Long providerId) {
        this.internalId = internalId;
        this.tenantId = mifosTenantIdentifier;
        this.createdOnDate = createdOnDate;
        this.sourceAddress = sourceAddress;
        this.mobileNumber = mobileNumber;
        this.message = message;
        this.providerId = providerId;
    }
    protected SmsMessageApiQueueResourceData() {}
    public static final SmsMessageApiQueueResourceData instance(Long internalId, String mifosTenantIdentifier, String createdOnDate,
            String sourceAddress, String mobileNumber, String message, Long providerId) {
        return new SmsMessageApiQueueResourceData(internalId, mifosTenantIdentifier, createdOnDate, sourceAddress, mobileNumber, message,
                providerId);
    }
    public Long getInternalId() {
        return internalId;
    }
    public String getTenantId() {
        return tenantId;
    }
    public String getCreatedOnDate() {
        return createdOnDate;
    }
    public String getSourceAddress() {
        return sourceAddress;
    }
    public String getMobileNumber() {
        return mobileNumber;
    }
    public String getMessage() {
        return message;
    }
    public Long getproviderId() {
        return providerId;
    }
    public String toJsonString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    public static String toJsonString(Collection<SmsMessageApiQueueResourceData> smsResourceData) {
        Gson gson = new Gson();
        return gson.toJson(smsResourceData);
    }
}
