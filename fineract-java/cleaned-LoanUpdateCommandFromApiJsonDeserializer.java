
package org.apache.fineract.portfolio.loanaccount.serialization;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.infrastructure.core.exception.InvalidJsonException;
import org.apache.fineract.infrastructure.core.serialization.AbstractFromApiJsonDeserializer;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.apache.fineract.portfolio.loanaccount.command.LoanUpdateCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public final class LoanUpdateCommandFromApiJsonDeserializer extends AbstractFromApiJsonDeserializer<LoanUpdateCommand> {
    final Set<String> supportedParameters = new HashSet<>(Arrays.asList("unassignedDate", "locale", "dateFormat"));
    private final FromJsonHelper fromApiJsonHelper;
    @Autowired
    public LoanUpdateCommandFromApiJsonDeserializer(final FromJsonHelper fromApiJsonHelper) {
        this.fromApiJsonHelper = fromApiJsonHelper;
    }
    @Override
    public LoanUpdateCommand commandFromApiJson(final String json) {
        if (StringUtils.isBlank(json)) {
            throw new InvalidJsonException();
        }
        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, this.supportedParameters);
        final JsonElement element = this.fromApiJsonHelper.parse(json);
        final LocalDate unassignedDate = this.fromApiJsonHelper.extractLocalDateNamed("unassignedDate", element);
        return new LoanUpdateCommand(unassignedDate);
    }
}
