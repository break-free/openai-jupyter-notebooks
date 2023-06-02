
package org.apache.fineract.accounting.closure.serialization;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.accounting.closure.api.GLClosureJsonInputParams;
import org.apache.fineract.accounting.closure.command.GLClosureCommand;
import org.apache.fineract.infrastructure.core.exception.InvalidJsonException;
import org.apache.fineract.infrastructure.core.serialization.AbstractFromApiJsonDeserializer;
import org.apache.fineract.infrastructure.core.serialization.FromApiJsonDeserializer;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.apache.fineract.portfolio.loanaccount.guarantor.command.GuarantorCommand;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public final class GLClosureCommandFromApiJsonDeserializer extends AbstractFromApiJsonDeserializer<GLClosureCommand> {
    private final FromJsonHelper fromApiJsonHelper;
    @Override
    public GLClosureCommand commandFromApiJson(final String json) {
        if (StringUtils.isBlank(json)) {
            throw new InvalidJsonException();
        }
        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        final Set<String> supportedParameters = GLClosureJsonInputParams.getAllValues();
        this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, supportedParameters);
        final JsonElement element = this.fromApiJsonHelper.parse(json);
        final Long id = this.fromApiJsonHelper.extractLongNamed(GLClosureJsonInputParams.ID.getValue(), element);
        final Long officeId = this.fromApiJsonHelper.extractLongNamed(GLClosureJsonInputParams.OFFICE_ID.getValue(), element);
        final String comments = this.fromApiJsonHelper.extractStringNamed(GLClosureJsonInputParams.COMMENTS.getValue(), element);
        final LocalDate closingDate = this.fromApiJsonHelper.extractLocalDateNamed(GLClosureJsonInputParams.CLOSING_DATE.getValue(),
                element);
        return new GLClosureCommand(id, officeId, closingDate, comments);
    }
}
