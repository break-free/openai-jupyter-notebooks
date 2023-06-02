
package org.apache.fineract.portfolio.savings.domain;
import java.util.Comparator;
public class SavingsAccountTransactionComparator implements Comparator<SavingsAccountTransaction> {
    @Override
    public int compare(final SavingsAccountTransaction o1, final SavingsAccountTransaction o2) {
        int compareResult = 0;
        final int comparsion = o1.transactionLocalDate().compareTo(o2.transactionLocalDate());
        if (comparsion == 0) {
            compareResult = o1.getCreatedDate().compareTo(o2.getCreatedDate());
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
