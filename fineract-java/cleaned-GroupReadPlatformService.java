
package org.apache.fineract.portfolio.group.service;
import java.util.Collection;
import org.apache.fineract.infrastructure.core.data.PaginationParameters;
import org.apache.fineract.infrastructure.core.service.Page;
import org.apache.fineract.infrastructure.core.service.SearchParameters;
import org.apache.fineract.portfolio.group.data.GroupGeneralData;
public interface GroupReadPlatformService {
    GroupGeneralData retrieveTemplate(Long officeId, boolean isCenterGroup, boolean staffInSelectedOfficeOnly);
    Page<GroupGeneralData> retrievePagedAll(SearchParameters searchParameters, PaginationParameters parameters);
    Collection<GroupGeneralData> retrieveAll(SearchParameters searchParameters, PaginationParameters parameters);
    GroupGeneralData retrieveOne(Long groupId);
    Collection<GroupGeneralData> retrieveGroupsForLookup(Long officeId);
    GroupGeneralData retrieveGroupWithClosureReasons();
}
