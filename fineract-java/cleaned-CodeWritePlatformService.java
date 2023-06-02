
package org.apache.fineract.infrastructure.codes.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface CodeWritePlatformService {
    CommandProcessingResult createCode(JsonCommand command);
    CommandProcessingResult updateCode(Long codeId, JsonCommand command);
    CommandProcessingResult deleteCode(Long codeId);
}
