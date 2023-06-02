
package org.apache.fineract.portfolio.loanaccount.handler;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.DataIntegrityErrorHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.loanaccount.domain.LoanTransactionType;
import org.apache.fineract.portfolio.loanaccount.service.LoanWritePlatformService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
@CommandType(entity = "LOAN", action = "MERCHANTISSUEDREFUND")
public class LoanMerchantIssuedRefundCommandHandler implements NewCommandSourceHandler {
    private final LoanWritePlatformService writePlatformService;
    private final DataIntegrityErrorHandler dataIntegrityErrorHandler;
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        try {
            boolean isRecoveryRepayment = false;
            return this.writePlatformService.makeLoanRepayment(LoanTransactionType.MERCHANT_ISSUED_REFUND, command.getLoanId(), command,
                    isRecoveryRepayment);
        } catch (final JpaSystemException | DataIntegrityViolationException dve) {
            dataIntegrityErrorHandler.handleDataIntegrityIssues(command, dve.getMostSpecificCause(), dve, "loan.merchantIssuedRefund",
                    "Merchant Issued Refund");
            return CommandProcessingResult.empty();
        }
    }
}
