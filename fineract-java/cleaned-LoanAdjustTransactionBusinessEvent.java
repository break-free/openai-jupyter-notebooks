
package org.apache.fineract.portfolio.businessevent.domain.loan;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.fineract.portfolio.businessevent.domain.AbstractBusinessEvent;
import org.apache.fineract.portfolio.loanaccount.domain.LoanTransaction;
public class LoanAdjustTransactionBusinessEvent extends AbstractBusinessEvent<LoanAdjustTransactionBusinessEvent.Data> {
    public LoanAdjustTransactionBusinessEvent(Data value) {
        super(value);
    }
    @RequiredArgsConstructor
    @Getter
    public static class Data {
        private final LoanTransaction transactionToAdjust;
        @Setter
        private LoanTransaction newTransactionDetail;
    }
}
