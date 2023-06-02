
package org.apache.fineract.portfolio.savings.data;
import java.time.LocalDate;
public final class ClosingOfSavingsAccounts {
    private final transient Integer rowIndex;
    private final transient Long accountId;
    private final LocalDate closedOnDate;
    private final Long onAccountClosureId;
    private final Long toSavingsAccountId;
    private final String dateFormat;
    private final String accountType;
    private final String locale;
    private final String note;
    public static ClosingOfSavingsAccounts importInstance(Long accountId, LocalDate closedOnDate, Long onAccountClosureId,
            Long toSavingsAccountId, String accountType, Integer rowIndex, String locale, String dateFormat) {
        return new ClosingOfSavingsAccounts(accountId, closedOnDate, onAccountClosureId, toSavingsAccountId, accountType, rowIndex, locale,
                dateFormat);
    }
    private ClosingOfSavingsAccounts(Long accountId, LocalDate closedOnDate, Long onAccountClosureId, Long toSavingsAccountId,
            String accountType, Integer rowIndex, String locale, String dateFormat) {
        this.accountId = accountId;
        this.closedOnDate = closedOnDate;
        this.onAccountClosureId = onAccountClosureId;
        this.toSavingsAccountId = toSavingsAccountId;
        this.accountType = accountType;
        this.rowIndex = rowIndex;
        this.dateFormat = dateFormat;
        this.locale = locale;
        this.note = "";
    }
    public Integer getRowIndex() {
        return rowIndex;
    }
    public Long getAccountId() {
        return accountId;
    }
    public LocalDate getClosedOnDate() {
        return closedOnDate;
    }
    public Long getOnAccountClosureId() {
        return onAccountClosureId;
    }
    public Long getToSavingsAccountId() {
        return toSavingsAccountId;
    }
    public String getDateFormat() {
        return dateFormat;
    }
    public String getAccountType() {
        return accountType;
    }
    public String getLocale() {
        return locale;
    }
    public String getNote() {
        return note;
    }
}
