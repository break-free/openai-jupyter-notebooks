
package org.apache.fineract.portfolio.self.pockets.service;
import org.apache.fineract.portfolio.self.pockets.data.PocketAccountMappingData;
public interface PocketAccountMappingReadPlatformService {
    PocketAccountMappingData retrieveAll();
    boolean validatePocketAndAccountMapping(Long pocketId, Long accountId, Integer accountType);
}
