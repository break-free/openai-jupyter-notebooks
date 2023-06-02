
package org.apache.fineract.infrastructure.entityaccess.data;
import java.io.Serializable;
import java.time.LocalDate;
public final class FineractEntityToEntityMappingData implements Serializable {
    @SuppressWarnings("unused")
    private Long mapId;
    @SuppressWarnings("unused")
    private Long relationId;
    @SuppressWarnings("unused")
    private Long fromId;
    @SuppressWarnings("unused")
    private Long toId;
    @SuppressWarnings("unused")
    private LocalDate startDate;
    @SuppressWarnings("unused")
    private LocalDate endDate;
    @SuppressWarnings("unused")
    private final String fromEntity;
    @SuppressWarnings("unused")
    private final String toEntity;
    private FineractEntityToEntityMappingData(final Long mapId, final Long relationId, final Long fromId, final Long toId,
            final LocalDate startDate, final LocalDate endDate, final String fromEntity, final String toEntity) {
        this.mapId = mapId;
        this.relationId = relationId;
        this.fromId = fromId;
        this.toId = toId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fromEntity = fromEntity;
        this.toEntity = toEntity;
    }
    public static FineractEntityToEntityMappingData getRelatedEntities(final Long mapId, final Long relationId, final Long fromId,
            final Long toId, final LocalDate startDate, final LocalDate endDate, final String fromEntity, final String toEntity) {
        return new FineractEntityToEntityMappingData(mapId, relationId, fromId, toId, startDate, endDate, fromEntity, toEntity);
    }
    public static FineractEntityToEntityMappingData getRelatedEntities(final Long relationId, final Long fromId, final Long toId,
            final LocalDate startDate, final LocalDate endDate) {
        final Long mapId = null;
        final String fromEntity = null;
        final String toEntity = null;
        return new FineractEntityToEntityMappingData(mapId, relationId, fromId, toId, startDate, endDate, fromEntity, toEntity);
    }
    public Long getRelationId() {
        return relationId;
    }
    public Long getToId() {
        return toId;
    }
    public String getFromEntity() {
        return fromEntity;
    }
}
