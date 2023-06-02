
package org.apache.fineract.infrastructure.entityaccess.domain;
import org.apache.fineract.infrastructure.entityaccess.exception.FineractEntityAccessNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class FineractEntityToEntityMappingRepositoryWrapper {
    private final FineractEntityToEntityMappingRepository fineractEntityToEntityMappingRepository;
    @Autowired
    public FineractEntityToEntityMappingRepositoryWrapper(
            final FineractEntityToEntityMappingRepository fineractEntityToEntityMappingRepository) {
        this.fineractEntityToEntityMappingRepository = fineractEntityToEntityMappingRepository;
    }
    public FineractEntityToEntityMapping findOneWithNotFoundDetection(final Long id) {
        return this.fineractEntityToEntityMappingRepository.findById(id).orElseThrow(() -> new FineractEntityAccessNotFoundException(id));
    }
    public void delete(final FineractEntityToEntityMapping mapId) {
        this.fineractEntityToEntityMappingRepository.delete(mapId);
    }
}
