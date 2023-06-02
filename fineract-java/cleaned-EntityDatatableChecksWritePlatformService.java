
package org.apache.fineract.infrastructure.dataqueries.service;
import com.google.gson.JsonArray;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface EntityDatatableChecksWritePlatformService {
    CommandProcessingResult createCheck(JsonCommand command);
    CommandProcessingResult deleteCheck(Long entityDatatableCheckId);
    void runTheCheck(Long entityId, String entityName, Long statusCode, String foreignKeyColumn);
    void runTheCheckForProduct(Long entityId, String entityName, Long statusCode, String foreignKeyColumn, long productLoanId);
    boolean saveDatatables(Long status, String entity, Long entityId, Long productId, JsonArray data);
}
