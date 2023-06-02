
package org.apache.fineract.portfolio.shareproducts.service;
import com.google.gson.JsonElement;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.apache.fineract.portfolio.products.service.ProductCommandsService;
import org.apache.fineract.portfolio.shareproducts.constants.ShareProductApiConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service(value = "SHAREPRODUCT_COMMANDSERVICE")
public class ShareProductCommandsServiceImpl implements ProductCommandsService {
    private final FromJsonHelper fromApiJsonHelper;
    @Autowired
    public ShareProductCommandsServiceImpl(final FromJsonHelper fromApiJsonHelper) {
        this.fromApiJsonHelper = fromApiJsonHelper;
    }
    public CommandProcessingResult postDividends(Long productId, JsonCommand jsonCommand) {
        return null;
    }
    @Override
    public Object handleCommand(Long productId, String command, String jsonBody) {
        final JsonElement parsedCommand = this.fromApiJsonHelper.parse(jsonBody);
        final JsonCommand jsonCommand = JsonCommand.from(jsonBody, parsedCommand, this.fromApiJsonHelper, null, null, null, null, null,
                null, null, null, null, null, null, null);
        if (ShareProductApiConstants.PREIEW_DIVIDENDS_COMMAND_STRING.equals(command)) {
            return null;
        } else if (ShareProductApiConstants.POST_DIVIDENdS_COMMAND_STRING.equals(command)) {
            return postDividends(productId, jsonCommand);
        }
        return CommandProcessingResult.empty();
    }
}
