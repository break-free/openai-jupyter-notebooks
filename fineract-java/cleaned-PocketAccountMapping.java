
package org.apache.fineract.portfolio.self.pockets.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
@SuppressWarnings("serial")
@Entity
@Table(name = "m_pocket_accounts_mapping")
public class PocketAccountMapping extends AbstractPersistableCustom {
    @Column(name = "pocket_id", length = 20, nullable = false)
    private Long pocketId;
    @Column(name = "account_id", length = 20, nullable = false)
    private Long accountId;
    @Column(name = "account_type", nullable = false)
    private Integer accountType;
    @Column(name = "account_number", nullable = false)
    private String accountNumber;
    protected PocketAccountMapping() {}
    private PocketAccountMapping(final Long pocketId, final Long accountId, final Integer accountType, final String accountNumber) {
        this.pocketId = pocketId;
        this.accountId = accountId;
        this.accountType = accountType;
        this.accountNumber = accountNumber;
    }
    public static PocketAccountMapping instance(final Long pocketId, final Long accountId, final Integer accountType,
            final String accountNumber) {
        return new PocketAccountMapping(pocketId, accountId, accountType, accountNumber);
    }
    public Long getPocketId() {
        return pocketId;
    }
    public void setPocketId(Long pocketId) {
        this.pocketId = pocketId;
    }
    public Long getAccountId() {
        return accountId;
    }
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
    public Integer getAccountType() {
        return accountType;
    }
    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }
}
