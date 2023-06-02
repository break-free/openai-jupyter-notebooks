
package org.apache.fineract.portfolio.loanaccount.domain;
import java.util.HashMap;
import java.util.Map;
public class ChangedTransactionDetail {
    private final Map<Long, LoanTransaction> newTransactionMappings = new HashMap<>();
    public Map<Long, LoanTransaction> getNewTransactionMappings() {
        return this.newTransactionMappings;
    }
}
