
package org.apache.fineract.infrastructure.creditbureau.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.creditbureau.service.CreditBureauLoanProductMappingWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@CommandType(entity = "CREDITBUREAU_LOANPRODUCT_MAPPING", action = "CREATE")
public class CreateCreditBureauLoanProductMappingCommandHandler implements NewCommandSourceHandler {
    private final CreditBureauLoanProductMappingWritePlatformService writePlatformService;
    @Autowired
    public CreateCreditBureauLoanProductMappingCommandHandler(
            final CreditBureauLoanProductMappingWritePlatformService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Override
    public CommandProcessingResult processCommand(JsonCommand command) {
        return this.writePlatformService.addCreditBureauLoanProductMapping(command.getOrganisationCreditBureauId(), command);
    }
}
