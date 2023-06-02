
package org.apache.fineract.accounting.journalentry.data;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.accounting.glaccount.data.GLAccountData;
@RequiredArgsConstructor
@Getter
public final class OfficeOpeningBalancesData {
    private final Long officeId;
    private final String officeName;
    private final LocalDate transactionDate;
    private final GLAccountData contraAccount;
    private final List<JournalEntryData> assetAccountOpeningBalances;
    private final List<JournalEntryData> liabityAccountOpeningBalances;
    private final List<JournalEntryData> incomeAccountOpeningBalances;
    private final List<JournalEntryData> equityAccountOpeningBalances;
    private final List<JournalEntryData> expenseAccountOpeningBalances;
    public static OfficeOpeningBalancesData createNew(final Long officeId, final String officeName, final LocalDate transactionDate,
            final GLAccountData contraAccount, final List<JournalEntryData> assetAccountOpeningBalances,
            final List<JournalEntryData> liabityAccountOpeningBalances, final List<JournalEntryData> incomeAccountOpeningBalances,
            final List<JournalEntryData> equityAccountOpeningBalances, final List<JournalEntryData> expenseAccountOpeningBalances) {
        return new OfficeOpeningBalancesData(officeId, officeName, transactionDate, contraAccount, assetAccountOpeningBalances,
                liabityAccountOpeningBalances, incomeAccountOpeningBalances, equityAccountOpeningBalances, expenseAccountOpeningBalances);
    }
}
