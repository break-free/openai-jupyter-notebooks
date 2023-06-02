
package org.apache.fineract.accounting.journalentry.api;
import java.time.LocalDate;
import java.util.Locale;
import javax.ws.rs.WebApplicationException;
import org.apache.fineract.infrastructure.core.serialization.JsonParserHelper;
public class DateParam {
    private final String dateAsString;
    public DateParam(final String dateStr) throws WebApplicationException {
        this.dateAsString = dateStr;
    }
    public LocalDate getDate(final String parameterName, final String dateFormat, final String localeAsString) {
        final Locale locale = JsonParserHelper.localeFromString(localeAsString);
        return JsonParserHelper.convertFrom(this.dateAsString, parameterName, dateFormat, locale);
    }
}
