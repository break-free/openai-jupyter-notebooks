
package org.apache.fineract.interoperation.data;
import static org.apache.fineract.interoperation.util.InteropUtil.PARAM_ACCOUNT_ID;
import static org.apache.fineract.interoperation.util.InteropUtil.PARAM_AMOUNT;
import static org.apache.fineract.interoperation.util.InteropUtil.PARAM_DATE_FORMAT;
import static org.apache.fineract.interoperation.util.InteropUtil.PARAM_EXPIRATION;
import static org.apache.fineract.interoperation.util.InteropUtil.PARAM_EXTENSION_LIST;
import static org.apache.fineract.interoperation.util.InteropUtil.PARAM_GEO_CODE;
import static org.apache.fineract.interoperation.util.InteropUtil.PARAM_LOCALE;
import static org.apache.fineract.interoperation.util.InteropUtil.PARAM_NOTE;
import static org.apache.fineract.interoperation.util.InteropUtil.PARAM_REQUEST_CODE;
import static org.apache.fineract.interoperation.util.InteropUtil.PARAM_TRANSACTION_CODE;
import static org.apache.fineract.interoperation.util.InteropUtil.PARAM_TRANSACTION_ROLE;
import static org.apache.fineract.interoperation.util.InteropUtil.PARAM_TRANSACTION_TYPE;
import com.google.gson.JsonObject;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.apache.fineract.interoperation.domain.InteropTransactionRole;
public class InteropTransactionRequestData extends InteropRequestData {
    static final String[] PARAMS = { PARAM_TRANSACTION_CODE, PARAM_REQUEST_CODE, PARAM_ACCOUNT_ID, PARAM_AMOUNT, PARAM_TRANSACTION_ROLE,
            PARAM_TRANSACTION_TYPE, PARAM_NOTE, PARAM_GEO_CODE, PARAM_EXPIRATION, PARAM_EXTENSION_LIST, PARAM_LOCALE, PARAM_DATE_FORMAT };
    public InteropTransactionRequestData(@NotNull String transactionCode, @NotNull String requestCode, @NotNull String accountId,
            @NotNull MoneyData amount, @NotNull InteropTransactionTypeData transactionType, String note, GeoCodeData geoCode,
            LocalDateTime expiration, List<ExtensionData> extensionList) {
        super(transactionCode, requestCode, accountId, amount, InteropTransactionRole.PAYER, transactionType, note, geoCode, expiration,
                extensionList);
    }
    public InteropTransactionRequestData(@NotNull String transactionCode, @NotNull String requestCode, @NotNull String accountId,
            @NotNull MoneyData amount, @NotNull InteropTransactionTypeData transactionType) {
        this(transactionCode, requestCode, accountId, amount, transactionType, null, null, null, null);
    }
    private InteropTransactionRequestData(InteropRequestData other) {
        this(other.getTransactionCode(), other.getRequestCode(), other.getAccountId(), other.getAmount(), other.getTransactionType(),
                other.getNote(), other.getGeoCode(), other.getExpiration(), other.getExtensionList());
    }
    public static InteropTransactionRequestData validateAndParse(final DataValidatorBuilder dataValidator, JsonObject element,
            FromJsonHelper jsonHelper) {
        if (element == null) {
            return null;
        }
        jsonHelper.checkForUnsupportedParameters(element, Arrays.asList(PARAMS));
        InteropRequestData interopRequestData = InteropRequestData.validateAndParse(dataValidator, element, jsonHelper);
        DataValidatorBuilder dataValidatorCopy = dataValidator.reset().parameter(PARAM_REQUEST_CODE)
                .value(interopRequestData.getRequestCode()).notNull();
        dataValidatorCopy = dataValidatorCopy.reset().parameter(PARAM_TRANSACTION_TYPE).value(interopRequestData.getTransactionType())
                .notNull();
        dataValidatorCopy = dataValidatorCopy.reset().parameter(PARAM_TRANSACTION_ROLE).value(interopRequestData.getTransactionRole())
                .ignoreIfNull().isOneOfTheseValues(InteropTransactionRole.PAYER);
        dataValidator.merge(dataValidatorCopy);
        return dataValidator.hasError() ? null : new InteropTransactionRequestData(interopRequestData);
    }
}
