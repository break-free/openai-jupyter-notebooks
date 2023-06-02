
package org.apache.fineract.portfolio.collateral.api;
import java.util.HashSet;
import java.util.Set;
public final class CollateralApiConstants {
    private CollateralApiConstants() {
    }
    public static final String COLLATERAL_CODE_NAME = "LoanCollateral";
    public enum CollateralJSONinputParams {
        LOAN_ID("loanId"), COLLATERAL_ID("collateralId"), COLLATERAL_TYPE_ID("collateralTypeId"), VALUE("value"), DESCRIPTION(
                "description");
        private final String value;
        CollateralJSONinputParams(final String value) {
            this.value = value;
        }
        private static final Set<String> values = new HashSet<>();
        static {
            for (final CollateralJSONinputParams type : CollateralJSONinputParams.values()) {
                values.add(type.value);
            }
        }
        public static Set<String> getAllValues() {
            return values;
        }
        @Override
        public String toString() {
            return name().toString().replaceAll("_", " ");
        }
        public String getValue() {
            return this.value;
        }
    }
}
