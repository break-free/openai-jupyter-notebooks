
package org.apache.fineract.portfolio.self.shareaccounts.data;
import com.google.gson.JsonElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.exception.InvalidJsonException;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.apache.fineract.portfolio.accounts.constants.ShareAccountApiConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class SelfShareAccountsDataValidator {
    private final FromJsonHelper fromApiJsonHelper;
    @Autowired
    public SelfShareAccountsDataValidator(final FromJsonHelper fromApiJsonHelper) {
        this.fromApiJsonHelper = fromApiJsonHelper;
    }
    public HashMap<String, Object> validateShareAccountApplication(final String json) {
        if (StringUtils.isBlank(json)) {
            throw new InvalidJsonException();
        }
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors)
                .resource(ShareAccountApiConstants.shareEntityType);
        final JsonElement element = this.fromApiJsonHelper.parse(json);
        final String clientId = this.fromApiJsonHelper.extractStringNamed(ShareAccountApiConstants.clientid_paramname, element);
        baseDataValidator.reset().parameter(ShareAccountApiConstants.clientid_paramname).value(clientId).notNull().longGreaterThanZero();
        if (!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException(dataValidationErrors);
        }
        HashMap<String, Object> retAttr = new HashMap<>();
        retAttr.put(ShareAccountApiConstants.clientid_paramname, Long.parseLong(clientId));
        return retAttr;
    }
}
