
package org.apache.fineract.portfolio.collateral.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.collateral.service.CollateralWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "COLLATERAL", action = "DELETE")
public class DeleteCollateralCommandHandler implements NewCommandSourceHandler {
    private final CollateralWritePlatformService collateralWritePlatformService;
    @Autowired
    public DeleteCollateralCommandHandler(final CollateralWritePlatformService guarantorWritePlatformService) {
        this.collateralWritePlatformService = guarantorWritePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.collateralWritePlatformService.deleteCollateral(command.getLoanId(), command.entityId(), command.commandId());
    }
}
