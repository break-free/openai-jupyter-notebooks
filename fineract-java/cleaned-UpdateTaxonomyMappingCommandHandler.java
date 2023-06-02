
package org.apache.fineract.mix.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.mix.service.MixTaxonomyMappingWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "XBRLMAPPING", action = "UPDATE")
public class UpdateTaxonomyMappingCommandHandler implements NewCommandSourceHandler {
    private final MixTaxonomyMappingWritePlatformService writeTaxonomyService;
    @Autowired
    public UpdateTaxonomyMappingCommandHandler(final MixTaxonomyMappingWritePlatformService writeTaxonomyService) {
        this.writeTaxonomyService = writeTaxonomyService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writeTaxonomyService.updateMapping(command.entityId(), command);
    }
}
