
package org.apache.fineract.organisation.staff.domain;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
public final class StaffEnumerations {
    private StaffEnumerations() {
    }
    public static EnumOptionData organisationalRole(final Integer id) {
        return organisationalRole(StaffOrganisationalRoleType.fromInt(id));
    }
    public static EnumOptionData organisationalRole(final StaffOrganisationalRoleType type) {
        EnumOptionData optionData = new EnumOptionData(StaffOrganisationalRoleType.INVALID.getValue().longValue(),
                StaffOrganisationalRoleType.INVALID.getCode(), "Invalid");
        switch (type) {
            case PROGRAM_DIRECTOR:
                optionData = new EnumOptionData(type.getValue().longValue(), type.getCode(), "Program Director");
            break;
            case BRANCH_MANAGER:
                optionData = new EnumOptionData(type.getValue().longValue(), type.getCode(), "Branch Manager");
            break;
            case FIELD_OFFICER_COORDINATOR:
                optionData = new EnumOptionData(type.getValue().longValue(), type.getCode(), "Field Officer Coordinator");
            break;
            case FIELD_OFFICER:
                optionData = new EnumOptionData(type.getValue().longValue(), type.getCode(), "Field Officer");
            break;
            case INVALID:
            break;
        }
        return optionData;
    }
}
