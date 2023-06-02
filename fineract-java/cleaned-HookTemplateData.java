
package org.apache.fineract.infrastructure.hooks.data;
import java.io.Serializable;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
@SuppressWarnings("unused")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class HookTemplateData implements Serializable {
    private final Long id;
    private final String name;
    private final List<Field> schema;
    public static HookTemplateData instance(final Long id, final String name, final List<Field> schema) {
        return new HookTemplateData(id, name, schema);
    }
    public Long getServiceId() {
        return id;
    }
}
