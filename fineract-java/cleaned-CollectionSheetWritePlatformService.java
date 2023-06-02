
package org.apache.fineract.portfolio.collectionsheet.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface CollectionSheetWritePlatformService {
    CommandProcessingResult updateCollectionSheet(JsonCommand command);
    CommandProcessingResult saveIndividualCollectionSheet(JsonCommand command);
}
