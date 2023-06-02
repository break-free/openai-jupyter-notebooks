
package org.apache.fineract.portfolio.self.pockets.domain;
import java.util.Collection;
import java.util.List;
import org.apache.fineract.portfolio.self.pockets.exception.MappingIdNotLinkedToPocketException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class PocketAccountMappingRepositoryWrapper {
    private final PocketAccountMappingRepository pocketAccountMappingRepository;
    @Autowired
    public PocketAccountMappingRepositoryWrapper(final PocketAccountMappingRepository pocketAccountMappingRepository) {
        this.pocketAccountMappingRepository = pocketAccountMappingRepository;
    }
    public void save(final PocketAccountMapping pocketAccountMapping) {
        this.pocketAccountMappingRepository.save(pocketAccountMapping);
    }
    public List<PocketAccountMapping> save(final List<PocketAccountMapping> pocketAccountsList) {
        return this.pocketAccountMappingRepository.saveAll(pocketAccountsList);
    }
    public void delete(final List<PocketAccountMapping> pocketAccountsList) {
        this.pocketAccountMappingRepository.deleteAll(pocketAccountsList);
    }
    public PocketAccountMapping findByIdAndPocketIdWithNotFoundException(final Long id, final Long pocketId) {
        PocketAccountMapping pocketAccountMapping = this.pocketAccountMappingRepository.findByIdAndPocketId(id, pocketId);
        if (pocketAccountMapping == null) {
            throw new MappingIdNotLinkedToPocketException(id);
        }
        return pocketAccountMapping;
    }
    public Collection<PocketAccountMapping> findByPocketId(final Long pocketId) {
        return this.pocketAccountMappingRepository.findByPocketId(pocketId);
    }
}
