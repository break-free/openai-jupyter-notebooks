
package org.apache.fineract.infrastructure.creditbureau.domain;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
@Entity
@Table(name = "m_organisation_creditbureau")
public class OrganisationCreditBureau extends AbstractPersistableCustom {
    private String alias;
    @OneToOne
    @JoinColumn(name = "creditbureau_id", nullable = false)
    private CreditBureau creditbureau;
    @Column(name = "is_active")
    private boolean isActive;
    @OneToMany(mappedBy = "organisation_creditbureau", cascade = CascadeType.ALL)
    private List<CreditBureauLoanProductMapping> creditBureauLoanProductMapping = new ArrayList<>();
    public OrganisationCreditBureau(String alias, CreditBureau creditbureau, boolean isActive,
            List<CreditBureauLoanProductMapping> creditBureauLoanProductMapping) {
        this.alias = alias;
        this.creditbureau = creditbureau;
        this.isActive = isActive;
        this.creditBureauLoanProductMapping = creditBureauLoanProductMapping;
    }
    public OrganisationCreditBureau() {
    }
    public static OrganisationCreditBureau fromJson(final JsonCommand command, CreditBureau creditbureau) {
        final String alias = command.stringValueOfParameterNamed("alias");
        final boolean isActive = command.booleanPrimitiveValueOfParameterNamed("isActive");
        return new OrganisationCreditBureau(alias, creditbureau, isActive, null);
    }
    public String getAlias() {
        return this.alias;
    }
    public void setAlias(String alias) {
        this.alias = alias;
    }
    public CreditBureau getCreditBureau() {
        return this.creditbureau;
    }
    public void setCreditBureau(CreditBureau creditbureau) {
        this.creditbureau = creditbureau;
    }
    public boolean isActive() {
        return this.isActive;
    }
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    public List<CreditBureauLoanProductMapping> getCreditBureauLoanProductMapping() {
        return this.creditBureauLoanProductMapping;
    }
    public void setCreditBureauLoanProductMapping(List<CreditBureauLoanProductMapping> creditBureauLoanProductMapping) {
        this.creditBureauLoanProductMapping = creditBureauLoanProductMapping;
    }
}
