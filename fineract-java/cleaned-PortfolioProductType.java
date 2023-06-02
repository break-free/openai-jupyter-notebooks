
package org.apache.fineract.accounting.producttoaccountmapping.domain;
import java.util.HashMap;
import java.util.Map;
public enum PortfolioProductType {
    LOAN(1, "productType.loan"), SAVING(2, "productType.saving"), CLIENT(5, "productType.client"), PROVISIONING(3,
            "productType.provisioning"), SHARES(4, "productType.shares");
    private final Integer value;
    private final String code;
    PortfolioProductType(final Integer value, final String code) {
        this.value = value;
        this.code = code;
    }
    @Override
    public String toString() {
        return name().toString().replaceAll("_", " ");
    }
    public Integer getValue() {
        return this.value;
    }
    public String getCode() {
        return this.code;
    }
    private static final Map<Integer, PortfolioProductType> intToEnumMap = new HashMap<>();
    static {
        for (final PortfolioProductType type : PortfolioProductType.values()) {
            intToEnumMap.put(type.value, type);
        }
    }
    public static PortfolioProductType fromInt(final int i) {
        final PortfolioProductType type = intToEnumMap.get(Integer.valueOf(i));
        return type;
    }
    public boolean isSavingProduct() {
        return this.value.equals(PortfolioProductType.SAVING.getValue());
    }
    public boolean isLoanProduct() {
        return this.value.equals(PortfolioProductType.LOAN.getValue());
    }
    public boolean isClient() {
        return this.value.equals(PortfolioProductType.CLIENT.getValue());
    }
    public boolean isShareProduct() {
        return this.value.equals(PortfolioProductType.SHARES.getValue());
    }
}
