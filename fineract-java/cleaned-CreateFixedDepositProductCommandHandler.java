
package org.apache.fineract.portfolio.savings.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.savings.service.FixedDepositProductWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@CommandType(entity = "FIXEDDEPOSITPRODUCT", action = "CREATE")
public class CreateFixedDepositProductCommandHandler implements NewCommandSourceHandler {
    private final FixedDepositProductWritePlatformService writePlatformService;
    @Autowired
    public CreateFixedDepositProductCommandHandler(final FixedDepositProductWritePlatformService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.create(command);
    }
}
