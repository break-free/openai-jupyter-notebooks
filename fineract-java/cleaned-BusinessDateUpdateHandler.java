
package org.apache.fineract.infrastructure.businessdate.handler;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.businessdate.service.BusinessDateWritePlatformService;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@RequiredArgsConstructor
@Service
@CommandType(entity = "BUSINESS_DATE", action = "UPDATE")
public class BusinessDateUpdateHandler implements NewCommandSourceHandler {
    private final BusinessDateWritePlatformService businessDateWritePlatformService;
    @Transactional
    @Override
    public CommandProcessingResult processCommand(@NotNull final JsonCommand command) {
        return businessDateWritePlatformService.updateBusinessDate(command);
    }
}
