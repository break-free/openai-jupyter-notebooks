
package org.apache.fineract.organisation.office.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.organisation.monetary.domain.MonetaryCurrency;
@Entity
@Table(name = "m_organisation_currency")
public class OrganisationCurrency extends AbstractPersistableCustom {
    @Column(name = "code", nullable = false, length = 3)
    private String code;
    @Column(name = "decimal_places", nullable = false)
    private Integer decimalPlaces;
    @Column(name = "currency_multiplesof")
    private Integer inMultiplesOf;
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Column(name = "internationalized_name_code", nullable = false, length = 50)
    private String nameCode;
    @Column(name = "display_symbol", nullable = true, length = 10)
    private String displaySymbol;
    protected OrganisationCurrency() {
        this.code = null;
        this.name = null;
        this.decimalPlaces = null;
        this.inMultiplesOf = null;
        this.nameCode = null;
        this.displaySymbol = null;
    }
    public OrganisationCurrency(final String code, final String name, final int decimalPlaces, final Integer inMultiplesOf,
            final String nameCode, final String displaySymbol) {
        this.code = code;
        this.name = name;
        this.decimalPlaces = decimalPlaces;
        this.inMultiplesOf = inMultiplesOf;
        this.nameCode = nameCode;
        this.displaySymbol = displaySymbol;
    }
    public final String getCode() {
        return code;
    }
    public final MonetaryCurrency toMonetaryCurrency() {
        return new MonetaryCurrency(this.code, this.decimalPlaces, this.inMultiplesOf);
    }
}
