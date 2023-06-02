
package org.apache.fineract.infrastructure.sms.data;
import com.google.gson.Gson;
import java.util.List;
public class SmsMessageApiReportResourceData {
    private List<Long> externalIds;
    private String mifosTenantIdentifier;
    private SmsMessageApiReportResourceData(List<Long> externalIds, String mifosTenantIdentifier) {
        this.externalIds = externalIds;
        this.mifosTenantIdentifier = mifosTenantIdentifier;
    }
    protected SmsMessageApiReportResourceData() {}
    public static final SmsMessageApiReportResourceData instance(List<Long> externalIds, String mifosTenantIdentifier) {
        return new SmsMessageApiReportResourceData(externalIds, mifosTenantIdentifier);
    }
    public List<Long> getExternalIds() {
        return externalIds;
    }
    public String getMifosTenantIdentifier() {
        return mifosTenantIdentifier;
    }
    public String toJsonString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
