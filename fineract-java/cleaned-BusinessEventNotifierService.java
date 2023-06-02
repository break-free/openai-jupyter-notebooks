
package org.apache.fineract.portfolio.businessevent.service;
import org.apache.fineract.portfolio.businessevent.BusinessEventListener;
import org.apache.fineract.portfolio.businessevent.domain.BusinessEvent;
public interface BusinessEventNotifierService {
    void notifyPreBusinessEvent(BusinessEvent<?> businessEvent);
    void notifyPostBusinessEvent(BusinessEvent<?> businessEvent);
    <T extends BusinessEvent<?>> void addPreBusinessEventListener(Class<T> eventType, BusinessEventListener<T> listener);
    <T extends BusinessEvent<?>> void addPostBusinessEventListener(Class<T> eventType, BusinessEventListener<T> listener);
}
