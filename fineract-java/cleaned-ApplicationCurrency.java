
package org.apache.fineract.organisation.monetary.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.organisation.monetary.data.CurrencyData;
import org.apache.fineract.organisation.office.domain.OrganisationCurrency;
@Entity
@Table(name = "m_currency")
public class ApplicationCurrency extends AbstractPersistableCustom {
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
    protected ApplicationCurrency() {
        this.code = null;
        this.name = null;
        this.decimalPlaces = null;
        this.inMultiplesOf = null;
        this.nameCode = null;
        this.displaySymbol = null;
    }
    public static ApplicationCurrency from(final ApplicationCurrency currency, final int decimalPlaces, final Integer inMultiplesOf) {
        return new ApplicationCurrency(currency.code, currency.name, decimalPlaces, inMultiplesOf, currency.nameCode,
                currency.displaySymbol);
    }
    private ApplicationCurrency(final String code, final String name, final int decimalPlaces, final Integer inMultiplesOf,
            final String nameCode, final String displaySymbol) {
        this.code = code;
        this.name = name;
        this.decimalPlaces = decimalPlaces;
        this.inMultiplesOf = inMultiplesOf;
        this.nameCode = nameCode;
        this.displaySymbol = displaySymbol;
    }
    public String getCode() {
        return this.code;
    }
    public String getName() {
        return this.name;
    }
    public Integer getDecimalPlaces() {
        return this.decimalPlaces;
    }
    public Integer getCurrencyInMultiplesOf() {
        return this.inMultiplesOf;
    }
    public String getNameCode() {
        return this.nameCode;
    }
    public String getDisplaySymbol() {
        return this.displaySymbol;
    }
    public CurrencyData toData() {
        return new CurrencyData(this.code, this.name, this.decimalPlaces, this.inMultiplesOf, this.displaySymbol, this.nameCode);
    }
    public CurrencyData toData(final int digitsAfterDecimalSupported, final Integer inMultiplesOf) {
        return new CurrencyData(this.code, this.name, digitsAfterDecimalSupported, inMultiplesOf, this.displaySymbol, this.nameCode);
    }
    public OrganisationCurrency toOrganisationCurrency() {
        return new OrganisationCurrency(this.code, this.name, this.decimalPlaces, this.inMultiplesOf, this.nameCode, this.displaySymbol);
    }
    public void setCode(final String code) {
        this.code = code;
    }
}
