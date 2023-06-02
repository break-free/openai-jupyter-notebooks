
package org.apache.fineract.portfolio.loanaccount.domain;
import java.util.Comparator;
public class LoanTermVariationsComparator implements Comparator<LoanTermVariations> {
    @Override
    public int compare(final LoanTermVariations o1, final LoanTermVariations o2) {
        int compareResult = 0;
        final int comparsion = o1.getTermApplicableFrom().compareTo(o2.getTermApplicableFrom());
        if (comparsion == 0) {
            if (o2.getTermType().isDueDateVariation() || o2.getTermType().isInsertInstallment()) {
                compareResult = 1;
            }
        } else {
            compareResult = comparsion;
        }
        return compareResult;
    }
}
