
package org.apache.fineract.portfolio.account.service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.infrastructure.core.domain.JdbcSupport;
import org.apache.fineract.portfolio.account.data.AccountAssociationsData;
import org.apache.fineract.portfolio.account.data.PortfolioAccountData;
import org.apache.fineract.portfolio.account.domain.AccountAssociationType;
import org.apache.fineract.portfolio.loanaccount.domain.LoanStatus;
import org.apache.fineract.portfolio.savings.domain.SavingsAccountStatusType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class AccountAssociationsReadPlatformServiceImpl implements AccountAssociationsReadPlatformService {
    private static final Logger LOG = LoggerFactory.getLogger(AccountAssociationsReadPlatformServiceImpl.class);
    private final JdbcTemplate jdbcTemplate;
    @Override
    public PortfolioAccountData retriveLoanLinkedAssociation(final Long loanId) {
        PortfolioAccountData linkedAccount = null;
        final AccountAssociationsMapper mapper = new AccountAssociationsMapper();
        final String sql = "select " + mapper.schema() + " where aa.loan_account_id = ? and aa.association_type_enum = ?";
        try {
            final AccountAssociationsData accountAssociationsData = this.jdbcTemplate.queryForObject(sql, mapper, loanId, 
                    AccountAssociationType.LINKED_ACCOUNT_ASSOCIATION.getValue());
            if (accountAssociationsData != null) {
                linkedAccount = accountAssociationsData.linkedAccount();
            }
        } catch (final EmptyResultDataAccessException e) {
            LOG.debug("Linking account is not configured");
        }
        return linkedAccount;
    }
    @Override
    public Collection<AccountAssociationsData> retriveLoanAssociations(final Long loanId, final Integer associationType) {
        final AccountAssociationsMapper mapper = new AccountAssociationsMapper();
        final String sql = "select " + mapper.schema() + " where aa.loan_account_id = ? and aa.association_type_enum = ?";
        try {
            return this.jdbcTemplate.query(sql, mapper, new Object[] { loanId, associationType }); 
        } catch (final EmptyResultDataAccessException e) {
            return null;
        }
    }
    @Override
    public PortfolioAccountData retriveSavingsLinkedAssociation(final Long savingsId) {
        PortfolioAccountData linkedAccount = null;
        final AccountAssociationsMapper mapper = new AccountAssociationsMapper();
        final String sql = "select " + mapper.schema() + " where aa.savings_account_id = ? and aa.association_type_enum = ?";
        try {
            final AccountAssociationsData accountAssociationsData = this.jdbcTemplate.queryForObject(sql, mapper, 
                    new Object[] { savingsId, AccountAssociationType.LINKED_ACCOUNT_ASSOCIATION.getValue() });
            if (accountAssociationsData != null) {
                linkedAccount = accountAssociationsData.linkedAccount();
            }
        } catch (final EmptyResultDataAccessException e) {
            LOG.debug("Linking account is not configured");
        }
        return linkedAccount;
    }
    @Override
    public boolean isLinkedWithAnyActiveAccount(final Long savingsId) {
        boolean hasActiveAccount = false;
        final String sql1 = "select aa.is_active as active,aa.association_type_enum as type, loanAccount.loan_status_id as loanStatus,"
                + "savingAccount.status_enum as savingsStatus from m_portfolio_account_associations aa "
                + "left join m_loan loanAccount on loanAccount.id = aa.loan_account_id "
                + "left join m_savings_account savingAccount on savingAccount.id = aa.savings_account_id "
                + "where aa.linked_savings_account_id = ?";
        final List<Map<String, Object>> statusList = this.jdbcTemplate.queryForList(sql1, savingsId);
        for (final Map<String, Object> statusMap : statusList) {
            AccountAssociationType associationType = AccountAssociationType.fromInt((Integer) statusMap.get("type"));
            if (!associationType.isLinkedAccountAssociation() && (Boolean) statusMap.get("active")) {
                hasActiveAccount = true;
                break;
            }
            if (statusMap.get("loanStatus") != null) {
                final LoanStatus loanStatus = LoanStatus.fromInt((Integer) statusMap.get("loanStatus"));
                if (loanStatus.isActiveOrAwaitingApprovalOrDisbursal() || loanStatus.isUnderTransfer()) {
                    hasActiveAccount = true;
                    break;
                }
            }
            if (statusMap.get("savingsStatus") != null) {
                final SavingsAccountStatusType saveStatus = SavingsAccountStatusType.fromInt((Integer) statusMap.get("savingsStatus"));
                if (saveStatus.isActiveOrAwaitingApprovalOrDisbursal() || saveStatus.isUnderTransfer()) {
                    hasActiveAccount = true;
                    break;
                }
            }
        }
        return hasActiveAccount;
    }
    private static final class AccountAssociationsMapper implements RowMapper<AccountAssociationsData> {
        private final String schemaSql;
        AccountAssociationsMapper() {
            final StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("aa.id as id,");
            sqlBuilder.append("loanAccount.id as loanAccountId, loanAccount.account_no as loanAccountNo,");
            sqlBuilder.append("linkSavingsAccount.id as linkSavingsAccountId, linkSavingsAccount.account_no as linkSavingsAccountNo ");
            sqlBuilder.append("from m_portfolio_account_associations aa ");
            sqlBuilder.append("left join m_loan loanAccount on loanAccount.id = aa.loan_account_id ");
            sqlBuilder.append("left join m_savings_account linkSavingsAccount on linkSavingsAccount.id = aa.linked_savings_account_id ");
            this.schemaSql = sqlBuilder.toString();
        }
        public String schema() {
            return this.schemaSql;
        }
        @Override
        public AccountAssociationsData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException {
            final Long id = rs.getLong("id");
            final Long loanAccountId = JdbcSupport.getLong(rs, "loanAccountId");
            final String loanAccountNo = rs.getString("loanAccountNo");
            final PortfolioAccountData account = PortfolioAccountData.lookup(loanAccountId, loanAccountNo);
            final Long linkSavingsAccountId = JdbcSupport.getLong(rs, "linkSavingsAccountId");
            final String linkSavingsAccountNo = rs.getString("linkSavingsAccountNo");
            final PortfolioAccountData linkedAccount = PortfolioAccountData.lookup(linkSavingsAccountId, linkSavingsAccountNo);
            return new AccountAssociationsData(id, account, linkedAccount);
        }
    }
    @Override
    public PortfolioAccountData retriveSavingsAccount(final Long savingsId) {
        String accountNo = jdbcTemplate.queryForObject("select account_no from m_savings_account where id = ?", String.class, savingsId);
        return PortfolioAccountData.lookup(savingsId, accountNo);
    }
}
