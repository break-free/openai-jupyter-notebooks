
package org.apache.fineract.accounting.journalentry.data;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Getter
@Setter
public class SharesDTO {
    private Long shareAccountId;
    private Long shareProductId;
    private Long officeId;
    private String currencyCode;
    private boolean cashBasedAccountingEnabled;
    private boolean accrualBasedAccountingEnabled;
    private List<SharesTransactionDTO> newTransactions;
}
