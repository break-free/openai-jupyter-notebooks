
package org.apache.fineract.infrastructure.bulkimport.populator.comparator;
import java.util.Comparator;
import org.apache.fineract.portfolio.loanaccount.data.LoanAccountData;
public class LoanComparatorByStatusActive implements Comparator<LoanAccountData> {
    @Override
    public int compare(LoanAccountData o1, LoanAccountData o2) {
        boolean isData1StatusActive = o1.getStatusStringValue().equals("Active");
        boolean isData2StatusActive = o2.getStatusStringValue().equals("Active");
        if (isData1StatusActive && isData2StatusActive) {
            return 0;
        }
        if (isData1StatusActive) {
            return -1;
        }
        if (isData2StatusActive) {
            return 1;
        }
        return 0;
    }
}
