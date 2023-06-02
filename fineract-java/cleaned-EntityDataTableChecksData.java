
package org.apache.fineract.infrastructure.dataqueries.data;
import java.io.Serializable;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
public class EntityDataTableChecksData implements Serializable {
    private final long id;
    private final String entity;
    private final EnumOptionData status;
    private final String datatableName;
    private final boolean systemDefined;
    private final Long order;
    private final Long productId;
    private final String productName;
    public EntityDataTableChecksData(final long id, final String entity, final EnumOptionData status, final String datatableName,
            final boolean systemDefined, final Long loanProductId, final String productName) {
        this.id = id;
        this.entity = entity;
        this.status = status;
        this.datatableName = datatableName;
        this.systemDefined = systemDefined;
        this.order = id;
        this.productId = loanProductId;
        this.productName = productName;
    }
}
