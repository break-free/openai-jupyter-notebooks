
package org.apache.fineract.infrastructure.dataqueries.service;
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
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.exception.InvalidJsonException;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.apache.fineract.infrastructure.dataqueries.data.EntityTables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public final class EntityDatatableChecksDataValidator {
    private final Set<String> supportedParameters = new HashSet<>(
            Arrays.asList("entity", "datatableName", "status", "systemDefined", "productId"));
    private final FromJsonHelper fromApiJsonHelper;
    @Autowired
    public EntityDatatableChecksDataValidator(final FromJsonHelper fromApiJsonHelper) {
        this.fromApiJsonHelper = fromApiJsonHelper;
    }
    public void validateForCreate(final String json) {
        if (StringUtils.isBlank(json)) {
            throw new InvalidJsonException();
        }
        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, this.supportedParameters);
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("entityDatatableChecks");
        final JsonElement element = this.fromApiJsonHelper.parse(json);
        final String entity = this.fromApiJsonHelper.extractStringNamed("entity", element);
        baseDataValidator.reset().parameter("entity").value(entity).notBlank().isOneOfTheseStringValues(EntityTables.getEntitiesList());
        final Integer status = this.fromApiJsonHelper.extractIntegerSansLocaleNamed("status", element);
        final Object[] entityTablesStatuses = EntityTables.getStatus(entity).toArray();
        baseDataValidator.reset().parameter("status").value(status).isOneOfTheseValues(entityTablesStatuses);
        final String datatableName = this.fromApiJsonHelper.extractStringNamed("datatableName", element);
        baseDataValidator.reset().parameter("datatableName").value(datatableName).notBlank();
        if (this.fromApiJsonHelper.parameterExists("systemDefined", element)) {
            final String systemDefined = this.fromApiJsonHelper.extractStringNamed("systemDefined", element);
            baseDataValidator.reset().parameter("systemDefined").value(systemDefined).validateForBooleanValue();
        }
        if (this.fromApiJsonHelper.parameterExists("productId", element)) {
            final long productId = this.fromApiJsonHelper.extractLongNamed("productId", element);
            baseDataValidator.reset().parameter("productId").value(productId).integerZeroOrGreater();
        }
        throwExceptionIfValidationWarningsExist(dataValidationErrors);
    }
    private void throwExceptionIfValidationWarningsExist(final List<ApiParameterError> dataValidationErrors) {
        if (!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException(dataValidationErrors);
        }
    }
}
