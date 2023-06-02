
package org.apache.fineract.portfolio.search.service;
import java.util.Collection;
import org.apache.fineract.portfolio.search.data.AdHocQuerySearchConditions;
import org.apache.fineract.portfolio.search.data.AdHocSearchQueryData;
import org.apache.fineract.portfolio.search.data.SearchConditions;
import org.apache.fineract.portfolio.search.data.SearchData;
public interface SearchReadPlatformService {
    Collection<SearchData> retriveMatchingData(SearchConditions searchConditions);
    AdHocSearchQueryData retrieveAdHocQueryTemplate();
    Collection<AdHocSearchQueryData> retrieveAdHocQueryMatchingData(AdHocQuerySearchConditions searchConditions);
}
