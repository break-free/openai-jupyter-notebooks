
package org.apache.fineract.portfolio.collateral.serialization;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.infrastructure.core.exception.InvalidJsonException;
import org.apache.fineract.infrastructure.core.serialization.AbstractFromApiJsonDeserializer;
import org.apache.fineract.infrastructure.core.serialization.FromApiJsonDeserializer;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.apache.fineract.portfolio.collateral.api.CollateralApiConstants.CollateralJSONinputParams;
import org.apache.fineract.portfolio.collateral.command.CollateralCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public final class CollateralCommandFromApiJsonDeserializer extends AbstractFromApiJsonDeserializer<CollateralCommand> {
    private final FromJsonHelper fromApiJsonHelper;
    @Autowired
    public CollateralCommandFromApiJsonDeserializer(final FromJsonHelper fromApiJsonHelper) {
        this.fromApiJsonHelper = fromApiJsonHelper;
    }
    @Override
    public CollateralCommand commandFromApiJson(final String json) {
        if (StringUtils.isBlank(json)) {
            throw new InvalidJsonException();
        }
        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        final Set<String> supportedParameters = CollateralJSONinputParams.getAllValues();
        supportedParameters.add("locale");
        supportedParameters.add("dateFormat");
        this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, supportedParameters);
        final JsonElement element = this.fromApiJsonHelper.parse(json);
        final JsonObject topLevelJsonElement = element.getAsJsonObject();
        final Locale locale = this.fromApiJsonHelper.extractLocaleParameter(topLevelJsonElement);
        final Long collateralTypeId = this.fromApiJsonHelper.extractLongNamed(CollateralJSONinputParams.COLLATERAL_TYPE_ID.getValue(),
                element);
        final String description = this.fromApiJsonHelper.extractStringNamed(CollateralJSONinputParams.DESCRIPTION.getValue(), element);
        final BigDecimal value = this.fromApiJsonHelper.extractBigDecimalNamed(CollateralJSONinputParams.VALUE.getValue(), element, locale);
        return new CollateralCommand(collateralTypeId, value, description);
    }
}
