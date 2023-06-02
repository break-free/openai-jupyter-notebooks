
package org.apache.fineract.portfolio.collateralmanagement.handler;
import javax.transaction.Transactional;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.collateralmanagement.service.ClientCollateralManagementWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@CommandType(entity = "CLIENT_COLLATERAL_PRODUCT", action = "DELETE")
public class DeleteClientCollateralProductCommandHandler implements NewCommandSourceHandler {
    private final ClientCollateralManagementWritePlatformService clientCollateralManagementWritePlatformService;
    @Autowired
    public DeleteClientCollateralProductCommandHandler(
            final ClientCollateralManagementWritePlatformService clientCollateralManagementWritePlatformService) {
        this.clientCollateralManagementWritePlatformService = clientCollateralManagementWritePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(JsonCommand command) {
        return this.clientCollateralManagementWritePlatformService.deleteClientCollateralProduct(command.entityId());
    }
}
