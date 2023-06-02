
package org.apache.fineract.portfolio.collectionsheet.serialization;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.infrastructure.core.exception.InvalidJsonException;
import org.apache.fineract.infrastructure.core.serialization.AbstractFromApiJsonDeserializer;
import org.apache.fineract.infrastructure.core.serialization.FromApiJsonDeserializer;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.apache.fineract.portfolio.collectionsheet.command.CollectionSheetBulkDisbursalCommand;
import org.apache.fineract.portfolio.collectionsheet.command.SingleDisbursalCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public final class CollectionSheetBulkDisbursalCommandFromApiJsonDeserializer
        extends AbstractFromApiJsonDeserializer<CollectionSheetBulkDisbursalCommand> {
    private final FromJsonHelper fromApiJsonHelper;
    @Autowired
    public CollectionSheetBulkDisbursalCommandFromApiJsonDeserializer(final FromJsonHelper fromApiJsonHelper) {
        this.fromApiJsonHelper = fromApiJsonHelper;
    }
    @Override
    public CollectionSheetBulkDisbursalCommand commandFromApiJson(final String json) {
        if (StringUtils.isBlank(json)) {
            throw new InvalidJsonException();
        }
        final JsonElement element = this.fromApiJsonHelper.parse(json);
        final JsonObject topLevelJsonElement = element.getAsJsonObject();
        final Locale locale = this.fromApiJsonHelper.extractLocaleParameter(topLevelJsonElement);
        final LocalDate transactionDate = this.fromApiJsonHelper.extractLocalDateNamed("transactionDate", element);
        final String note = this.fromApiJsonHelper.extractStringNamed("note", element);
        SingleDisbursalCommand[] loanDisbursementTransactions = null;
        if (element.isJsonObject()) {
            if (topLevelJsonElement.has("bulkDisbursementTransactions")
                    && topLevelJsonElement.get("bulkDisbursementTransactions").isJsonArray()) {
                final JsonArray array = topLevelJsonElement.get("bulkDisbursementTransactions").getAsJsonArray();
                loanDisbursementTransactions = new SingleDisbursalCommand[array.size()];
                for (int i = 0; i < array.size(); i++) {
                    final JsonObject loanTransactionElement = array.get(i).getAsJsonObject();
                    final Long loanId = this.fromApiJsonHelper.extractLongNamed("loanId", loanTransactionElement);
                    final BigDecimal disbursementAmount = this.fromApiJsonHelper.extractBigDecimalNamed("transactionAmount",
                            loanTransactionElement, locale);
                    loanDisbursementTransactions[i] = new SingleDisbursalCommand(loanId, disbursementAmount, transactionDate);
                }
            }
        }
        return new CollectionSheetBulkDisbursalCommand(note, transactionDate, loanDisbursementTransactions);
    }
}
