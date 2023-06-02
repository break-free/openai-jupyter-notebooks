
package org.apache.fineract.portfolio.businessevent;
import org.apache.fineract.portfolio.businessevent.domain.BusinessEvent;
public interface BusinessEventListener<T extends BusinessEvent<?>> {
    void onBusinessEvent(T event);
}
