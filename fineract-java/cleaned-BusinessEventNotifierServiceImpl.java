
package org.apache.fineract.portfolio.businessevent.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.fineract.portfolio.businessevent.BusinessEventListener;
import org.apache.fineract.portfolio.businessevent.domain.BusinessEvent;
import org.springframework.stereotype.Service;
@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BusinessEventNotifierServiceImpl implements BusinessEventNotifierService {
    private final Map<Class, List<BusinessEventListener>> preListeners = new HashMap<>();
    private final Map<Class, List<BusinessEventListener>> postListeners = new HashMap<>();
    @Override
    public void notifyPreBusinessEvent(BusinessEvent<?> businessEvent) {
        List<BusinessEventListener> businessEventListeners = preListeners.get(businessEvent.getClass());
        if (businessEventListeners != null) {
            for (BusinessEventListener eventListener : businessEventListeners) {
                eventListener.onBusinessEvent(businessEvent);
            }
        }
    }
    @Override
    public <T extends BusinessEvent<?>> void addPreBusinessEventListener(Class<T> eventType, BusinessEventListener<T> listener) {
        List<BusinessEventListener> businessEventListeners = preListeners.get(eventType);
        if (businessEventListeners == null) {
            businessEventListeners = new ArrayList<>();
            preListeners.put(eventType, businessEventListeners);
        }
        businessEventListeners.add(listener);
    }
    @Override
    public void notifyPostBusinessEvent(BusinessEvent<?> businessEvent) {
        List<BusinessEventListener> businessEventListeners = postListeners.get(businessEvent.getClass());
        if (businessEventListeners != null) {
            for (BusinessEventListener eventListener : businessEventListeners) {
                eventListener.onBusinessEvent(businessEvent);
            }
        }
    }
    @Override
    public <T extends BusinessEvent<?>> void addPostBusinessEventListener(Class<T> eventType, BusinessEventListener<T> listener) {
        List<BusinessEventListener> businessEventListeners = postListeners.get(eventType);
        if (businessEventListeners == null) {
            businessEventListeners = new ArrayList<>();
            postListeners.put(eventType, businessEventListeners);
        }
        businessEventListeners.add(listener);
    }
}
