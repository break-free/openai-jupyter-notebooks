
package org.apache.fineract.organisation.teller.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.organisation.teller.service.TellerWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@CommandType(entity = "TELLER", action = "SETTLECASHFROMCASHIER")
public class SettleCashFromCashierCommandHandler implements NewCommandSourceHandler {
    private final TellerWritePlatformService writePlatformService;
    @Autowired
    public SettleCashFromCashierCommandHandler(final TellerWritePlatformService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.settleCashFromCashier(command.subentityId(), command);
    }
}
