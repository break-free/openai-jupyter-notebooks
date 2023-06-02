
package org.apache.fineract.portfolio.floatingrates.data;
import java.math.BigDecimal;
import java.time.LocalDate;
public class InterestRatePeriodData {
    private LocalDate fromDate;
    private final BigDecimal interestRate;
    private final boolean isDifferentialToBLR;
    private final LocalDate blrFromDate;
    private final BigDecimal blrInterestRate;
    private BigDecimal loanDifferentialInterestRate;
    private BigDecimal loanProductDifferentialInterestRate;
    private BigDecimal effectiveInterestRate;
    public InterestRatePeriodData(LocalDate fromDate, BigDecimal interestRate, boolean isDifferentialToBLR, LocalDate blrFromDate,
            BigDecimal blrInterestRate) {
        this.fromDate = fromDate;
        this.interestRate = interestRate;
        this.isDifferentialToBLR = isDifferentialToBLR;
        this.blrFromDate = blrFromDate;
        this.blrInterestRate = blrInterestRate;
    }
    public LocalDate getFromDate() {
        return this.fromDate;
    }
    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }
    public BigDecimal getInterestRate() {
        return this.interestRate;
    }
    public boolean isDifferentialToBLR() {
        return this.isDifferentialToBLR;
    }
    public LocalDate getBlrFromDate() {
        return this.blrFromDate;
    }
    public BigDecimal getBlrInterestRate() {
        return this.blrInterestRate;
    }
    public BigDecimal getLoanDifferentialInterestRate() {
        return this.loanDifferentialInterestRate;
    }
    public void setLoanDifferentialInterestRate(BigDecimal loanDifferentialInterestRate) {
        this.loanDifferentialInterestRate = loanDifferentialInterestRate;
    }
    public BigDecimal getLoanProductDifferentialInterestRate() {
        return this.loanProductDifferentialInterestRate;
    }
    public void setLoanProductDifferentialInterestRate(BigDecimal loanProductDifferentialInterestRate) {
        this.loanProductDifferentialInterestRate = loanProductDifferentialInterestRate;
    }
    public BigDecimal getEffectiveInterestRate() {
        return this.effectiveInterestRate;
    }
    public void setEffectiveInterestRate(BigDecimal effectiveInterestRate) {
        this.effectiveInterestRate = effectiveInterestRate;
    }
}
