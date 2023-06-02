
package org.apache.fineract.portfolio.loanaccount.loanschedule.domain;
import org.apache.fineract.portfolio.loanproduct.domain.InterestMethod;
import org.springframework.stereotype.Component;
@Component
public class DefaultLoanScheduleGeneratorFactory implements LoanScheduleGeneratorFactory {
    @Override
    public LoanScheduleGenerator create(final InterestMethod interestMethod) {
        LoanScheduleGenerator loanScheduleGenerator = null;
        switch (interestMethod) {
            case FLAT:
                loanScheduleGenerator = new FlatInterestLoanScheduleGenerator();
            break;
            case DECLINING_BALANCE:
                loanScheduleGenerator = new DecliningBalanceInterestLoanScheduleGenerator();
            break;
            case INVALID:
            break;
        }
        return loanScheduleGenerator;
    }
}
