
package org.apache.fineract.portfolio.savings.data;
import java.math.BigDecimal;
import java.util.List;
import org.apache.fineract.portfolio.accountdetails.data.SavingsSummaryCustom;
public class GSIMContainer {
    private final BigDecimal gsimId;
    private final BigDecimal groupId;
    private final String accountNumber;
    private final List<SavingsSummaryCustom> childGSIMAccounts;
    private final BigDecimal parentBalance;
    private final String savingsStatus;
    public GSIMContainer(final BigDecimal gsimId, final BigDecimal groupId, final String accountNumber,
            final List<SavingsSummaryCustom> childGSIMAccounts, final BigDecimal parentBalance, final String savingsStatus) {
        this.gsimId = gsimId;
        this.groupId = groupId;
        this.accountNumber = accountNumber;
        this.childGSIMAccounts = childGSIMAccounts;
        this.parentBalance = parentBalance;
        this.savingsStatus = savingsStatus;
    }
    public BigDecimal getGsimId() {
        return gsimId;
    }
    public BigDecimal getGroupId() {
        return groupId;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public List<SavingsSummaryCustom> getChildGSIMAccounts() {
        return childGSIMAccounts;
    }
    public BigDecimal getparentBalance() {
        return parentBalance;
    }
    public String getSavingsStatus() {
        return savingsStatus;
    }
}
