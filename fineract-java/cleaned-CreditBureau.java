
package org.apache.fineract.infrastructure.creditbureau.domain;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
@Entity
@Table(name = "m_creditbureau")
public class CreditBureau extends AbstractPersistableCustom {
    private String name;
    private String product;
    private String country;
    @Column(name = "implementation_key")
    private String implementationKey;
    public CreditBureau(String name, String product, String country, String implementationKey,
            List<CreditBureauLoanProductMapping> CreditBureauLoanProductMapping) {
        this.name = name;
        this.product = product;
        this.country = country;
        this.implementationKey = implementationKey;
    }
    public CreditBureau() {
    }
    public static CreditBureau fromJson(final JsonCommand command) {
        final String tname = command.stringValueOfParameterNamed("name");
        final String tproduct = command.stringValueOfParameterNamed("product");
        final String tcountry = command.stringValueOfParameterNamed("country");
        final String timplementationKey = command.stringValueOfParameterNamed("implementationKey");
        return new CreditBureau(tname, tproduct, tcountry, timplementationKey, null);
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getProduct() {
        return this.product;
    }
    public void setProduct(String product) {
        this.product = product;
    }
    public String getCountry() {
        return this.country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getImplementationKey() {
        return this.implementationKey;
    }
    public void setImplementationKey(String implementationKey) {
        this.implementationKey = implementationKey;
    }
}
