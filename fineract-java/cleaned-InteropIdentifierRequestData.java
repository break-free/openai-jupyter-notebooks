
package org.apache.fineract.interoperation.data;
import static org.apache.fineract.interoperation.util.InteropUtil.PARAM_ACCOUNT_ID;
import com.google.gson.JsonObject;
import java.util.Arrays;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.apache.fineract.interoperation.domain.InteropIdentifierType;
public class InteropIdentifierRequestData {
    static final String[] PARAMS = { PARAM_ACCOUNT_ID };
    @NotEmpty
    private final InteropIdentifierType idType;
    @NotEmpty
    private final String idValue;
    private final String subIdOrType;
    @NotEmpty
    private final String accountId;
    public InteropIdentifierRequestData(@NotNull InteropIdentifierType idType, @NotNull String idValue, String subIdOrType,
            String accountId) {
        this.idType = idType;
        this.idValue = idValue;
        this.subIdOrType = subIdOrType;
        this.accountId = accountId;
    }
    public InteropIdentifierType getIdType() {
        return idType;
    }
    public String getIdValue() {
        return idValue;
    }
    public String getSubIdOrType() {
        return subIdOrType;
    }
    public String getAccountId() {
        return accountId;
    }
    public static InteropIdentifierRequestData validateAndParse(final DataValidatorBuilder dataValidator,
            @NotNull InteropIdentifierType idType, @NotNull String idValue, String subIdOrType, JsonObject element,
            FromJsonHelper jsonHelper) {
        if (element == null) {
            return null;
        }
        jsonHelper.checkForUnsupportedParameters(element, Arrays.asList(PARAMS));
        String accountId = jsonHelper.extractStringNamed(PARAM_ACCOUNT_ID, element);
        DataValidatorBuilder dataValidatorCopy = dataValidator.reset().parameter(PARAM_ACCOUNT_ID).value(accountId).notBlank();
        dataValidator.merge(dataValidatorCopy);
        return dataValidator.hasError() ? null : new InteropIdentifierRequestData(idType, idValue, subIdOrType, accountId);
    }
}
