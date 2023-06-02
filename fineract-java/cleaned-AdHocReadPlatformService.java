
package org.apache.fineract.adhocquery.service;
import java.util.Collection;
import org.apache.fineract.adhocquery.data.AdHocData;
public interface AdHocReadPlatformService {
    Collection<AdHocData> retrieveAllAdHocQuery();
    Collection<AdHocData> retrieveAllActiveAdHocQuery();
    AdHocData retrieveOne(Long adHocId);
    AdHocData retrieveNewAdHocDetails();
}
