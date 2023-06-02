
package org.apache.fineract.portfolio.loanaccount.domain;
import java.util.Comparator;
import org.apache.fineract.portfolio.loanaccount.data.LoanTransactionData;
public class LoanRepaymentDataComparator implements Comparator<LoanTransactionData> {
    @Override
    public int compare(final LoanTransactionData o1, final LoanTransactionData o2) {
        return o2.dateOf().compareTo(o1.dateOf());
    }
}
