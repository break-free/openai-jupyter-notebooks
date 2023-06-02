
package org.apache.fineract.portfolio.collateral.command;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.apache.fineract.portfolio.collateral.api.CollateralApiConstants.CollateralJSONinputParams;
public class CollateralCommand {
    private final Long collateralTypeId;
    private final BigDecimal value;
    private final String description;
    public CollateralCommand(final Long collateralTypeId, final BigDecimal value, final String description) {
        this.collateralTypeId = collateralTypeId;
        this.value = value;
        this.description = description;
    }
    public Long getCollateralTypeId() {
        return this.collateralTypeId;
    }
    public BigDecimal getValue() {
        return this.value;
    }
    public String getDescription() {
        return this.description;
    }
    public void validateForCreate() {
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("collateral");
        baseDataValidator.reset().parameter(CollateralJSONinputParams.COLLATERAL_TYPE_ID.getValue()).value(this.collateralTypeId).notNull()
                .integerGreaterThanZero();
        baseDataValidator.reset().parameter(CollateralJSONinputParams.VALUE.getValue()).value(this.value).ignoreIfNull().positiveAmount();
        baseDataValidator.reset().parameter(CollateralJSONinputParams.DESCRIPTION.getValue()).value(this.description).ignoreIfNull()
                .notExceedingLengthOf(500);
        if (!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException("validation.msg.validation.errors.exist", "Validation errors exist.",
                    dataValidationErrors);
        }
    }
    public void validateForUpdate() {
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("collateral");
        baseDataValidator.reset().parameter(CollateralJSONinputParams.COLLATERAL_TYPE_ID.getValue()).value(this.collateralTypeId)
                .ignoreIfNull().integerGreaterThanZero();
        baseDataValidator.reset().parameter(CollateralJSONinputParams.VALUE.getValue()).value(this.value).ignoreIfNull().positiveAmount();
        baseDataValidator.reset().parameter(CollateralJSONinputParams.DESCRIPTION.getValue()).value(this.description).ignoreIfNull()
                .notExceedingLengthOf(500);
        if (!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException("validation.msg.validation.errors.exist", "Validation errors exist.",
                    dataValidationErrors);
        }
    }
}
