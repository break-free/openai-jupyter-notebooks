
package org.apache.fineract.portfolio.group.api;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.apache.fineract.portfolio.group.data.CenterData;
public final class GroupingTypesApiConstants {
    private GroupingTypesApiConstants() {
    }
    public static final String CENTER_RESOURCE_NAME = "center";
    public static final String GROUP_RESOURCE_NAME = "group";
    public static final String COMMUNAL_BANK_RESOURCE_NAME = "communalbank";
    public static final String GROUP_ROLE_RESOURCE_NAME = "grouprole";
    public static final String GROUP_ROLE_NAME = "GROUPROLE";
    public static final String GROUP_CLOSURE_REASON = "GroupClosureReason";
    public static final String CENTER_CLOSURE_REASON = "CenterClosureReason";
    public static final String roleParamName = "role";
    public static final String groupIdParamName = "groupId";
    public static final String clientIdParamName = "clientId";
    public static final String groupRolesParamName = "groupRoles";
    public static final String accountNoParamName = "accountNo";
    public static final String localeParamName = "locale";
    public static final String dateFormatParamName = "dateFormat";
    public static final String idParamName = "id";
    public static final String nameParamName = "name";
    public static final String externalIdParamName = "externalId";
    public static final String officeIdParamName = "officeId";
    public static final String staffIdParamName = "staffId";
    public static final String activeParamName = "active";
    public static final String activationDateParamName = "activationDate";
    public static final String groupMembersParamName = "groupMembers";
    public static final String submittedOnDateParamName = "submittedOnDate";
    public static final String inheritStaffForClientAccounts = "inheritStaffForClientAccounts";
    public static final String centerIdParamName = "centerId";
    public static final String clientMembersParamName = "clientMembers";
    public static final String statusParamName = "status";
    public static final String hierarchyParamName = "hierarchy";
    public static final String officeNameParamName = "officeName";
    public static final String staffNameParamName = "staffName";
    public static final String officeOptionsParamName = "officeOptions";
    public static final String staffOptionsParamName = "staffOptions";
    public static final String clientOptionsParamName = "clientOptions";
    public static final String collectionMeetingCalendar = "collectionMeetingCalendar";
    public static final String timeLine = "timeline";
    public static final String closureReasons = "closureReasons";
    public static final String totalCollected = "totalCollected";
    public static final String totalOverdue = "totalOverdue";
    public static final String totaldue = "totaldue";
    public static final String installmentDue = "installmentDue";
    public static final String closureDateParamName = "closureDate";
    public static final String closureReasonIdParamName = "closureReasonId";
    public static final String meetingFallCenters = "meetingFallCenters";
    public static final String datatables = "datatables";
    static final Set<String> CENTER_RESPONSE_DATA_PARAMETERS = new HashSet<>(
            Arrays.asList(idParamName, nameParamName, externalIdParamName, officeIdParamName, officeNameParamName, staffIdParamName,
                    staffNameParamName, hierarchyParamName, officeOptionsParamName, staffOptionsParamName, statusParamName, activeParamName,
                    activationDateParamName, timeLine, groupMembersParamName, collectionMeetingCalendar, closureReasons, datatables));
    static final Set<String> CENTER_GROUP_RESPONSE_DATA_PARAMETERS = new HashSet<>(Arrays.asList(idParamName, nameParamName,
            externalIdParamName, officeIdParamName, officeNameParamName, staffIdParamName, staffNameParamName, hierarchyParamName,
            officeOptionsParamName, staffOptionsParamName, clientOptionsParamName, datatables));
    static final Set<String> GROUP_RESPONSE_DATA_PARAMETERS = new HashSet<>(
            Arrays.asList(idParamName, nameParamName, externalIdParamName, officeIdParamName, officeNameParamName, "parentId", "parentName",
                    staffIdParamName, staffNameParamName, hierarchyParamName, officeOptionsParamName, statusParamName, activeParamName,
                    activationDateParamName, staffOptionsParamName, clientOptionsParamName, timeLine, datatables));
    static final Set<String> COLLECTIONSHEET_DATA_PARAMETERS = new HashSet<>(Arrays.asList("dueDate", "loanProducts", "groups"));
    static final Set<String> STAFF_CENTER_RESPONSE_DATA_PARAMETERS = new HashSet<>(Arrays.asList(staffIdParamName, staffNameParamName,
            meetingFallCenters, totalCollected, totalOverdue, totaldue, installmentDue));
}
