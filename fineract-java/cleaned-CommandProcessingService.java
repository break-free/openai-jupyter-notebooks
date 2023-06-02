
package org.apache.fineract.commands.service;
import org.apache.fineract.commands.domain.CommandSource;
import org.apache.fineract.commands.domain.CommandWrapper;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.useradministration.domain.AppUser;
public interface CommandProcessingService {
    CommandProcessingResult processAndLogCommand(CommandWrapper wrapper, JsonCommand command, boolean isApprovedByChecker);
    CommandProcessingResult logCommand(CommandSource commandSourceResult);
    boolean validateCommand(CommandWrapper commandWrapper, AppUser user);
}
