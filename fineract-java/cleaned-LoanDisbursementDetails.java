
package org.apache.fineract.portfolio.loanaccount.domain;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.portfolio.loanaccount.data.DisbursementData;
@Entity
@Table(name = "m_loan_disbursement_detail")
public class LoanDisbursementDetails extends AbstractPersistableCustom {
    @ManyToOne
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;
    @Column(name = "expected_disburse_date")
    private LocalDate expectedDisbursementDate;
    @Column(name = "disbursedon_date")
    private LocalDate actualDisbursementDate;
    @Column(name = "principal", scale = 6, precision = 19, nullable = false)
    private BigDecimal principal;
    @Column(name = "net_disbursal_amount", scale = 6, precision = 19)
    private BigDecimal netDisbursalAmount;
    protected LoanDisbursementDetails() {
    }
    public LoanDisbursementDetails(final LocalDate expectedDisbursementDate, final LocalDate actualDisbursementDate,
            final BigDecimal principal, final BigDecimal netDisbursalAmount) {
        this.expectedDisbursementDate = expectedDisbursementDate;
        this.actualDisbursementDate = actualDisbursementDate;
        this.principal = principal;
        this.netDisbursalAmount = netDisbursalAmount;
    }
    public void updateLoan(final Loan loan) {
        this.loan = loan;
    }
    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof LoanDisbursementDetails)) {
            return false;
        }
        final LoanDisbursementDetails loanDisbursementDetails = (LoanDisbursementDetails) obj;
        if (loanDisbursementDetails.principal.equals(this.principal)
                && loanDisbursementDetails.expectedDisbursementDate.compareTo(this.expectedDisbursementDate) == 0 ? Boolean.TRUE
                        : Boolean.FALSE) {
            return true;
        }
        return false;
    }
    @Override
    public int hashCode() {
        return Objects.hash(expectedDisbursementDate, principal);
    }
    public void copy(final LoanDisbursementDetails disbursementDetails) {
        this.principal = disbursementDetails.principal;
        this.expectedDisbursementDate = disbursementDetails.expectedDisbursementDate;
        this.actualDisbursementDate = disbursementDetails.actualDisbursementDate;
    }
    public LocalDate expectedDisbursementDate() {
        return this.expectedDisbursementDate;
    }
    public LocalDate expectedDisbursementDateAsLocalDate() {
        LocalDate expectedDisburseDate = null;
        if (this.expectedDisbursementDate != null) {
            expectedDisburseDate = this.expectedDisbursementDate;
        }
        return expectedDisburseDate;
    }
    public LocalDate actualDisbursementDate() {
        return this.actualDisbursementDate;
    }
    public BigDecimal principal() {
        return this.principal;
    }
    public void updatePrincipal(BigDecimal principal) {
        this.principal = principal;
    }
    public LocalDate getDisbursementDate() {
        return this.actualDisbursementDate;
    }
    public DisbursementData toData() {
        LocalDate expectedDisburseDate = expectedDisbursementDateAsLocalDate();
        BigDecimal waivedChargeAmount = null;
        return new DisbursementData(getId(), expectedDisburseDate, this.actualDisbursementDate, this.principal, this.netDisbursalAmount,
                null, null, waivedChargeAmount);
    }
    public void updateActualDisbursementDate(LocalDate actualDisbursementDate) {
        this.actualDisbursementDate = actualDisbursementDate;
    }
    public BigDecimal getNetDisbursalAmount() {
        return this.netDisbursalAmount;
    }
    public void setNetDisbursalAmount(BigDecimal netDisbursalAmount) {
        this.netDisbursalAmount = netDisbursalAmount;
    }
    public void updateExpectedDisbursementDateAndAmount(LocalDate expectedDisbursementDate, BigDecimal principal) {
        this.expectedDisbursementDate = expectedDisbursementDate;
        this.principal = principal;
    }
}
