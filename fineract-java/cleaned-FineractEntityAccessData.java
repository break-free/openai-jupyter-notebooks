
package org.apache.fineract.infrastructure.entityaccess.data;
import org.apache.fineract.infrastructure.entityaccess.domain.FineractEntity;
import org.apache.fineract.infrastructure.entityaccess.domain.FineractEntityAccessType;
public class FineractEntityAccessData {
    private FineractEntity firstEntity;
    private FineractEntityAccessType accessType;
    private FineractEntity secondEntity;
    public FineractEntityAccessData(FineractEntity firstEntity, FineractEntityAccessType accessType, FineractEntity secondEntity) {
        this.firstEntity = firstEntity;
        this.accessType = accessType;
        this.secondEntity = secondEntity;
    }
    public FineractEntity getFirstEntity() {
        return this.firstEntity;
    }
    public FineractEntityAccessType getAccessType() {
        return this.accessType;
    }
    public FineractEntity getSecondEntity() {
        return this.secondEntity;
    }
}
