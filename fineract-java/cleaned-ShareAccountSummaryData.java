
package org.apache.fineract.portfolio.accountdetails.data;
import java.io.Serializable;
import org.apache.fineract.organisation.monetary.data.CurrencyData;
import org.apache.fineract.portfolio.shareaccounts.data.ShareAccountApplicationTimelineData;
import org.apache.fineract.portfolio.shareaccounts.data.ShareAccountStatusEnumData;
@SuppressWarnings("unused")
public class ShareAccountSummaryData implements Serializable {
    private final Long id;
    private final String accountNo;
    private final Long totalApprovedShares;
    private final Long totalPendingForApprovalShares;
    private final String externalId;
    private final Long productId;
    private final String productName;
    private final String shortProductName;
    private final ShareAccountStatusEnumData status;
    private final CurrencyData currency;
    private final ShareAccountApplicationTimelineData timeline;
    public ShareAccountSummaryData(final Long id, final String accountNo, final String externalId, final Long productId,
            final String productName, final String shortProductName, final ShareAccountStatusEnumData status, final CurrencyData currency,
            final Long approvedShares, final Long pendingForApprovalShares, final ShareAccountApplicationTimelineData timeline) {
        this.id = id;
        this.accountNo = accountNo;
        this.externalId = externalId;
        if (approvedShares == null) {
            this.totalApprovedShares = Long.valueOf(0);
        } else {
            this.totalApprovedShares = approvedShares;
        }
        if (pendingForApprovalShares == null) {
            this.totalPendingForApprovalShares = Long.valueOf(0);
        } else {
            this.totalPendingForApprovalShares = pendingForApprovalShares;
        }
        this.productId = productId;
        this.productName = productName;
        this.shortProductName = shortProductName;
        this.status = status;
        this.currency = currency;
        this.timeline = timeline;
    }
    public String getAccountNo() {
        return this.accountNo;
    }
}
