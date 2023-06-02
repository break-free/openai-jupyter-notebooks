
package org.apache.fineract.portfolio.repaymentwithpostdatedchecks.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.repaymentwithpostdatedchecks.service.RepaymentWithPostDatedChecksWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "REPAYMENT_WITH_POSTDATEDCHECKS", action = "DELETE")
public class DeletePostDatedChecksHandler implements NewCommandSourceHandler {
    private final RepaymentWithPostDatedChecksWritePlatformService repaymentWithPostDatedChecksWritePlatformService;
    @Autowired
    public DeletePostDatedChecksHandler(
            final RepaymentWithPostDatedChecksWritePlatformService repaymentWithPostDatedChecksWritePlatformService) {
        this.repaymentWithPostDatedChecksWritePlatformService = repaymentWithPostDatedChecksWritePlatformService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.repaymentWithPostDatedChecksWritePlatformService.deletePostDatedChecks(command);
    }
}
