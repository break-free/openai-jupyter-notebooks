
package org.apache.fineract.portfolio.tax.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.tax.service.TaxWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@CommandType(entity = "TAXCOMPONENT", action = "CREATE")
public class CreateTaxComponentCommandHandler implements NewCommandSourceHandler {
    private final TaxWritePlatformService taxWritePlatformService;
    @Autowired
    public CreateTaxComponentCommandHandler(final TaxWritePlatformService taxWritePlatformService) {
        this.taxWritePlatformService = taxWritePlatformService;
    }
    @Override
    public CommandProcessingResult processCommand(JsonCommand jsonCommand) {
        return this.taxWritePlatformService.createTaxComponent(jsonCommand);
    }
}
