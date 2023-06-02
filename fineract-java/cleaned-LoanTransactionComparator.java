
package org.apache.fineract.portfolio.loanaccount.domain;
import java.util.Comparator;
public class LoanTransactionComparator implements Comparator<LoanTransaction> {
    @Override
    public int compare(final LoanTransaction o1, final LoanTransaction o2) {
        int compareResult = 0;
        final int comparsion = o1.getTransactionDate().compareTo(o2.getTransactionDate());
        if (comparsion == 0) {
            int comparisonBasedOnCreatedDate = 0;
            if (o1.isIncomePosting() && o2.isNotIncomePosting()) {
                compareResult = -1;
            } else if (o1.isNotIncomePosting() && o2.isIncomePosting()) {
                compareResult = 1;
            } else {
                compareResult = 0;
            }
            if (o1.getCreatedDateTime() != null && o2.getCreatedDateTime() != null) {
                comparisonBasedOnCreatedDate = o1.getCreatedDateTime().compareTo(o2.getCreatedDateTime());
            }
            if (comparisonBasedOnCreatedDate == 0) {
                if (o1.isWaiver() && o2.isNotWaiver()) {
                    compareResult = -1;
                } else if (o1.isNotWaiver() && o2.isWaiver()) {
                    compareResult = 1;
                } else {
                    compareResult = 0;
                }
            }
        } else {
            compareResult = comparsion;
        }
        return compareResult;
    }
}
