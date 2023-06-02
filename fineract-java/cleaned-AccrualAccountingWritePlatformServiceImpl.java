
package org.apache.fineract.accounting.accrual.service;
import static org.apache.fineract.accounting.accrual.api.AccrualAccountingConstants.PERIODIC_ACCRUAL_ACCOUNTING_EXECUTION_ERROR_CODE;
import static org.apache.fineract.accounting.accrual.api.AccrualAccountingConstants.PERIODIC_ACCRUAL_ACCOUNTING_RESOURCE_NAME;
import static org.apache.fineract.accounting.accrual.api.AccrualAccountingConstants.accrueTillParamName;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.accounting.accrual.serialization.AccrualAccountingDataValidator;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.exception.MultiException;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.apache.fineract.portfolio.loanaccount.service.LoanAccrualPlatformService;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class AccrualAccountingWritePlatformServiceImpl implements AccrualAccountingWritePlatformService {
    private final LoanAccrualPlatformService loanAccrualPlatformService;
    private final AccrualAccountingDataValidator accountingDataValidator;
    @Override
    public CommandProcessingResult executeLoansPeriodicAccrual(JsonCommand command) {
        this.accountingDataValidator.validateLoanPeriodicAccrualData(command.json());
        LocalDate tilldate = command.localDateValueOfParameterNamed(accrueTillParamName);
        try {
            this.loanAccrualPlatformService.addPeriodicAccruals(tilldate);
        } catch (MultiException e) {
            final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
            final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors)
                    .resource(PERIODIC_ACCRUAL_ACCOUNTING_RESOURCE_NAME);
            baseDataValidator.reset().failWithCodeNoParameterAddedToErrorCode(PERIODIC_ACCRUAL_ACCOUNTING_EXECUTION_ERROR_CODE,
                    e.getMessage());
            throw new PlatformApiDataValidationException(dataValidationErrors, e);
        }
        return CommandProcessingResult.empty();
    }
}
