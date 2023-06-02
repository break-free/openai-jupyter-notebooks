
package org.apache.fineract.infrastructure.core.domain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.fineract.infrastructure.businessdate.domain.BusinessDateType;
@Getter
@AllArgsConstructor
public enum ActionContext {
    DEFAULT(0, "Default context", BusinessDateType.BUSINESS_DATE), COB(1, "Close of Business context", BusinessDateType.COB_DATE);
    private final int order;
    private final String description;
    private final BusinessDateType businessDateType;
}
