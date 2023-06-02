
package org.apache.fineract.portfolio.savings.data;
import java.time.LocalDate;
public final class SavingsApproval {
    private final transient Integer rowIndex;
    private final LocalDate approvedOnDate;
    private final String dateFormat;
    private final String locale;
    private final String note;
    public static SavingsApproval importInstance(LocalDate approvedOnDate, Integer rowIndex, String locale, String dateFormat) {
        return new SavingsApproval(approvedOnDate, rowIndex, locale, dateFormat);
    }
    private SavingsApproval(LocalDate approvedOnDate, Integer rowIndex, String locale, String dateFormat) {
        this.approvedOnDate = approvedOnDate;
        this.rowIndex = rowIndex;
        this.dateFormat = dateFormat;
        this.locale = locale;
        this.note = "";
    }
    public LocalDate getApprovedOnDate() {
        return approvedOnDate;
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
    public String getNote() {
        return note;
    }
}
