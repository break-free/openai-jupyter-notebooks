
package org.apache.fineract.infrastructure.sms.data;
public class SmsMessageDeliveryReportData {
    private Long id;
    private String externalId;
    private String addedOnDate;
    private String deliveredOnDate;
    private Integer deliveryStatus;
    private Boolean hasError;
    private String errorMessage;
    private SmsMessageDeliveryReportData(Long id, String externalId, String addedOnDate, String deliveredOnDate, Integer deliveryStatus,
            Boolean hasError, String errorMessage) {
        this.id = id;
        this.externalId = externalId;
        this.addedOnDate = addedOnDate;
        this.deliveredOnDate = deliveredOnDate;
        this.deliveryStatus = deliveryStatus;
        this.hasError = hasError;
        this.errorMessage = errorMessage;
    }
    protected SmsMessageDeliveryReportData() {}
    public static SmsMessageDeliveryReportData getInstance(Long id, String externalId, String addedOnDate, String deliveredOnDate,
            Integer deliveryStatus, Boolean hasError, String errorMessage) {
        return new SmsMessageDeliveryReportData(id, externalId, addedOnDate, deliveredOnDate, deliveryStatus, hasError, errorMessage);
    }
    public Long getId() {
        return id;
    }
    public String getExternalId() {
        return externalId;
    }
    public String getAddedOnDate() {
        return addedOnDate;
    }
    public String getDeliveredOnDate() {
        return deliveredOnDate;
    }
    public Integer getDeliveryStatus() {
        return deliveryStatus;
    }
    public Boolean getHasError() {
        return hasError;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
}
