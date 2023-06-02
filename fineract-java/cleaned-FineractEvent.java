
package org.apache.fineract.infrastructure.core.domain;
import java.io.Serializable;
import org.springframework.context.ApplicationEvent;
public abstract class FineractEvent extends ApplicationEvent implements ContextHolder, Serializable {
    private final FineractContext context;
    public FineractEvent(Object source, FineractContext context) {
        super(source);
        this.context = context;
    }
    @Override
    public FineractContext getContext() {
        return context;
    }
}
