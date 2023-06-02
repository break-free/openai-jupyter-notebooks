
package org.apache.fineract.accounting.journalentry.data;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Getter
public class LoanDTO {
    @Setter
    private Long loanId;
    @Setter
    private Long loanProductId;
    @Setter
    private Long officeId;
    @Setter
    private String currencyCode;
    @Setter
    private boolean cashBasedAccountingEnabled;
    private final boolean upfrontAccrualBasedAccountingEnabled;
    private final boolean periodicAccrualBasedAccountingEnabled;
    @Setter
    private List<LoanTransactionDTO> newLoanTransactions;
}
