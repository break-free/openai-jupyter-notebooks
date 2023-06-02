
package org.apache.fineract.portfolio.businessevent.domain.loan.product;
import org.apache.fineract.portfolio.loanproduct.domain.LoanProduct;
public class LoanProductCreateBusinessEvent extends LoanProductBusinessEvent {
    public LoanProductCreateBusinessEvent(LoanProduct value) {
        super(value);
    }
}
