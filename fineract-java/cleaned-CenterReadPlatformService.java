
package org.apache.fineract.portfolio.group.service;
import java.time.LocalDate;
import java.util.Collection;
import org.apache.fineract.infrastructure.core.data.PaginationParameters;
import org.apache.fineract.infrastructure.core.service.Page;
import org.apache.fineract.infrastructure.core.service.SearchParameters;
import org.apache.fineract.portfolio.group.data.CenterData;
import org.apache.fineract.portfolio.group.data.GroupGeneralData;
import org.apache.fineract.portfolio.group.data.StaffCenterData;
public interface CenterReadPlatformService {
    CenterData retrieveTemplate(Long officeId, boolean staffInSelectedOfficeOnly);
    CenterData retrieveOne(Long centerId);
    Collection<CenterData> retrieveAllForDropdown(Long officeId);
    Page<CenterData> retrievePagedAll(SearchParameters searchParameters, PaginationParameters parameters);
    Collection<CenterData> retrieveAll(SearchParameters searchParameters, PaginationParameters parameters);
    GroupGeneralData retrieveCenterGroupTemplate(Long centerId);
    Collection<GroupGeneralData> retrieveAssociatedGroups(Long centerId);
    CenterData retrieveCenterWithClosureReasons();
    Collection<StaffCenterData> retriveAllCentersByMeetingDate(Long officeId, LocalDate meetingDate, Long staffId);
}
