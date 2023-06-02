
package org.apache.fineract.infrastructure.creditbureau.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface OrganisationCreditBureauWritePlatflormService {
    CommandProcessingResult addOrganisationCreditBureau(Long ocb_id, JsonCommand command);
    CommandProcessingResult updateCreditBureau(JsonCommand command);
}
