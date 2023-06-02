
package org.apache.fineract.portfolio.account.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.apache.fineract.portfolio.savings.domain.SavingsAccount;
@Entity
@Table(name = "m_portfolio_account_associations")
public class AccountAssociations extends AbstractPersistableCustom {
    @ManyToOne
    @JoinColumn(name = "loan_account_id", nullable = true)
    private Loan loanAccount;
    @ManyToOne
    @JoinColumn(name = "savings_account_id", nullable = true)
    private SavingsAccount savingsAccount;
    @ManyToOne
    @JoinColumn(name = "linked_loan_account_id", nullable = true)
    private Loan linkedLoanAccount;
    @ManyToOne
    @JoinColumn(name = "linked_savings_account_id", nullable = true)
    private SavingsAccount linkedSavingsAccount;
    @Column(name = "association_type_enum", nullable = false)
    private Integer associationType;
    @Column(name = "is_active", nullable = false)
    private boolean active = true;
    protected AccountAssociations() {}
    private AccountAssociations(final Loan loanAccount, final SavingsAccount savingsAccount, final Loan linkedLoanAccount,
            final SavingsAccount linkedSavingsAccount, final Integer associationType, boolean active) {
        this.loanAccount = loanAccount;
        this.savingsAccount = savingsAccount;
        this.linkedLoanAccount = linkedLoanAccount;
        this.linkedSavingsAccount = linkedSavingsAccount;
        this.associationType = associationType;
        this.active = active;
    }
    public static AccountAssociations associateSavingsAccount(final Loan loan, final SavingsAccount savingsAccount,
            final Integer associationType, boolean isActive) {
        return new AccountAssociations(loan, null, null, savingsAccount, associationType, isActive);
    }
    public static AccountAssociations associateSavingsAccount(final SavingsAccount savingsAccount,
            final SavingsAccount linkedSavingsAccount, final Integer associationType, boolean isActive) {
        return new AccountAssociations(null, savingsAccount, null, linkedSavingsAccount, associationType, isActive);
    }
    public SavingsAccount linkedSavingsAccount() {
        return this.linkedSavingsAccount;
    }
    public void updateLinkedSavingsAccount(final SavingsAccount savingsAccount) {
        this.linkedSavingsAccount = savingsAccount;
    }
}
