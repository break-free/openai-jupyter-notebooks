
package org.apache.fineract.portfolio.businessevent.domain.share;
import org.apache.fineract.portfolio.businessevent.domain.AbstractBusinessEvent;
public class ShareProductDividentsCreateBusinessEvent extends AbstractBusinessEvent<Long> {
    public ShareProductDividentsCreateBusinessEvent(Long value) {
        super(value);
    }
}
