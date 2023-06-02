
package org.apache.fineract.organisation.monetary.data;
import java.io.Serializable;
import java.util.Objects;
public class CurrencyData implements Serializable {
    private final String code;
    private final String name;
    private final int decimalPlaces;
    private final Integer inMultiplesOf;
    private final String displaySymbol;
    @SuppressWarnings("unused")
    private final String nameCode;
    @SuppressWarnings("unused")
    private final String displayLabel;
    public static CurrencyData blank() {
        return new CurrencyData("", "", 0, 0, "", "");
    }
    public CurrencyData(String code) {
        this.code = code;
        this.name = null;
        this.decimalPlaces = 0;
        this.inMultiplesOf = null;
        this.displaySymbol = null;
        this.nameCode = null;
        this.displayLabel = null;
    }
    public CurrencyData(final String code, final String name, final int decimalPlaces, final Integer inMultiplesOf,
            final String displaySymbol, final String nameCode) {
        this.code = code;
        this.name = name;
        this.decimalPlaces = decimalPlaces;
        this.inMultiplesOf = inMultiplesOf;
        this.displaySymbol = displaySymbol;
        this.nameCode = nameCode;
        this.displayLabel = generateDisplayLabel();
    }
    public CurrencyData(final String code, final int decimalPlaces, final Integer inMultiplesOf) {
        this.code = code;
        this.name = null;
        this.decimalPlaces = decimalPlaces;
        this.inMultiplesOf = inMultiplesOf;
        this.displaySymbol = null;
        this.nameCode = null;
        this.displayLabel = null;
    }
    public String code() {
        return this.code;
    }
    public int decimalPlaces() {
        return this.decimalPlaces;
    }
    public Integer currencyInMultiplesOf() {
        return this.inMultiplesOf;
    }
    private String generateDisplayLabel() {
        final StringBuilder builder = new StringBuilder(this.name).append(' ');
        if (this.displaySymbol != null && !"".equalsIgnoreCase(this.displaySymbol.trim())) {
            builder.append('(').append(this.displaySymbol).append(')');
        } else {
            builder.append('[').append(this.code).append(']');
        }
        return builder.toString();
    }
    public String getName() {
        return name;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CurrencyData)) {
            return false;
        }
        CurrencyData that = (CurrencyData) o;
        return (decimalPlaces == that.decimalPlaces) && Objects.equals(code, that.code) && Objects.equals(name, that.name)
                && Objects.equals(inMultiplesOf, that.inMultiplesOf) && Objects.equals(displaySymbol, that.displaySymbol)
                && Objects.equals(nameCode, that.nameCode) && Objects.equals(displayLabel, that.displayLabel);
    }
    @Override
    public int hashCode() {
        return Objects.hash(code, name, decimalPlaces, inMultiplesOf, displaySymbol, nameCode, displayLabel);
    }
    public String getCode() {
        return this.code;
    }
    public int getDecimalPlaces() {
        return this.decimalPlaces;
    }
    public Integer getInMultiplesOf() {
        return this.inMultiplesOf;
    }
}
