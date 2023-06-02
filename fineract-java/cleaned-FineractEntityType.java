
package org.apache.fineract.infrastructure.entityaccess.domain;
public final class FineractEntityType {
    private String type;
    private String description;
    private String tableName;
    public static final FineractEntityType OFFICE = new FineractEntityType("office", "Offices", "m_office");
    public static final FineractEntityType LOAN_PRODUCT = new FineractEntityType("loan_product", "Loan Products", "m_product_loan");
    public static final FineractEntityType SAVINGS_PRODUCT = new FineractEntityType("savings_product", "Savings Products",
            "m_savings_product");
    public static final FineractEntityType CHARGE = new FineractEntityType("charge", "Fees/Charges", "m_charge");
    public static final FineractEntityType SHARE_PRODUCT = new FineractEntityType("shares_product", "Shares Products", "m_share_product");
    private FineractEntityType(String type, String description, String tableName) {
        this.type = type;
        this.description = description;
        this.tableName = tableName;
    }
    public String getType() {
        return this.type;
    }
    public String getDescription() {
        return this.description;
    }
    public String getTable() {
        return this.tableName;
    }
    public static FineractEntityType get(String type) {
        FineractEntityType retType = null;
        if (type.equals(OFFICE.type)) {
            retType = OFFICE;
        } else if (type.equals(LOAN_PRODUCT.type)) {
            retType = LOAN_PRODUCT;
        } else if (type.equals(SAVINGS_PRODUCT.type)) {
            retType = SAVINGS_PRODUCT;
        } else if (type.equals(CHARGE.type)) {
            retType = CHARGE;
        } else if (type.equals(SHARE_PRODUCT.type)) {
            retType = SHARE_PRODUCT;
        }
        return retType;
    }
}
