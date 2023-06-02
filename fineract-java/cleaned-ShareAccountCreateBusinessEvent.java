
package org.apache.fineract.portfolio.businessevent.domain.share;
import org.apache.fineract.portfolio.shareaccounts.domain.ShareAccount;
public class ShareAccountCreateBusinessEvent extends ShareAccountBusinessEvent {
    public ShareAccountCreateBusinessEvent(ShareAccount value) {
        super(value);
    }
}
