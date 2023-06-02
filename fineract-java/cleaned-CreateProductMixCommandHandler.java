
package org.apache.fineract.portfolio.loanproduct.productmix.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.loanproduct.productmix.service.ProductMixWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "PRODUCTMIX", action = "CREATE")
public class CreateProductMixCommandHandler implements NewCommandSourceHandler {
    private final ProductMixWritePlatformService productMixWritePlatformService;
    @Autowired
    public CreateProductMixCommandHandler(final ProductMixWritePlatformService productMixWritePlatformService) {
        this.productMixWritePlatformService = productMixWritePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.productMixWritePlatformService.createProductMix(command.getProductId(), command);
    }
}
