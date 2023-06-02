
package org.apache.fineract.accounting.rule.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.apache.fineract.accounting.journalentry.domain.JournalEntryType;
import org.apache.fineract.infrastructure.codes.domain.CodeValue;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
@Entity
@Table(name = "acc_rule_tags", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "acc_rule_id", "tag_id", "acc_type_enum" }, name = "UNIQUE_ACCOUNT_RULE_TAGS") })
public class AccountingTagRule extends AbstractPersistableCustom {
    @ManyToOne
    @JoinColumn(name = "acc_rule_id", nullable = false)
    private AccountingRule accountingRule;
    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    private CodeValue tagId;
    @Column(name = "acc_type_enum", nullable = false)
    private Integer accountType;
    public static AccountingTagRule create(final CodeValue tagId, final Integer accountType) {
        return new AccountingTagRule(tagId, accountType);
    }
    public AccountingTagRule(final CodeValue tagId, final Integer accountType) {
        this.tagId = tagId;
        this.accountType = accountType;
    }
    public void updateAccountingTagRule(final AccountingRule accountingRule) {
        this.accountingRule = accountingRule;
    }
    public AccountingTagRule() {
    }
    public Integer getAccountType() {
        return this.accountType;
    }
    public boolean isDebitAccount() {
        return JournalEntryType.fromInt(this.accountType).isDebitType();
    }
    public boolean isCreditAccount() {
        return JournalEntryType.fromInt(this.accountType).isCreditType();
    }
    public Long getTagId() {
        return this.tagId.getId();
    }
}
