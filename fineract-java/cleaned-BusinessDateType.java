
package org.apache.fineract.infrastructure.businessdate.domain;
import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public enum BusinessDateType {
    BUSINESS_DATE(1, "Business Date"), COB_DATE(2, "Close of Business Date");
    private final Integer id;
    private final String description;
    public String getName() {
        return name();
    }
}
