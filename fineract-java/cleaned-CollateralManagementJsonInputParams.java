
package org.apache.fineract.portfolio.collateralmanagement.api;
public enum CollateralManagementJsonInputParams {
    NAME("name"), QUALITY("quality"), BASE_PRICE("basePrice"), UNIT_TYPE("unitType"), PCT_TO_BASE("pctToBase"), CURRENCY(
            "currency"), COLLATERAL_PRODUCT_READ_PERMISSION("COLLATERAL_PRODUCT"), CLIENT_COLLATERAL_PRODUCT_READ_PERMISSION(
                    "CLIENT_COLLATERAL_PRODUCT"), QUANTITY("quantity"), TOTAL_COLLATERAL_VALUE("totalCollateralValue");
    private final String value;
    CollateralManagementJsonInputParams(final String value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return name().replaceAll("_", " ");
    }
    public String getValue() {
        return this.value;
    }
}
