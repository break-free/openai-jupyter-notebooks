
package org.apache.fineract.infrastructure.cache.command;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.cache.CacheApiConstants;
import org.apache.fineract.infrastructure.cache.domain.CacheType;
import org.apache.fineract.infrastructure.cache.service.CacheWritePlatformService;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.exception.InvalidJsonException;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "CACHE", action = "UPDATE")
public class UpdateCacheCommandHandler implements NewCommandSourceHandler {
    private final CacheWritePlatformService cacheService;
    private static final Set<String> REQUEST_DATA_PARAMETERS = new HashSet<>(Arrays.asList(CacheApiConstants.cacheTypeParameter));
    @Autowired
    public UpdateCacheCommandHandler(final CacheWritePlatformService cacheService) {
        this.cacheService = cacheService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        final String json = command.json();
        if (StringUtils.isBlank(json)) {
            throw new InvalidJsonException();
        }
        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        command.checkForUnsupportedParameters(typeOfMap, json, REQUEST_DATA_PARAMETERS);
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors)
                .resource(CacheApiConstants.RESOURCE_NAME.toLowerCase());
        final int cacheTypeEnum = command.integerValueSansLocaleOfParameterNamed(CacheApiConstants.cacheTypeParameter);
        baseDataValidator.reset().parameter(CacheApiConstants.cacheTypeParameter).value(Integer.valueOf(cacheTypeEnum)).notNull()
                .isOneOfTheseValues(Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3));
        if (!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException(dataValidationErrors);
        }
        final CacheType cacheType = CacheType.fromInt(cacheTypeEnum);
        final Map<String, Object> changes = this.cacheService.switchToCache(cacheType);
        return new CommandProcessingResultBuilder().withCommandId(command.commandId()).with(changes).build();
    }
}
