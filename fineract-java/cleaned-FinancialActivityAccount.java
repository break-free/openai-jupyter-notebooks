
package org.apache.fineract.accounting.financialactivityaccount.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.fineract.accounting.glaccount.domain.GLAccount;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
@Entity
@Table(name = "acc_gl_financial_activity_account")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class FinancialActivityAccount extends AbstractPersistableCustom {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gl_account_id")
    private GLAccount glAccount;
    @Column(name = "financial_activity_type", nullable = false)
    private Integer financialActivityType;
    public static FinancialActivityAccount createNew(final GLAccount glAccount, final Integer financialAccountType) {
        return new FinancialActivityAccount(glAccount, financialAccountType);
    }
    public void updateGlAccount(final GLAccount glAccount) {
        this.glAccount = glAccount;
    }
    public void updateFinancialActivityType(final Integer financialActivityType) {
        this.financialActivityType = financialActivityType;
    }
}
