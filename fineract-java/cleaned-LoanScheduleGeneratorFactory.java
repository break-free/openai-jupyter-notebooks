
package org.apache.fineract.portfolio.loanaccount.loanschedule.domain;
import org.apache.fineract.portfolio.loanproduct.domain.InterestMethod;
public interface LoanScheduleGeneratorFactory {
    LoanScheduleGenerator create(InterestMethod interestMethod);
}
