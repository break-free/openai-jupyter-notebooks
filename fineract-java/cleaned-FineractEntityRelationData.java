
package org.apache.fineract.infrastructure.entityaccess.data;
import java.io.Serializable;
public class FineractEntityRelationData implements Serializable {
    @SuppressWarnings("unused")
    private final Long id;
    @SuppressWarnings("unused")
    private final Integer fromEntityType;
    @SuppressWarnings("unused")
    private final Integer toEntityType;
    @SuppressWarnings("unused")
    private final String mappingTypes;
    public FineractEntityRelationData(final Long id, final Integer fromEntityType, final Integer toEntityType, final String mappingTypes) {
        this.id = id;
        this.fromEntityType = fromEntityType;
        this.toEntityType = toEntityType;
        this.mappingTypes = mappingTypes;
    }
    public static FineractEntityRelationData getMappingTypes(final Long id, final String mappingTypes) {
        Integer fromEntityType = null;
        final Integer toEntityType = null;
        return new FineractEntityRelationData(id, fromEntityType, toEntityType, mappingTypes);
    }
}
