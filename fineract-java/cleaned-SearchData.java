
package org.apache.fineract.portfolio.search.data;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
public class SearchData {
    private final Long entityId;
    private final String entityAccountNo;
    private final String entityExternalId;
    private final String entityName;
    private final String entityType;
    private final Long parentId;
    private final String parentName;
    private final String entityMobileNo;
    private final EnumOptionData entityStatus;
    private final String parentType;
    private final String subEntityType;
    public SearchData(final Long entityId, final String entityAccountNo, final String entityExternalId, final String entityName,
            final String entityType, final Long parentId, final String parentName, final String parentType, final String entityMobileNo,
            final EnumOptionData entityStatus, final String subEntityType) {
        this.entityId = entityId;
        this.entityAccountNo = entityAccountNo;
        this.entityExternalId = entityExternalId;
        this.entityName = entityName;
        this.entityType = entityType;
        this.parentId = parentId;
        this.parentName = parentName;
        this.parentType = parentType;
        this.entityMobileNo = entityMobileNo;
        this.entityStatus = entityStatus;
        this.subEntityType = subEntityType;
    }
    public Long getEntityId() {
        return this.entityId;
    }
    public String getEntityAccountNo() {
        return this.entityAccountNo;
    }
    public String getEntityExternalId() {
        return this.entityExternalId;
    }
    public String getEntityName() {
        return this.entityName;
    }
    public String getEntityType() {
        return this.entityType;
    }
    public Long getParentId() {
        return this.parentId;
    }
    public String getParentName() {
        return this.parentName;
    }
    public String getParentType() {
        return this.parentType;
    }
    public String getEntityMobileNo() {
        return this.entityMobileNo;
    }
    public EnumOptionData getEntityStatus() {
        return this.entityStatus;
    }
    public String getSubEntityType() {
        return this.subEntityType;
    }
}
