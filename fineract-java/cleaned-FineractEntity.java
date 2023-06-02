
package org.apache.fineract.infrastructure.entityaccess.domain;
public class FineractEntity {
    private Long entityId;
    private FineractEntityType type;
    @SuppressWarnings("unused")
    private FineractEntity() {}
    public FineractEntity(Long entityId, FineractEntityType type) {
        this.entityId = entityId;
        this.type = type;
    }
    public Long getId() {
        return this.entityId;
    }
    public FineractEntityType getType() {
        return this.type;
    }
}
