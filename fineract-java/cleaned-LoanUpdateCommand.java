
package org.apache.fineract.portfolio.loanaccount.command;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
public class LoanUpdateCommand {
    private final LocalDate unassignedDate;
    public LoanUpdateCommand(final LocalDate unassignDate) {
        this.unassignedDate = unassignDate;
    }
    public void validate() {
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("loan.transaction");
        baseDataValidator.reset().parameter("unassignedDate").value(this.unassignedDate).notNull();
        if (!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException("validation.msg.validation.errors.exist", "Validation errors exist.",
                    dataValidationErrors);
        }
    }
}
