
package org.apache.fineract.portfolio.repaymentwithpostdatedchecks.domain;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepaymentScheduleInstallment;
@Entity
@Table(name = "m_repayment_with_post_dated_checks")
public class PostDatedChecks extends AbstractPersistableCustom {
    @ManyToOne(optional = false)
    @JoinColumn(name = "loan_id", referencedColumnName = "id", nullable = false)
    private Loan loan;
    @OneToOne(optional = false)
    @JoinColumn(name = "repayment_id", referencedColumnName = "id", nullable = false)
    private LoanRepaymentScheduleInstallment loanRepaymentScheduleInstallment;
    @Column(name = "account_no", nullable = false, length = 10)
    private Long accountNo;
    @Column(name = "bank_name", nullable = false, length = 100)
    private String bankName;
    @Column(name = "amount", scale = 6, precision = 19)
    private BigDecimal amount;
    @Column(name = "repayment_date", nullable = false)
    private LocalDate repaymentDate;
    @Column(name = "status", columnDefinition = "0")
    private Integer status;
    @Column(name = "check_no", nullable = false, unique = true)
    private Long checkNo;
    public PostDatedChecks() {}
    private PostDatedChecks(final Long accountNo, final String bankName, final BigDecimal amount,
            final LoanRepaymentScheduleInstallment loanRepaymentScheduleInstallment, final LocalDate date, final Loan loan,
            final Long checkNo) {
        this.bankName = bankName;
        this.accountNo = accountNo;
        this.amount = amount;
        this.loanRepaymentScheduleInstallment = loanRepaymentScheduleInstallment;
        this.repaymentDate = date;
        this.loan = loan;
        this.checkNo = checkNo;
    }
    public static PostDatedChecks instanceOf(final Long accountNo, final String bankName, final BigDecimal amount,
            final LoanRepaymentScheduleInstallment loanRepaymentScheduleInstallment, final Loan loan, final Long checkNo) {
        return new PostDatedChecks(accountNo, bankName, amount, loanRepaymentScheduleInstallment,
                loanRepaymentScheduleInstallment.getDueDate(), loan, checkNo);
    }
    public void setLoan(Loan loan) {
        this.loan = loan;
    }
    public void setLoanRepaymentScheduleInstallment(final LoanRepaymentScheduleInstallment loanRepaymentScheduleInstallment) {
        this.loanRepaymentScheduleInstallment = loanRepaymentScheduleInstallment;
    }
    public Map<String, Object> updatePostDatedChecks(JsonCommand command) {
        final Map<String, Object> changes = new HashMap<>();
        if (command.isChangeInBigDecimalParameterNamed("amount", this.amount)) {
            final BigDecimal newAmount = command.bigDecimalValueOfParameterNamed("amount");
            this.amount = newAmount;
            changes.put("amount", newAmount);
        }
        if (command.isChangeInStringParameterNamed("name", this.bankName)) {
            final String newName = command.stringValueOfParameterNamed("name");
            this.bankName = newName;
            changes.put("bankName", newName);
        }
        if (command.isChangeInLongParameterNamed("accountNo", this.accountNo)) {
            final Long newAccountNo = command.longValueOfParameterNamed("accountNo");
            this.accountNo = newAccountNo;
            changes.put("accountNo", newAccountNo);
        }
        if (command.isChangeInLongParameterNamed("checkNo", this.accountNo)) {
            final Long newCheckNo = command.longValueOfParameterNamed("checkNo");
            this.checkNo = newCheckNo;
            changes.put("checkNo", newCheckNo);
        }
        return changes;
    }
    public Loan getLoan() {
        return this.loan;
    }
    public LoanRepaymentScheduleInstallment getLoanRepaymentScheduleInstallment() {
        return this.loanRepaymentScheduleInstallment;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getBankName() {
        return this.bankName;
    }
    public Long getCheckNo() {
        return this.checkNo;
    }
    public Long getAccountNo() {
        return this.accountNo;
    }
    public BigDecimal getAmount() {
        return this.amount;
    }
    public Integer getStatus() {
        return this.status;
    }
}
