
package org.apache.fineract.portfolio.savings.data;
import java.time.LocalDate;
public final class SavingsActivation {
    private final transient Integer rowIndex;
    private final LocalDate activatedOnDate;
    private final String dateFormat;
    private final String locale;
    public static SavingsActivation importInstance(LocalDate activatedOnDate, Integer rowIndex, String locale, String dateFormat) {
        return new SavingsActivation(activatedOnDate, rowIndex, locale, dateFormat);
    }
    private SavingsActivation(LocalDate activatedOnDate, Integer rowIndex, String locale, String dateFormat) {
        this.activatedOnDate = activatedOnDate;
        this.rowIndex = rowIndex;
        this.dateFormat = dateFormat;
        this.locale = locale;
    }
    public LocalDate getActivatedOnDate() {
        return activatedOnDate;
    }
    public String getLocale() {
        return locale;
    }
    public String getDateFormat() {
        return dateFormat;
    }
    public Integer getRowIndex() {
        return rowIndex;
    }
}
