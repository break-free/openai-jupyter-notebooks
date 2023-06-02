
package org.apache.fineract.portfolio.savings.domain;
import java.util.Comparator;
import org.apache.fineract.portfolio.savings.data.SavingsAccountTransactionData;
public class SavingsAccountTransactionDataComparator implements Comparator<SavingsAccountTransactionData> {
    @Override
    public int compare(final SavingsAccountTransactionData o1, final SavingsAccountTransactionData o2) {
        int compareResult = 0;
        final int comparsion = o1.getTransactionDate().compareTo(o2.getLastTransactionDate());
        if (comparsion == 0) {
            compareResult = o1.getSubmittedOnDate().compareTo(o2.getSubmittedOnDate());
            if (compareResult == 0 && o1.getId() != null && o2.getId() != null) {
                compareResult = o1.getId().compareTo(o2.getId());
            } else {
                compareResult = comparsion;
            }
        } else {
            compareResult = comparsion;
        }
        return compareResult;
    }
}
