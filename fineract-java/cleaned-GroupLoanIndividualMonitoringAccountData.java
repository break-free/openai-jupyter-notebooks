
package org.apache.fineract.portfolio.loanaccount.data;
import java.math.BigDecimal;
public final class GroupLoanIndividualMonitoringAccountData {
    private final BigDecimal glimId;
    private final BigDecimal groupId;
    private final String accountNumber;
    private final String childAccountNumber;
    private final BigDecimal childPrincipalAmount;
    private final BigDecimal parentPrincipalAmount;
    private final Long childAccountsCount;
    private final String loanStatus;
    private GroupLoanIndividualMonitoringAccountData(final BigDecimal glimId, final BigDecimal groupId, final String accountNumber,
            final String childAccountNumber, final BigDecimal childPrincipalAmount, final BigDecimal parentPrincipalAmount,
            final Long childAccountsCount, final String loanStatus) {
        this.glimId = glimId;
        this.groupId = groupId;
        this.accountNumber = accountNumber;
        this.childAccountNumber = childAccountNumber;
        this.childPrincipalAmount = childPrincipalAmount;
        this.parentPrincipalAmount = parentPrincipalAmount;
        this.childAccountsCount = childAccountsCount;
        this.loanStatus = loanStatus;
    }
    public static GroupLoanIndividualMonitoringAccountData getInstance(final BigDecimal glimId, final BigDecimal groupId,
            final String accountNumber, final String childAccountNumber, final BigDecimal childPrincipalAmount,
            final BigDecimal parentPrincipalAmount, final Long childAccountsCount, final String loanStatus) {
        return new GroupLoanIndividualMonitoringAccountData(glimId, groupId, accountNumber, childAccountNumber, childPrincipalAmount,
                parentPrincipalAmount, childAccountsCount, loanStatus);
    }
    public static GroupLoanIndividualMonitoringAccountData getInstance1(final BigDecimal glimId, final BigDecimal groupId,
            final String accountNumber, final BigDecimal parentPrincipalAmount, final String loanStatus) {
        return new GroupLoanIndividualMonitoringAccountData(glimId, groupId, accountNumber, null, null, parentPrincipalAmount, null,
                loanStatus);
    }
    public BigDecimal getGlimId() {
        return glimId;
    }
    public BigDecimal getGroupId() {
        return groupId;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public String getChildAccountNumber() {
        return childAccountNumber;
    }
    public BigDecimal getChildPrincipalAmount() {
        return childPrincipalAmount;
    }
    public BigDecimal getParentPrincipalAmount() {
        return parentPrincipalAmount;
    }
    public Long getChildAccountsCount() {
        return childAccountsCount;
    }
    public String getLoanStatus() {
        return loanStatus;
    }
}
