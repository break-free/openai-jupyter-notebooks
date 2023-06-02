
package org.apache.fineract.portfolio.loanaccount.domain;
public interface LoanLifecycleStateMachine {
    LoanStatus transition(LoanEvent loanEvent, LoanStatus from);
}
