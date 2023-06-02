
package org.apache.fineract.portfolio.loanaccount.data;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.apache.fineract.organisation.monetary.data.CurrencyData;
public class RepaymentScheduleRelatedLoanData {
    private final LocalDate expectedDisbursementDate;
    private final LocalDate actualDisbursementDate;
    private final CurrencyData currency;
    private final BigDecimal principal;
    private final BigDecimal netDisbursalAmount;
    private final BigDecimal inArrearsTolerance;
    private final BigDecimal totalFeeChargesAtDisbursement;
    public RepaymentScheduleRelatedLoanData(final LocalDate expectedDisbursementDate, final LocalDate actualDisbursementDate,
            final CurrencyData currency, final BigDecimal principal, final BigDecimal inArrearsTolerance,
            final BigDecimal totalFeeChargesAtDisbursement) {
        this.expectedDisbursementDate = expectedDisbursementDate;
        this.actualDisbursementDate = actualDisbursementDate;
        this.currency = currency;
        this.principal = principal;
        this.inArrearsTolerance = inArrearsTolerance;
        this.totalFeeChargesAtDisbursement = totalFeeChargesAtDisbursement;
        this.netDisbursalAmount = this.principal.subtract(this.totalFeeChargesAtDisbursement);
    }
    public LocalDate disbursementDate() {
        LocalDate disbursementDate = this.expectedDisbursementDate;
        if (this.actualDisbursementDate != null) {
            disbursementDate = this.actualDisbursementDate;
        }
        return disbursementDate;
    }
    public BigDecimal amount() {
        return this.principal;
    }
    public boolean isDisbursed() {
        return this.actualDisbursementDate != null;
    }
    public CurrencyData getCurrency() {
        return this.currency;
    }
    public BigDecimal getInArrearsTolerance() {
        return this.inArrearsTolerance;
    }
    public BigDecimal getTotalFeeChargesAtDisbursement() {
        return this.totalFeeChargesAtDisbursement;
    }
    public DisbursementData disbursementData() {
        BigDecimal waivedChargeAmount = null;
        return new DisbursementData(null, this.expectedDisbursementDate, this.actualDisbursementDate, this.principal,
                this.netDisbursalAmount, null, null, waivedChargeAmount);
    }
}
