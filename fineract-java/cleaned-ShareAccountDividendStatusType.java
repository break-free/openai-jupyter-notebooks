
package org.apache.fineract.portfolio.shareaccounts.domain;
public enum ShareAccountDividendStatusType {
    INVALID(0, "shareAccountDividendStatusType.invalid"), INITIATED(100, "shareAccountDividendStatusType.initiated"), POSTED(300,
            "shareAccountDividendStatusType.posted");
    private final Integer value;
    private final String code;
    ShareAccountDividendStatusType(final Integer value, final String code) {
        this.value = value;
        this.code = code;
    }
    public static ShareAccountDividendStatusType fromInt(final Integer type) {
        ShareAccountDividendStatusType enumeration = ShareAccountDividendStatusType.INVALID;
        switch (type) {
            case 100:
                enumeration = ShareAccountDividendStatusType.INITIATED;
            break;
            case 300:
                enumeration = ShareAccountDividendStatusType.POSTED;
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
}
