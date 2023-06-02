
package org.apache.fineract.portfolio.group.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.collectionsheet.service.CollectionSheetWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "GROUP", action = "SAVECOLLECTIONSHEET")
public class SaveGroupCollectionSheetCommandHandler implements NewCommandSourceHandler {
    private final CollectionSheetWritePlatformService writePlatformService;
    @Autowired
    public SaveGroupCollectionSheetCommandHandler(final CollectionSheetWritePlatformService collectionSheetWritePlatformService) {
        this.writePlatformService = collectionSheetWritePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.updateCollectionSheet(command);
    }
}
