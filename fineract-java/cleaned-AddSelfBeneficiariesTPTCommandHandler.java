
package org.apache.fineract.portfolio.self.account.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.self.account.service.SelfBeneficiariesTPTWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@CommandType(entity = "SSBENEFICIARYTPT", action = "CREATE")
public class AddSelfBeneficiariesTPTCommandHandler implements NewCommandSourceHandler {
    private final SelfBeneficiariesTPTWritePlatformService writePlatformService;
    @Autowired
    public AddSelfBeneficiariesTPTCommandHandler(final SelfBeneficiariesTPTWritePlatformService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.add(command);
    }
}
