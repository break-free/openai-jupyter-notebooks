
package org.apache.fineract.infrastructure.hooks.processor;
import org.apache.fineract.infrastructure.core.domain.FineractContext;
import org.apache.fineract.infrastructure.hooks.domain.Hook;
public interface HookProcessor {
    void process(Hook hook, String payload, String entityName, String actionName, FineractContext context) throws Exception;
}
