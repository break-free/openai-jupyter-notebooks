
package org.apache.fineract.infrastructure.hooks.data;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public final class Event implements Serializable {
    private final String actionName;
    private final String entityName;
    public static Event instance(final String actionName, final String entityName) {
        return new Event(actionName, entityName);
    }
}
