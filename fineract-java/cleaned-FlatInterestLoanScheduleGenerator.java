
package org.apache.fineract.portfolio.loanaccount.loanschedule.domain;
import java.math.MathContext;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import org.apache.fineract.organisation.monetary.domain.Money;
import org.apache.fineract.portfolio.loanaccount.data.LoanTermVariationsData;
public class FlatInterestLoanScheduleGenerator extends AbstractLoanScheduleGenerator {
    @Override
    public PrincipalInterest calculatePrincipalInterestComponentsForPeriod(final PaymentPeriodsInOneYearCalculator calculator,
            final double interestCalculationGraceOnRepaymentPeriodFraction, final Money totalCumulativePrincipal,
            Money totalCumulativeInterest, Money totalInterestDueForLoan, final Money cumulatingInterestPaymentDueToGrace,
            final Money outstandingBalance, final LoanApplicationTerms loanApplicationTerms, final int periodNumber, final MathContext mc,
            @SuppressWarnings("unused") TreeMap<LocalDate, Money> principalVariation,
            @SuppressWarnings("unused") Map<LocalDate, Money> compoundingMap, LocalDate periodStartDate, LocalDate periodEndDate,
            @SuppressWarnings("unused") Collection<LoanTermVariationsData> termVariations) {
        final PrincipalInterest result = loanApplicationTerms.calculateTotalInterestForPeriod(calculator,
                interestCalculationGraceOnRepaymentPeriodFraction, periodNumber, mc, cumulatingInterestPaymentDueToGrace,
                outstandingBalance, periodStartDate, periodEndDate);
        Money interestForThisInstallment = result.interest();
        Money principalForThisInstallment = loanApplicationTerms.calculateTotalPrincipalForPeriod(calculator, outstandingBalance,
                periodNumber, mc, interestForThisInstallment);
        final Money interestBroughtForwardDueToGrace = result.interestPaymentDueToGrace();
        final Money totalCumulativePrincipalToDate = totalCumulativePrincipal.plus(principalForThisInstallment);
        final Money totalCumulativeInterestToDate = totalCumulativeInterest.plus(interestForThisInstallment);
        principalForThisInstallment = loanApplicationTerms.adjustPrincipalIfLastRepaymentPeriod(principalForThisInstallment,
                totalCumulativePrincipalToDate, periodNumber);
        interestForThisInstallment = loanApplicationTerms.adjustInterestIfLastRepaymentPeriod(interestForThisInstallment,
                totalCumulativeInterestToDate, totalInterestDueForLoan, periodNumber);
        return new PrincipalInterest(principalForThisInstallment, interestForThisInstallment, interestBroughtForwardDueToGrace);
    }
}
