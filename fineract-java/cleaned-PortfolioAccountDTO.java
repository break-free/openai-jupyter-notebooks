
package org.apache.fineract.portfolio.account.data;
public class PortfolioAccountDTO {
    private final Integer accountTypeId;
    private final Long clientId;
    private Long groupId;
    private final String currencyCode;
    private final long[] accountStatus;
    private final Integer depositType;
    private final boolean excludeOverDraftAccounts;
    public PortfolioAccountDTO(final Integer accountTypeId, final Long clientId, final String currencyCode, final long[] accountStatus,
            final Integer depositType, final boolean excludeOverDraftAccounts) {
        this.accountTypeId = accountTypeId;
        this.clientId = clientId;
        this.currencyCode = currencyCode;
        this.accountStatus = accountStatus;
        this.depositType = depositType;
        this.excludeOverDraftAccounts = excludeOverDraftAccounts;
    }
    public PortfolioAccountDTO(final Integer accountTypeId, final Long clientId, final long[] accountStatus) {
        this.accountTypeId = accountTypeId;
        this.clientId = clientId;
        this.currencyCode = null;
        this.accountStatus = accountStatus;
        this.depositType = null;
        this.excludeOverDraftAccounts = false;
    }
    public PortfolioAccountDTO(final Integer accountTypeId, final Long clientId, final String currencyCode, final long[] accountStatus,
            final Integer depositType) {
        this.accountTypeId = accountTypeId;
        this.clientId = clientId;
        this.currencyCode = currencyCode;
        this.accountStatus = accountStatus;
        this.depositType = depositType;
        this.excludeOverDraftAccounts = false;
    }
    public Integer getAccountTypeId() {
        return this.accountTypeId;
    }
    public Long getClientId() {
        return this.clientId;
    }
    public String getCurrencyCode() {
        return this.currencyCode;
    }
    public long[] getAccountStatus() {
        return this.accountStatus;
    }
    public long getFirstAccountStatus() {
        return this.accountStatus[0];
    }
    public Integer getDepositType() {
        return this.depositType;
    }
    public boolean isExcludeOverDraftAccounts() {
        return this.excludeOverDraftAccounts;
    }
    public Long getGroupId() {
        return this.groupId;
    }
    public void setGroupId(final Long groupId) {
        this.groupId = groupId;
    }
}
