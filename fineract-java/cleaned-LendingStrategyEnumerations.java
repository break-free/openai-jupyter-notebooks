
package org.apache.fineract.portfolio.loanproduct.service;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.portfolio.loanproduct.domain.LendingStrategy;
public final class LendingStrategyEnumerations {
    private LendingStrategyEnumerations() {
    }
    public static EnumOptionData lendingStrategy(final Integer id) {
        return lendingStrategy(LendingStrategy.fromInt(id));
    }
    public static EnumOptionData lendingStrategy(final LendingStrategy type) {
        EnumOptionData optionData = null;
        switch (type) {
            case INDIVIDUAL_LOAN:
                optionData = new EnumOptionData(type.getId().longValue(), type.getCode(), "Individual loan");
            break;
            case GROUP_LOAN:
                optionData = new EnumOptionData(type.getId().longValue(), type.getCode(), "Group loan");
            break;
            case JOINT_LIABILITY_LOAN:
                optionData = new EnumOptionData(type.getId().longValue(), type.getCode(), "Joint liability loan");
            break;
            case LINKED_LOAN:
                optionData = new EnumOptionData(type.getId().longValue(), type.getCode(), "Linked loan");
            break;
            default:
                optionData = new EnumOptionData(LendingStrategy.INVALID.getId().longValue(), LendingStrategy.INVALID.getCode(), "Invalid");
            break;
        }
        return optionData;
    }
}
