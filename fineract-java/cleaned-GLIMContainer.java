
package org.apache.fineract.portfolio.loanaccount.data;
import java.math.BigDecimal;
import java.util.List;
import org.apache.fineract.portfolio.accountdetails.data.LoanAccountSummaryData;
public class GLIMContainer {
    private final BigDecimal glimId;
    private final BigDecimal groupId;
    private final String accountNumber;
    private final List<LoanAccountSummaryData> childGLIMAccounts;
    private final BigDecimal parentPrincipalAmount;
    private final String loanStatus;
    public GLIMContainer(final BigDecimal glimId, final BigDecimal groupId, final String accountNumber,
            final List<LoanAccountSummaryData> childGLIMAccounts, final BigDecimal parentPrincipalAmount, final String loanStatus) {
        this.glimId = glimId;
        this.groupId = groupId;
        this.accountNumber = accountNumber;
        this.childGLIMAccounts = childGLIMAccounts;
        this.parentPrincipalAmount = parentPrincipalAmount;
        this.loanStatus = loanStatus;
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
    public List<LoanAccountSummaryData> getChildGLIMAccounts() {
        return childGLIMAccounts;
    }
    public BigDecimal getParentPrincipalAmount() {
        return parentPrincipalAmount;
    }
    public String getLoanStatus() {
        return loanStatus;
    }
}
