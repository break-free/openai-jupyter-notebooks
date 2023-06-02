
package org.apache.fineract.organisation.staff.domain;
public enum StaffOrganisationalRoleType {
    INVALID(0, "staffOrganisationalRoleType.invalid"), 
    PROGRAM_DIRECTOR(100, "staffOrganisationalRoleType.programDirector"), 
    BRANCH_MANAGER(200, "staffOrganisationalRoleType.branchManager"), 
    FIELD_OFFICER_COORDINATOR(300, "staffOrganisationalRoleType.coordinator"), 
    FIELD_OFFICER(400, "staffOrganisationalRoleType.fieldAgent");
    private final Integer value;
    private final String code;
    StaffOrganisationalRoleType(final Integer value, final String code) {
        this.value = value;
        this.code = code;
    }
    public Integer getValue() {
        return this.value;
    }
    public String getCode() {
        return this.code;
    }
    public static StaffOrganisationalRoleType fromInt(final Integer chargeCalculation) {
        StaffOrganisationalRoleType chargeCalculationType = StaffOrganisationalRoleType.INVALID;
        switch (chargeCalculation) {
            case 100:
                chargeCalculationType = PROGRAM_DIRECTOR;
            break;
            case 200:
                chargeCalculationType = BRANCH_MANAGER;
            break;
            case 300:
                chargeCalculationType = FIELD_OFFICER_COORDINATOR;
            break;
            case 400:
                chargeCalculationType = FIELD_OFFICER;
            break;
        }
        return chargeCalculationType;
    }
}
