
package org.apache.fineract.portfolio.savings.domain;
import java.math.BigDecimal;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.portfolio.group.domain.Group;
@Entity
@Table(name = "gsim_accounts", uniqueConstraints = { @UniqueConstraint(columnNames = { "account_number" }, name = "gsim_id") })
public final class GroupSavingsIndividualMonitoring extends AbstractPersistableCustom {
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;
    @Column(name = "account_number", nullable = false)
    private String accountNumber;
    @Column(name = "parent_deposit")
    private BigDecimal parentDeposit;
    @Column(name = "child_accounts_count")
    private Long childAccountsCount;
    @Column(name = "accepting_child")
    private Boolean isAcceptingChild;
    @OneToMany
    private Set<SavingsAccount> childSaving;
    @Column(name = "savings_status_id", nullable = false)
    private Integer savingsStatus;
    @Column(name = "application_id", nullable = true)
    private BigDecimal applicationId;
    private GroupSavingsIndividualMonitoring() {}
    private GroupSavingsIndividualMonitoring(String accountNumber, Group group, BigDecimal parentDeposit, Long childAccountsCount,
            Boolean isAcceptingChild, Integer savingsStatus, BigDecimal applicationId) {
        this.accountNumber = accountNumber;
        this.group = group;
        this.parentDeposit = parentDeposit;
        this.childAccountsCount = childAccountsCount;
        this.isAcceptingChild = isAcceptingChild;
        this.savingsStatus = savingsStatus;
        this.applicationId = applicationId;
    }
    public static GroupSavingsIndividualMonitoring getInstance(String accountNumber, Group group, BigDecimal parentDeposit,
            Long childAccountsCount, Boolean isAcceptingChild, Integer savingsStatus, BigDecimal applicationId) {
        return new GroupSavingsIndividualMonitoring(accountNumber, group, parentDeposit, childAccountsCount, isAcceptingChild,
                savingsStatus, applicationId);
    }
    public Group getGroup() {
        return group;
    }
    public void setGroup(Group group) {
        this.group = group;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public BigDecimal getParentDeposit() {
        return parentDeposit;
    }
    public void setParentDeposit(BigDecimal parentDeposit) {
        this.parentDeposit = parentDeposit;
    }
    public Long getChildAccountsCount() {
        return childAccountsCount;
    }
    public void setChildAccountsCount(Long childAccountsCount) {
        this.childAccountsCount = childAccountsCount;
    }
    public Boolean getIsAcceptingChild() {
        return isAcceptingChild;
    }
    public void setIsAcceptingChild(Boolean isAcceptingChild) {
        this.isAcceptingChild = isAcceptingChild;
    }
    public Set<SavingsAccount> getChildSaving() {
        return childSaving;
    }
    public void setChildSaving(Set<SavingsAccount> childSaving) {
        this.childSaving = childSaving;
    }
    public Integer getSavingsStatus() {
        return savingsStatus;
    }
    public void setSavingsStatus(Integer savingsStatus) {
        this.savingsStatus = savingsStatus;
    }
}
