
package org.apache.fineract.organisation.staff.service;
import java.util.Collection;
import org.apache.fineract.organisation.staff.data.StaffData;
public interface StaffReadPlatformService {
    StaffData retrieveStaff(Long staffId);
    Collection<StaffData> retrieveAllStaffForDropdown(Long officeId);
    Collection<StaffData> retrieveAllLoanOfficersInOfficeById(Long officeId);
    Collection<StaffData> retrieveAllStaffInOfficeAndItsParentOfficeHierarchy(Long officeId, boolean loanOfficersOnly);
    Collection<StaffData> retrieveAllStaff(Long officeId, boolean loanOfficersOnly, String status);
    Object[] hasAssociatedItems(Long staffId);
}
