
package org.apache.fineract.infrastructure.entityaccess.domain;
import org.apache.fineract.infrastructure.entityaccess.exception.FineractEntityAccessNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public final class FineractEntityRelationRepositoryWrapper {
    private final FineractEntityRelationRepository fineractEntityRelationRepository;
    @Autowired
    public FineractEntityRelationRepositoryWrapper(final FineractEntityRelationRepository fineractEntityRelationRepository) {
        this.fineractEntityRelationRepository = fineractEntityRelationRepository;
    }
    public FineractEntityRelation findOneWithNotFoundDetection(final Long id) {
        return this.fineractEntityRelationRepository.findById(id).orElseThrow(() -> new FineractEntityAccessNotFoundException(id));
    }
    public FineractEntityRelation findOneByCodeName(final String codeName) {
        final FineractEntityRelation fineractEntityRelation = this.fineractEntityRelationRepository.findOneByCodeName(codeName);
        if (fineractEntityRelation == null) {
            throw new FineractEntityAccessNotFoundException(codeName);
        }
        return fineractEntityRelation;
    }
}
