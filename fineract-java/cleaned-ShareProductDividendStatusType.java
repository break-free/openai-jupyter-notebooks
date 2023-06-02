
package org.apache.fineract.portfolio.shareproducts.domain;
public enum ShareProductDividendStatusType {
    INVALID(0, "shareAccountDividendStatusType.invalid"), INITIATED(100, "shareAccountDividendStatusType.initiated"), APPROVED(300,
            "shareAccountDividendStatusType.approved");
    private final Integer value;
    private final String code;
    ShareProductDividendStatusType(final Integer value, final String code) {
        this.value = value;
        this.code = code;
    }
    public static ShareProductDividendStatusType fromInt(final Integer type) {
        ShareProductDividendStatusType enumeration = ShareProductDividendStatusType.INVALID;
        switch (type) {
            case 100:
                enumeration = ShareProductDividendStatusType.INITIATED;
            break;
            case 300:
                enumeration = ShareProductDividendStatusType.APPROVED;
            break;
        }
        return enumeration;
    }
    public Integer getValue() {
        return this.value;
    }
    public String getCode() {
        return this.code;
    }
    public boolean isApproved() {
        return this.value.equals(ShareProductDividendStatusType.APPROVED.getValue());
    }
}
