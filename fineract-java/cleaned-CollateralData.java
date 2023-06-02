
package org.apache.fineract.portfolio.collateral.data;
import java.math.BigDecimal;
import java.util.Collection;
import org.apache.fineract.infrastructure.codes.data.CodeValueData;
import org.apache.fineract.organisation.monetary.data.CurrencyData;
public final class CollateralData {
    private final Long id;
    private final CodeValueData type;
    private final BigDecimal value;
    private final String description;
    @SuppressWarnings("unused")
    private final Collection<CodeValueData> allowedCollateralTypes;
    private final CurrencyData currency;
    public static CollateralData instance(final Long id, final CodeValueData type, final BigDecimal value, final String description,
            final CurrencyData currencyData) {
        return new CollateralData(id, type, value, description, currencyData);
    }
    public static CollateralData template(final Collection<CodeValueData> codeValues) {
        return new CollateralData(null, null, null, null, null, codeValues);
    }
    private CollateralData(final Long id, final CodeValueData type, final BigDecimal value, final String description,
            final CurrencyData currencyData) {
        this.id = id;
        this.type = type;
        this.value = value;
        this.description = description;
        this.currency = currencyData;
        this.allowedCollateralTypes = null;
    }
    private CollateralData(final Long id, final CodeValueData type, final BigDecimal value, final String description,
            final CurrencyData currencyData, final Collection<CodeValueData> allowedCollateralTypes) {
        this.id = id;
        this.type = type;
        this.value = value;
        this.description = description;
        this.currency = currencyData;
        this.allowedCollateralTypes = allowedCollateralTypes;
    }
    public CollateralData template(final CollateralData collateralData, final Collection<CodeValueData> codeValues) {
        return new CollateralData(collateralData.id, collateralData.type, collateralData.value, collateralData.description,
                collateralData.currency, codeValues);
    }
}
