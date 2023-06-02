
package org.apache.fineract.portfolio.businessevent.domain.share;
import org.apache.fineract.portfolio.businessevent.domain.AbstractBusinessEvent;
import org.apache.fineract.portfolio.shareaccounts.domain.ShareAccount;
public abstract class ShareAccountBusinessEvent extends AbstractBusinessEvent<ShareAccount> {
    public ShareAccountBusinessEvent(ShareAccount value) {
        super(value);
    }
}
