
package org.apache.fineract.infrastructure.survey.data;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.exception.InvalidJsonException;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.apache.fineract.infrastructure.survey.api.LikelihoodApiConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class LikelihoodDataValidator {
    private final FromJsonHelper fromApiJsonHelper;
    private static final Set<String> UPDATE_LIKELIHOOD_DATA_PARAMETERS = new HashSet<>(Arrays.asList(LikelihoodApiConstants.ACTIVE));
    @Autowired
    public LikelihoodDataValidator(final FromJsonHelper fromApiJsonHelper) {
        this.fromApiJsonHelper = fromApiJsonHelper;
    }
    public void validateForUpdate(final JsonCommand command) {
        final String json = command.json();
        if (StringUtils.isBlank(json)) {
            throw new InvalidJsonException();
        }
        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, UPDATE_LIKELIHOOD_DATA_PARAMETERS);
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors)
                .resource(LikelihoodApiConstants.LIKELIHOOD_RESOURCE_NAME);
        final JsonElement element = this.fromApiJsonHelper.parse(json);
        if (this.fromApiJsonHelper.parameterExists(LikelihoodApiConstants.ACTIVE, element)) {
            final boolean enabledBool = this.fromApiJsonHelper.extractBooleanNamed(LikelihoodApiConstants.ACTIVE, element);
            baseDataValidator.reset().parameter(LikelihoodApiConstants.ACTIVE).value(enabledBool).validateForBooleanValue();
        }
        if (!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException(dataValidationErrors);
        }
    }
}
