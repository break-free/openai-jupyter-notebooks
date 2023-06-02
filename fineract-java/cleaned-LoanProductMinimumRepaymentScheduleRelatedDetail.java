
package org.apache.fineract.portfolio.loanproduct.domain;
import java.math.BigDecimal;
import org.apache.fineract.organisation.monetary.domain.MonetaryCurrency;
import org.apache.fineract.organisation.monetary.domain.Money;
import org.apache.fineract.portfolio.common.domain.PeriodFrequencyType;
public interface LoanProductMinimumRepaymentScheduleRelatedDetail {
    MonetaryCurrency getCurrency();
    Money getPrincipal();
    Integer graceOnInterestCharged();
    Integer graceOnInterestPayment();
    Integer graceOnPrincipalPayment();
    Integer recurringMoratoriumOnPrincipalPeriods();
    Money getInArrearsTolerance();
    BigDecimal getNominalInterestRatePerPeriod();
    PeriodFrequencyType getInterestPeriodFrequencyType();
    BigDecimal getAnnualNominalInterestRate();
    InterestMethod getInterestMethod();
    InterestCalculationPeriodMethod getInterestCalculationPeriodMethod();
    Integer getRepayEvery();
    PeriodFrequencyType getRepaymentPeriodFrequencyType();
    Integer getNumberOfRepayments();
    AmortizationMethod getAmortizationMethod();
    Integer getGraceOnDueDate();
}
