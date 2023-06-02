
package org.apache.fineract.portfolio.loanaccount.domain;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
@Entity
@Table(name = "m_loan_topup")
public class LoanTopupDetails extends AbstractPersistableCustom {
    @OneToOne
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;
    @Column(name = "closure_loan_id", nullable = false)
    private Long closureLoanId;
    @Column(name = "account_transfer_details_id", nullable = true)
    private Long accountTransferDetailsId;
    @Column(name = "topup_amount", nullable = true)
    private BigDecimal topupAmount;
    protected LoanTopupDetails() {}
    public LoanTopupDetails(final Loan loan, final Long loanIdToClose) {
        this.loan = loan;
        this.closureLoanId = loanIdToClose;
    }
    public Long getLoanIdToClose() {
        return this.closureLoanId;
    }
    public BigDecimal getTopupAmount() {
        return this.topupAmount;
    }
    public void setTopupAmount(BigDecimal topupAmount) {
        this.topupAmount = topupAmount;
    }
    public void setAccountTransferDetails(Long accountTransferDetailsId) {
        this.accountTransferDetailsId = accountTransferDetailsId;
    }
}
