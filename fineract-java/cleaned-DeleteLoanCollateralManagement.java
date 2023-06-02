
package org.apache.fineract.portfolio.collateralmanagement.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.collateralmanagement.service.LoanCollateralManagementWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "LOAN_COLLATERAL_PRODUCT", action = "DELETE")
public class DeleteLoanCollateralManagement implements NewCommandSourceHandler {
    private final LoanCollateralManagementWritePlatformService loanCollateralManagementWritePlatformService;
    @Autowired
    public DeleteLoanCollateralManagement(final LoanCollateralManagementWritePlatformService loanCollateralManagement) {
        this.loanCollateralManagementWritePlatformService = loanCollateralManagement;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand jsonCommand) {
        return this.loanCollateralManagementWritePlatformService.deleteLoanCollateral(jsonCommand);
    }
}
