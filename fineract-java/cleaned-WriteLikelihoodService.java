
package org.apache.fineract.infrastructure.survey.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface WriteLikelihoodService {
    CommandProcessingResult update(Long likelihoodId, JsonCommand command);
}
