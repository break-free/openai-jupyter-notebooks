
package org.apache.fineract.portfolio.loanaccount.data;
import java.math.BigDecimal;
public final class GlimRepaymentTemplate {
    private final BigDecimal glimId;
    private final BigDecimal groupId;
    private final BigDecimal clientId;
    private final String clientName;
    private final BigDecimal childLoanId;
    private final String parentAccountNo;
    private final BigDecimal parentPrincipalAmount;
    private final String childLoanAccountNo;
    private final BigDecimal childPrincipalAmount;
    private GlimRepaymentTemplate(final BigDecimal glimId, final BigDecimal groupId, final BigDecimal clientId, final String clientName,
            final BigDecimal childLoanId, final String parentAccountNo, final BigDecimal parentPrincipalAmount,
            final String childLoanAccountNo, final BigDecimal childPrincipalAmount) {
        this.glimId = glimId;
        this.groupId = groupId;
        this.clientId = clientId;
        this.clientName = clientName;
        this.childLoanId = childLoanId;
        this.parentAccountNo = parentAccountNo;
        this.parentPrincipalAmount = parentPrincipalAmount;
        this.childLoanAccountNo = childLoanAccountNo;
        this.childPrincipalAmount = childPrincipalAmount;
    }
    public static GlimRepaymentTemplate getInstance(final BigDecimal glimId, final BigDecimal groupId, final BigDecimal clientId,
            final String clientName, final BigDecimal childLoanId, final String parentAccountNo, final BigDecimal parentPrincipalAmount,
            final String childLoanAccountNo, final BigDecimal childPrincipalAmount) {
        return new GlimRepaymentTemplate(glimId, groupId, clientId, clientName, childLoanId, parentAccountNo, parentPrincipalAmount,
                childLoanAccountNo, childPrincipalAmount);
    }
    public BigDecimal getGlimId() {
        return glimId;
    }
    public BigDecimal getGroupId() {
        return groupId;
    }
    public BigDecimal getClientId() {
        return clientId;
    }
    public String getClientName() {
        return clientName;
    }
    public BigDecimal getChildLoanId() {
        return childLoanId;
    }
    public String getParentAccountNo() {
        return parentAccountNo;
    }
    public BigDecimal getParentPrincipalAmount() {
        return parentPrincipalAmount;
    }
    public String getChildLoanAccountNo() {
        return childLoanAccountNo;
    }
    public BigDecimal getChildPrincipalAmount() {
        return childPrincipalAmount;
    }
}
