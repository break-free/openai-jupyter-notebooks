
package org.apache.fineract.adhocquery.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface AdHocWritePlatformService {
    CommandProcessingResult createAdHocQuery(JsonCommand command);
    CommandProcessingResult updateAdHocQuery(Long adHocId, JsonCommand command);
    CommandProcessingResult deleteAdHocQuery(Long adHocId);
    CommandProcessingResult disableAdHocQuery(Long adHocId);
    CommandProcessingResult enableAdHocQuery(Long adHocId);
}
