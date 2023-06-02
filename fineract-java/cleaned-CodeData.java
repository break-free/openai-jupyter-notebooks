
package org.apache.fineract.infrastructure.codes.data;
import java.io.Serializable;
public final class CodeData implements Serializable {
    private final Long id;
    @SuppressWarnings("unused")
    private final String name;
    @SuppressWarnings("unused")
    private final boolean systemDefined;
    public static CodeData instance(final Long id, final String name, final boolean systemDefined) {
        return new CodeData(id, name, systemDefined);
    }
    private CodeData(final Long id, final String name, final boolean systemDefined) {
        this.id = id;
        this.name = name;
        this.systemDefined = systemDefined;
    }
    public Long getCodeId() {
        return this.id;
    }
}
