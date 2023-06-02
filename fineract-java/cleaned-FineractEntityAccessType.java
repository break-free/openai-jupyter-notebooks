
package org.apache.fineract.infrastructure.entityaccess.domain;
public final class FineractEntityAccessType {
    private String str;
    public static final FineractEntityAccessType OFFICE_ACCESS_TO_LOAN_PRODUCTS = new FineractEntityAccessType(
            "office_access_to_loan_products");
    public static final FineractEntityAccessType OFFICE_ACCESS_TO_SAVINGS_PRODUCTS = new FineractEntityAccessType(
            "office_access_to_savings_products");
    public static final FineractEntityAccessType OFFICE_ACCESS_TO_CHARGES = new FineractEntityAccessType("office_access_to_fees/charges");
    private FineractEntityAccessType(String str) {
        this.str = str;
    }
    public String toStr() {
        return this.str;
    }
    public static FineractEntityAccessType get(String type) {
        FineractEntityAccessType retType = null;
        if (type.equals(OFFICE_ACCESS_TO_LOAN_PRODUCTS.str)) {
            retType = OFFICE_ACCESS_TO_LOAN_PRODUCTS;
        } else if (type.equals(OFFICE_ACCESS_TO_SAVINGS_PRODUCTS.str)) {
            retType = OFFICE_ACCESS_TO_SAVINGS_PRODUCTS;
        } else if (type.equals(OFFICE_ACCESS_TO_CHARGES.str)) {
            retType = OFFICE_ACCESS_TO_CHARGES;
        }
        return retType;
    }
}
