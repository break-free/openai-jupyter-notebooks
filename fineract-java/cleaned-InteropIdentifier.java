
package org.apache.fineract.interoperation.domain;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.portfolio.savings.domain.SavingsAccount;
@Entity
@Table(name = "interop_identifier", uniqueConstraints = {
        @UniqueConstraint(name = "uk_hathor_identifier_account", columnNames = { "account_id", "type" }),
        @UniqueConstraint(name = "uk_hathor_identifier_value", columnNames = { "type", "a_value", "sub_value_or_type" }) })
public class InteropIdentifier extends AbstractPersistableCustom {
    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private SavingsAccount account;
    @Column(name = "type", nullable = false, length = 32)
    @Enumerated(EnumType.STRING)
    private InteropIdentifierType type;
    @Column(name = "a_value", nullable = false, length = 128)
    private String value;
    @Column(name = "sub_value_or_type", length = 128)
    private String subType;
    @Column(name = "created_by", nullable = false, length = 32)
    private String createdBy;
    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;
    @Column(name = "modified_by", length = 32)
    private String modifiedBy;
    @Column(name = "modified_on")
    private LocalDateTime modifiedOn;
    protected InteropIdentifier() {}
    public InteropIdentifier(@NotNull SavingsAccount account, @NotNull InteropIdentifierType type, @NotNull String value, String subType,
            @NotNull String createdBy, @NotNull LocalDateTime createdOn) {
        this.account = account;
        this.type = type;
        this.value = value;
        this.subType = subType;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
    }
    public InteropIdentifier(@NotNull SavingsAccount account, @NotNull InteropIdentifierType type, @NotNull String createdBy,
            @NotNull LocalDateTime createdOn) {
        this(account, type, null, null, createdBy, createdOn);
    }
    public SavingsAccount getAccount() {
        return account;
    }
    public InteropIdentifierType getType() {
        return type;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getSubType() {
        return subType;
    }
    public void setSubType(String subType) {
        this.subType = subType;
    }
    public String getCreatedBy() {
        return createdBy;
    }
    public LocalDateTime getCreatedOn() {
        return createdOn;
    }
    public String geModifiedBy() {
        return modifiedBy;
    }
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
    public LocalDateTime getModifiedOn() {
        return modifiedOn;
    }
    public void setModifiedOn(LocalDateTime modifiedOn) {
        this.modifiedOn = modifiedOn;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof InteropIdentifier)) {
            return false;
        }
        InteropIdentifier that = (InteropIdentifier) o;
        if (!account.equals(that.account)) {
            return false;
        }
        if (type != that.type) {
            return false;
        }
        if (!value.equals(that.value)) {
            return false;
        }
        return Objects.equals(subType, that.subType);
    }
    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + value.hashCode();
        result = 31 * result + (subType != null ? subType.hashCode() : 0);
        return result;
    }
}
