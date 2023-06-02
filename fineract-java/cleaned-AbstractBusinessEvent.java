
package org.apache.fineract.portfolio.businessevent.domain;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public abstract class AbstractBusinessEvent<T> implements BusinessEvent<T> {
    private final T value;
    @Override
    public T get() {
        return value;
    }
}
