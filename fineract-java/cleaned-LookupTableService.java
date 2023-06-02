
package org.apache.fineract.spm.service;
import java.util.List;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.spm.domain.LookupTable;
import org.apache.fineract.spm.domain.LookupTableRepository;
import org.apache.fineract.spm.domain.Survey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class LookupTableService {
    private final PlatformSecurityContext securityContext;
    private final LookupTableRepository lookupTableRepository;
    @Autowired
    public LookupTableService(final PlatformSecurityContext securityContext, final LookupTableRepository lookupTableRepository) {
        this.securityContext = securityContext;
        this.lookupTableRepository = lookupTableRepository;
    }
    public List<LookupTable> findByKey(final String key) {
        this.securityContext.authenticatedUser();
        return this.lookupTableRepository.findByKey(key);
    }
    public List<LookupTable> findBySurvey(final Survey survey) {
        this.securityContext.authenticatedUser();
        return this.lookupTableRepository.findBySurvey(survey);
    }
    public List<LookupTable> createLookupTable(final List<LookupTable> lookupTable) {
        this.securityContext.authenticatedUser();
        return this.lookupTableRepository.saveAll(lookupTable);
    }
}
