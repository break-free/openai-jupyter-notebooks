
package org.apache.fineract.portfolio.businessevent.domain.loan.product;
import org.apache.fineract.portfolio.businessevent.domain.AbstractBusinessEvent;
import org.apache.fineract.portfolio.loanproduct.domain.LoanProduct;
public abstract class LoanProductBusinessEvent extends AbstractBusinessEvent<LoanProduct> {
    public LoanProductBusinessEvent(LoanProduct value) {
        super(value);
    }
}
