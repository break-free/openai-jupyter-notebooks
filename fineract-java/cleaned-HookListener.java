
package org.apache.fineract.infrastructure.hooks.listener;
import org.apache.fineract.infrastructure.hooks.event.HookEvent;
import org.springframework.context.ApplicationListener;
public interface HookListener extends ApplicationListener<HookEvent> {
}
