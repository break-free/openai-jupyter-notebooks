
package org.apache.fineract.integrationtests.common.shares;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class ShareAccountHelper {
    private static final Logger LOG = LoggerFactory.getLogger(ShareAccountHelper.class);
    private static final String LOCALE = "en_GB";
    private String clientId;
    private String productId;
    private String submittedDate;
    private String externalId;
    private String savingsAccountId;
    private String requestedShares;
    private String applicationDate;
    private String allowDividendCalculationForInactiveClients;
    private String minimumActivePeriod;
    private String minimumActivePeriodFrequencyType;
    private String lockinPeriodFrequency;
    private String lockinPeriodFrequencyType;
    private List<Map<String, Object>> charges = null;
    public String build() {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("locale", LOCALE);
        if (this.clientId != null) {
            map.put("clientId", this.clientId);
        }
        if (this.productId != null) {
            map.put("productId", this.productId);
        }
        map.put("dateFormat", "dd MMMM yyyy");
        if (this.savingsAccountId != null) {
            map.put("savingsAccountId", savingsAccountId);
        }
        if (externalId != null) {
            map.put("externalId", this.externalId);
        }
        if (submittedDate != null) {
            map.put("submittedDate", this.submittedDate);
        }
        if (applicationDate != null) {
            map.put("applicationDate", this.applicationDate);
        }
        if (this.requestedShares != null) {
            map.put("requestedShares", this.requestedShares);
        }
        if (this.allowDividendCalculationForInactiveClients != null) {
            map.put("allowDividendCalculationForInactiveClients", this.allowDividendCalculationForInactiveClients);
        }
        if (this.charges != null) {
            map.put("charges", this.charges);
        }
        String shareAccountCreateJson = new Gson().toJson(map);
        LOG.info("{}", shareAccountCreateJson);
        return shareAccountCreateJson;
    }
    public ShareAccountHelper withClientId(final String clientId) {
        this.clientId = clientId;
        return this;
    }
    public ShareAccountHelper withProductId(final String productId) {
        this.productId = productId;
        return this;
    }
    public ShareAccountHelper withSavingsAccountId(final String savingsAccountId) {
        this.savingsAccountId = savingsAccountId;
        return this;
    }
    public ShareAccountHelper withSubmittedDate(final String submittedDate) {
        this.submittedDate = submittedDate;
        return this;
    }
    public ShareAccountHelper withRequestedShares(final String requestedShares) {
        this.requestedShares = requestedShares;
        return this;
    }
    public ShareAccountHelper withApplicationDate(final String applicationDate) {
        this.applicationDate = applicationDate;
        return this;
    }
    public ShareAccountHelper withExternalId(final String externalId) {
        this.externalId = externalId;
        return this;
    }
    public ShareAccountHelper withCharges(final List<Map<String, Object>> charges) {
        this.charges = new ArrayList<>();
        this.charges.addAll(charges);
        return this;
    }
}
