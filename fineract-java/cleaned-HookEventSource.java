
package org.apache.fineract.infrastructure.hooks.event;
import java.io.Serializable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Getter
public class HookEventSource implements Serializable {
    private final String entityName;
    private final String actionName;
}
