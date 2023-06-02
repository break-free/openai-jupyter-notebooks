
package org.apache.fineract.infrastructure.entityaccess.domain;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.infrastructure.entityaccess.api.FineractEntityApiResourceConstants;
import org.apache.fineract.infrastructure.entityaccess.exception.FineractEntityToEntityMappingDateException;
@Entity
@Table(name = "m_entity_to_entity_mapping", uniqueConstraints = { @UniqueConstraint(columnNames = { "rel_id", "from_id", "to_id" }) })
public class FineractEntityToEntityMapping extends AbstractPersistableCustom {
    @ManyToOne
    @JoinColumn(name = "rel_id")
    private FineractEntityRelation relationId;
    @Column(name = "from_id")
    private Long fromId;
    @Column(name = "to_id")
    private Long toId;
    @Column(name = "start_date", nullable = true)
    private LocalDate startDate;
    @Column(name = "end_date", nullable = true)
    private LocalDate endDate;
    private FineractEntityToEntityMapping(final FineractEntityRelation relationId, final Long fromId, final Long toId,
            final LocalDate startDate, final LocalDate endDate) {
        this.relationId = relationId;
        this.fromId = fromId;
        this.toId = toId;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public FineractEntityToEntityMapping() {
    }
    public static FineractEntityToEntityMapping newMap(FineractEntityRelation relationId, Long fromId, Long toId, LocalDate startDate,
            LocalDate endDate) {
        return new FineractEntityToEntityMapping(relationId, fromId, toId, startDate, endDate);
    }
    public Map<String, Object> updateMap(final JsonCommand command) {
        final Map<String, Object> actualChanges = new LinkedHashMap<>(9);
        if (command.isChangeInLongParameterNamed(FineractEntityApiResourceConstants.fromEnityType, this.fromId)) {
            final Long newValue = command.longValueOfParameterNamed(FineractEntityApiResourceConstants.fromEnityType);
            actualChanges.put(FineractEntityApiResourceConstants.fromEnityType, newValue);
            this.fromId = newValue;
        }
        if (command.isChangeInLongParameterNamed(FineractEntityApiResourceConstants.toEntityType, this.toId)) {
            final Long newValue = command.longValueOfParameterNamed(FineractEntityApiResourceConstants.toEntityType);
            actualChanges.put(FineractEntityApiResourceConstants.toEntityType, newValue);
            this.toId = newValue;
        }
        if (command.isChangeInDateParameterNamed(FineractEntityApiResourceConstants.startDate, this.startDate)) {
            final String valueAsInput = command.stringValueOfParameterNamed(FineractEntityApiResourceConstants.startDate);
            actualChanges.put(FineractEntityApiResourceConstants.startDate, valueAsInput);
            this.startDate = command.localDateValueOfParameterNamed(FineractEntityApiResourceConstants.startDate);
        }
        if (command.isChangeInDateParameterNamed(FineractEntityApiResourceConstants.endDate, this.endDate)) {
            final String valueAsInput = command.stringValueOfParameterNamed(FineractEntityApiResourceConstants.endDate);
            actualChanges.put(FineractEntityApiResourceConstants.endDate, valueAsInput);
            this.endDate = command.localDateValueOfParameterNamed(FineractEntityApiResourceConstants.endDate);
        }
        if (startDate != null && endDate != null) {
            if (endDate.isBefore(startDate)) {
                throw new FineractEntityToEntityMappingDateException(startDate.toString(), endDate.toString());
            }
        }
        return actualChanges;
    }
    public FineractEntityRelation getRelationId() {
        return this.relationId;
    }
    public void setRelationId(FineractEntityRelation relationId) {
        this.relationId = relationId;
    }
}
