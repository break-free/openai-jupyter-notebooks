
package org.apache.fineract.infrastructure.sms.data;
import java.util.List;
public class SmsMessageApiResponseData {
    private Integer httpStatusCode;
    private List<SmsMessageDeliveryReportData> data;
    private SmsMessageApiResponseData(Integer httpStatusCode, List<SmsMessageDeliveryReportData> data) {
        this.httpStatusCode = httpStatusCode;
        this.data = data;
    }
    protected SmsMessageApiResponseData() {}
    public static SmsMessageApiResponseData getInstance(Integer httpStatusCode, List<SmsMessageDeliveryReportData> data) {
        return new SmsMessageApiResponseData(httpStatusCode, data);
    }
    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }
    public List<SmsMessageDeliveryReportData> getData() {
        return data;
    }
}
