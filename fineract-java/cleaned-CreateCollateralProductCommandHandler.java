
package org.apache.fineract.portfolio.collateralmanagement.handler;
import javax.transaction.Transactional;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.collateralmanagement.service.CollateralManagementWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@CommandType(entity = "COLLATERAL_PRODUCT", action = "CREATE")
public class CreateCollateralProductCommandHandler implements NewCommandSourceHandler {
    private final CollateralManagementWritePlatformService collateralManagementWritePlatformService;
    @Autowired
    public CreateCollateralProductCommandHandler(final CollateralManagementWritePlatformService collateralManagementWritePlatformService) {
        this.collateralManagementWritePlatformService = collateralManagementWritePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand jsonCommand) {
        return this.collateralManagementWritePlatformService.createCollateral(jsonCommand);
    }
}
