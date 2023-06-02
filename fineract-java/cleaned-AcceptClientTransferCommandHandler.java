
package org.apache.fineract.portfolio.transfer.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.transfer.service.TransferWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "CLIENT", action = "ACCEPTTRANSFER")
public class AcceptClientTransferCommandHandler implements NewCommandSourceHandler {
    private final TransferWritePlatformService writePlatformService;
    @Autowired
    public AcceptClientTransferCommandHandler(final TransferWritePlatformService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.writePlatformService.acceptClientTransfer(command.entityId(), command);
    }
}
