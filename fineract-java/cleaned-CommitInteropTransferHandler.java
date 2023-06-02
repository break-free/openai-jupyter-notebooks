
package org.apache.fineract.interoperation.handler;
import static org.apache.fineract.interoperation.util.InteropUtil.ACTION_TRANSFER_COMMIT;
import static org.apache.fineract.interoperation.util.InteropUtil.ENTITY_NAME_TRANSFER;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.interoperation.service.InteropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = ENTITY_NAME_TRANSFER, action = ACTION_TRANSFER_COMMIT)
public class CommitInteropTransferHandler implements NewCommandSourceHandler {
    private final InteropService interopService;
    @Autowired
    public CommitInteropTransferHandler(InteropService interopService) {
        this.interopService = interopService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.interopService.commitTransfer(command);
    }
}
