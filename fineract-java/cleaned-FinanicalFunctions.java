
package org.apache.fineract.portfolio.loanaccount.loanschedule.domain;
public final class FinanicalFunctions {
    private FinanicalFunctions() {
    }
    public static double pmt(final double interestRateFraction, final double numberOfPayments, final double principal,
            final double futureValue, final boolean type) {
        double payment = 0;
        if (interestRateFraction == 0) {
            payment = -1 * (futureValue + principal) / numberOfPayments;
        } else {
            final double r1 = interestRateFraction + 1;
            payment = (futureValue + principal * Math.pow(r1, numberOfPayments)) * interestRateFraction
                    / ((type ? r1 : 1) * (1 - Math.pow(r1, numberOfPayments)));
        }
        return payment;
    }
    public static int nop(final double interestRateFraction, final double emiAmount, final double principal, final double futureValue,
            final boolean type) {
        double numberOfPayments = 0;
        if (interestRateFraction == 0) {
            numberOfPayments = ((Double) (-1 * (futureValue + principal) / emiAmount)).intValue();
        } else {
            final double r1 = interestRateFraction + 1;
            numberOfPayments = (futureValue + principal * Math.pow(r1, emiAmount)) * interestRateFraction
                    / ((type ? r1 : 1) * (1 - Math.pow(r1, emiAmount)));
        }
        return Double.valueOf(numberOfPayments).intValue();
    }
}
