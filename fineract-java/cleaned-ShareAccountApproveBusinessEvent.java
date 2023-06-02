
package org.apache.fineract.portfolio.businessevent.domain.share;
import org.apache.fineract.portfolio.shareaccounts.domain.ShareAccount;
public class ShareAccountApproveBusinessEvent extends ShareAccountBusinessEvent {
    public ShareAccountApproveBusinessEvent(ShareAccount value) {
        super(value);
    }
}
