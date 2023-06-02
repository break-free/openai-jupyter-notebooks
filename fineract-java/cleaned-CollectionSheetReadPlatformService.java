
package org.apache.fineract.portfolio.collectionsheet.service;
import org.apache.fineract.infrastructure.core.api.JsonQuery;
import org.apache.fineract.portfolio.collectionsheet.data.IndividualCollectionSheetData;
import org.apache.fineract.portfolio.collectionsheet.data.JLGCollectionSheetData;
public interface CollectionSheetReadPlatformService {
    JLGCollectionSheetData generateGroupCollectionSheet(Long groupId, JsonQuery query);
    JLGCollectionSheetData generateCenterCollectionSheet(Long groupId, JsonQuery query);
    IndividualCollectionSheetData generateIndividualCollectionSheet(JsonQuery query);
}
