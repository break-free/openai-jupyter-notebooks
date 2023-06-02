
package org.apache.fineract.portfolio.group.service;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.portfolio.group.domain.GroupTypes;
public final class GroupTypeEnumerations {
    private GroupTypeEnumerations() {
    }
    public static EnumOptionData groupType(final Integer id) {
        return groupType(GroupTypes.fromInt(id));
    }
    public static EnumOptionData groupType(final GroupTypes type) {
        EnumOptionData optionData = null;
        switch (type) {
            case CENTER:
                optionData = new EnumOptionData(type.getId().longValue(), type.getCode(), "Individual loan");
            break;
            case GROUP:
                optionData = new EnumOptionData(type.getId().longValue(), type.getCode(), "Group loan");
            break;
            default:
                optionData = new EnumOptionData(GroupTypes.INVALID.getId().longValue(), GroupTypes.INVALID.getCode(), "Invalid");
            break;
        }
        return optionData;
    }
}
