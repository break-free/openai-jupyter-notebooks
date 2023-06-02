
package org.apache.fineract.portfolio.loanaccount.loanschedule.domain;
import org.apache.fineract.organisation.monetary.domain.Money;
public class PrincipalInterest {
    private final Money principal;
    private final Money interest;
    private final Money interestPaymentDueToGrace;
    private Money rescheduleInterestPortion;
    public PrincipalInterest(final Money principal, final Money interest, final Money interestPaymentDueToGrace) {
        this.principal = principal;
        this.interest = interest;
        this.interestPaymentDueToGrace = interestPaymentDueToGrace;
    }
    public Money principal() {
        return this.principal;
    }
    public Money interest() {
        return this.interest;
    }
    public Money interestPaymentDueToGrace() {
        return this.interestPaymentDueToGrace;
    }
    public Money getRescheduleInterestPortion() {
        return rescheduleInterestPortion;
    }
    public void setRescheduleInterestPortion(Money rescheduleInterestPortion) {
        this.rescheduleInterestPortion = rescheduleInterestPortion;
    }
}
