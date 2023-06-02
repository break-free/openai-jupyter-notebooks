
package org.apache.fineract.infrastructure.accountnumberformat.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.apache.fineract.infrastructure.accountnumberformat.domain.AccountNumberFormatEnumerations.AccountNumberPrefixType;
import org.apache.fineract.infrastructure.accountnumberformat.service.AccountNumberFormatConstants;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
@Entity
@Table(name = AccountNumberFormatConstants.ACCOUNT_NUMBER_FORMAT_TABLE_NAME, uniqueConstraints = { @UniqueConstraint(columnNames = {
        AccountNumberFormatConstants.ACCOUNT_TYPE_ENUM_COLUMN_NAME }, name = AccountNumberFormatConstants.ACCOUNT_TYPE_UNIQUE_CONSTRAINT_NAME) })
public class AccountNumberFormat extends AbstractPersistableCustom {
    @Column(name = AccountNumberFormatConstants.ACCOUNT_TYPE_ENUM_COLUMN_NAME, nullable = false)
    private Integer accountTypeEnum;
    @Column(name = AccountNumberFormatConstants.PREFIX_TYPE_ENUM_COLUMN_NAME, nullable = true)
    private Integer prefixEnum;
    @Column(name = AccountNumberFormatConstants.PREFIX_CHARACTER_COLUMN_NAME, nullable = true)
    private String prefixCharacter;
    protected AccountNumberFormat() {
    }
    public AccountNumberFormat(EntityAccountType entityAccountType, AccountNumberPrefixType prefixType, String prefixCharacter) {
        this.accountTypeEnum = entityAccountType.getValue();
        if (prefixType != null) {
            this.prefixEnum = prefixType.getValue();
        }
        this.prefixCharacter = prefixCharacter;
    }
    public Integer getAccountTypeEnum() {
        return this.accountTypeEnum;
    }
    public String getPrefixCharacter() {
        return this.prefixCharacter;
    }
    public EntityAccountType getAccountType() {
        return EntityAccountType.fromInt(this.accountTypeEnum);
    }
    private void setAccountTypeEnum(Integer accountTypeEnum) {
        this.accountTypeEnum = accountTypeEnum;
    }
    public void setAccountType(EntityAccountType entityAccountType) {
        setAccountTypeEnum(entityAccountType.getValue());
    }
    public Integer getPrefixEnum() {
        return this.prefixEnum;
    }
    private void setPrefixEnum(Integer prefixEnum) {
        this.prefixEnum = prefixEnum;
    }
    public void setPrefix(AccountNumberPrefixType accountNumberPrefixType) {
        setPrefixEnum(accountNumberPrefixType.getValue());
    }
    public void setPrefixCharacter(String prefixCharacter) {
        this.prefixCharacter = prefixCharacter;
    }
}
