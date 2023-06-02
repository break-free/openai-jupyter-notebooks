
package org.apache.fineract.organisation.monetary.domain;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.apache.fineract.organisation.monetary.data.CurrencyData;
@Embeddable
public class MonetaryCurrency {
    @Column(name = "currency_code", length = 3, nullable = false)
    private String code;
    @Column(name = "currency_digits", nullable = false)
    private int digitsAfterDecimal;
    @Column(name = "currency_multiplesof")
    private Integer inMultiplesOf;
    protected MonetaryCurrency() {
        this.code = null;
        this.digitsAfterDecimal = 0;
        this.inMultiplesOf = 0;
    }
    public MonetaryCurrency(final String code, final int digitsAfterDecimal, final Integer inMultiplesOf) {
        this.code = code;
        this.digitsAfterDecimal = digitsAfterDecimal;
        this.inMultiplesOf = inMultiplesOf;
    }
    public MonetaryCurrency copy() {
        return new MonetaryCurrency(this.code, this.digitsAfterDecimal, this.inMultiplesOf);
    }
    public static MonetaryCurrency fromApplicationCurrency(ApplicationCurrency applicationCurrency) {
        return new MonetaryCurrency(applicationCurrency.getCode(), applicationCurrency.getDecimalPlaces(),
                applicationCurrency.getCurrencyInMultiplesOf());
    }
    public static MonetaryCurrency fromCurrencyData(final CurrencyData currencyData) {
        return new MonetaryCurrency(currencyData.getCode(), currencyData.getDecimalPlaces(), currencyData.getInMultiplesOf());
    }
    public String getCode() {
        return this.code;
    }
    public int getDigitsAfterDecimal() {
        return this.digitsAfterDecimal;
    }
    public Integer getCurrencyInMultiplesOf() {
        return this.inMultiplesOf;
    }
}
