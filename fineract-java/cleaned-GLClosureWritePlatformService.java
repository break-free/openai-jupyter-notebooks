
package org.apache.fineract.accounting.closure.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface GLClosureWritePlatformService {
    CommandProcessingResult createGLClosure(JsonCommand command);
    CommandProcessingResult updateGLClosure(Long glClosureId, JsonCommand command);
    CommandProcessingResult deleteGLClosure(Long glClosureId);
}
