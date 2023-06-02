
package org.apache.fineract.portfolio.loanaccount.loanschedule.data;
import java.math.BigDecimal;
import java.util.Collection;
import org.apache.fineract.organisation.monetary.data.CurrencyData;
@SuppressWarnings("unused")
public class LoanScheduleData {
    private final CurrencyData currency;
    private final Integer loanTermInDays;
    private final BigDecimal totalPrincipalDisbursed;
    private final BigDecimal totalPrincipalExpected;
    private final BigDecimal totalPrincipalPaid;
    private final BigDecimal totalInterestCharged;
    private final BigDecimal totalFeeChargesCharged;
    private final BigDecimal totalPenaltyChargesCharged;
    private final BigDecimal totalWaived;
    private final BigDecimal totalWrittenOff;
    private final BigDecimal totalRepaymentExpected;
    private final BigDecimal totalRepayment;
    private final BigDecimal totalPaidInAdvance;
    private final BigDecimal totalPaidLate;
    private final BigDecimal totalOutstanding;
    private final Collection<LoanSchedulePeriodData> periods;
    private Collection<LoanSchedulePeriodData> futurePeriods;
    public LoanScheduleData(final CurrencyData currency, final Collection<LoanSchedulePeriodData> periods, final Integer loanTermInDays,
            final BigDecimal totalPrincipalDisbursed, final BigDecimal totalPrincipalExpected, final BigDecimal totalPrincipalPaid,
            final BigDecimal totalInterestCharged, final BigDecimal totalFeeChargesCharged, final BigDecimal totalPenaltyChargesCharged,
            final BigDecimal totalWaived, final BigDecimal totalWrittenOff, final BigDecimal totalRepaymentExpected,
            final BigDecimal totalRepayment, final BigDecimal totalPaidInAdvance, final BigDecimal totalPaidLate,
            final BigDecimal totalOutstanding) {
        this.currency = currency;
        this.periods = periods;
        this.loanTermInDays = loanTermInDays;
        this.totalPrincipalDisbursed = totalPrincipalDisbursed;
        this.totalPrincipalExpected = totalPrincipalExpected;
        this.totalPrincipalPaid = totalPrincipalPaid;
        this.totalInterestCharged = totalInterestCharged;
        this.totalFeeChargesCharged = totalFeeChargesCharged;
        this.totalPenaltyChargesCharged = totalPenaltyChargesCharged;
        this.totalWaived = totalWaived;
        this.totalWrittenOff = totalWrittenOff;
        this.totalRepaymentExpected = totalRepaymentExpected;
        this.totalRepayment = totalRepayment;
        this.totalPaidInAdvance = totalPaidInAdvance;
        this.totalPaidLate = totalPaidLate;
        this.totalOutstanding = totalOutstanding;
    }
    public LoanScheduleData(final CurrencyData currency, final Collection<LoanSchedulePeriodData> periods, final Integer loanTermInDays,
            final BigDecimal totalPrincipalDisbursed, final BigDecimal totalPrincipalExpected, final BigDecimal totalInterestCharged,
            final BigDecimal totalFeeChargesCharged, final BigDecimal totalPenaltyChargesCharged, final BigDecimal totalRepaymentExpected) {
        this.currency = currency;
        this.periods = periods;
        this.loanTermInDays = loanTermInDays;
        this.totalPrincipalDisbursed = totalPrincipalDisbursed;
        this.totalPrincipalExpected = totalPrincipalExpected;
        this.totalPrincipalPaid = null;
        this.totalInterestCharged = totalInterestCharged;
        this.totalFeeChargesCharged = totalFeeChargesCharged;
        this.totalPenaltyChargesCharged = totalPenaltyChargesCharged;
        this.totalWaived = null;
        this.totalWrittenOff = null;
        this.totalRepaymentExpected = totalRepaymentExpected;
        this.totalRepayment = null;
        this.totalPaidInAdvance = null;
        this.totalPaidLate = null;
        this.totalOutstanding = null;
    }
    public Collection<LoanSchedulePeriodData> getPeriods() {
        return this.periods;
    }
    public void updateFuturePeriods(Collection<LoanSchedulePeriodData> futurePeriods) {
        this.futurePeriods = futurePeriods;
    }
}
