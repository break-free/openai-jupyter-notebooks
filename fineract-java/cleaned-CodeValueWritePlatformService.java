
package org.apache.fineract.infrastructure.codes.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface CodeValueWritePlatformService {
    CommandProcessingResult createCodeValue(JsonCommand command);
    CommandProcessingResult updateCodeValue(Long codeValueId, JsonCommand command);
    CommandProcessingResult deleteCodeValue(Long codeId, Long codeValueId);
}
