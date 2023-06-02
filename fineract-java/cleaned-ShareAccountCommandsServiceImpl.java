
package org.apache.fineract.portfolio.shareaccounts.service;
import com.google.gson.JsonElement;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.apache.fineract.portfolio.accounts.constants.ShareAccountApiConstants;
import org.apache.fineract.portfolio.accounts.service.AccountsCommandsService;
import org.apache.fineract.portfolio.shareaccounts.serialization.ShareAccountDataSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service(value = "SHAREACCOUNT_COMMANDSERVICE")
public class ShareAccountCommandsServiceImpl implements AccountsCommandsService {
    private final FromJsonHelper fromApiJsonHelper;
    private final ShareAccountDataSerializer shareAccountDataSerializer;
    @Autowired
    public ShareAccountCommandsServiceImpl(final FromJsonHelper fromApiJsonHelper,
            final ShareAccountDataSerializer shareAccountDataSerializer) {
        this.fromApiJsonHelper = fromApiJsonHelper;
        this.shareAccountDataSerializer = shareAccountDataSerializer;
    }
    @Override
    public Object handleCommand(Long accountId, String command, String jsonBody) {
        final JsonElement parsedCommand = this.fromApiJsonHelper.parse(jsonBody);
        final JsonCommand jsonCommand = JsonCommand.from(jsonBody, parsedCommand, this.fromApiJsonHelper, null, null, null, null, null,
                null, null, null, null, null, null, null);
        if (ShareAccountApiConstants.APPROVE_COMMAND.equals(command)) {
            return approveShareAccount(accountId, jsonCommand);
        }
        if (ShareAccountApiConstants.REJECT_COMMAND.equals(command)) {
            return rejectShareAccount(accountId, jsonCommand);
        } else if (ShareAccountApiConstants.APPLY_ADDITIONALSHARES_COMMAND.equals(command)) {
            return applyAdditionalShares(accountId, jsonCommand);
        } else if (ShareAccountApiConstants.APPROVE_ADDITIONSHARES_COMMAND.equals(command)) {
            return approveAdditionalShares(accountId, jsonCommand);
        } else if (ShareAccountApiConstants.REJECT_ADDITIONSHARES_COMMAND.equals(command)) {
            return rejectAdditionalShares(accountId, jsonCommand);
        }
        return CommandProcessingResult.empty();
    }
    public Object approveShareAccount(Long accountId, JsonCommand jsonCommand) {
        return null;
    }
    public Object rejectShareAccount(Long accountId, JsonCommand jsonCommand) {
        return null;
    }
    public Object applyAdditionalShares(Long accountId, JsonCommand jsonCommand) {
        return null;
    }
    public Object approveAdditionalShares(Long accountId, JsonCommand jsonCommand) {
        return null;
    }
    public Object rejectAdditionalShares(Long accountId, JsonCommand jsonCommand) {
        return null;
    }
}
