
package org.apache.fineract.mix.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.mix.domain.MixTaxonomyMapping;
import org.apache.fineract.mix.domain.MixTaxonomyMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class MixTaxonomyMappingWritePlatformServiceImpl implements MixTaxonomyMappingWritePlatformService {
    private final MixTaxonomyMappingRepository mappingRepository;
    @Autowired
    public MixTaxonomyMappingWritePlatformServiceImpl(final MixTaxonomyMappingRepository mappingRepository) {
        this.mappingRepository = mappingRepository;
    }
    @Transactional
    @Override
    public CommandProcessingResult updateMapping(final Long mappingId, final JsonCommand command) {
        try {
            MixTaxonomyMapping mapping = this.mappingRepository.findById(mappingId).orElse(null);
            if (mapping == null) {
                mapping = MixTaxonomyMapping.fromJson(command);
            } else {
                mapping.update(command);
            }
            this.mappingRepository.saveAndFlush(mapping);
            return new CommandProcessingResultBuilder().withCommandId(command.commandId()).withEntityId(mapping.getId()).build();
        } catch (final JpaSystemException | DataIntegrityViolationException dve) {
            return CommandProcessingResult.empty();
        }
    }
}
