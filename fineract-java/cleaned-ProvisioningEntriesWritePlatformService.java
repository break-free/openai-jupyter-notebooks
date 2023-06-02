
package org.apache.fineract.accounting.provisioning.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface ProvisioningEntriesWritePlatformService {
    CommandProcessingResult createProvisioningEntries(JsonCommand command);
    CommandProcessingResult reCreateProvisioningEntries(Long provisioningEntryId, JsonCommand command);
    CommandProcessingResult createProvisioningJournalEntries(Long provisioningEntryId, JsonCommand command);
    void generateLoanLossProvisioningAmount();
}
