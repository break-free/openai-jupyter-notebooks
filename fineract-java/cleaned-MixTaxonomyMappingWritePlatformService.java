
package org.apache.fineract.mix.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface MixTaxonomyMappingWritePlatformService {
    CommandProcessingResult updateMapping(Long mappingId, JsonCommand command);
}
