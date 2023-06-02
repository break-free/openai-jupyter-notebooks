
package org.apache.fineract.commands.service;
import org.apache.fineract.commands.domain.CommandWrapper;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface PortfolioCommandSourceWritePlatformService {
    CommandProcessingResult logCommandSource(CommandWrapper commandRequest);
    CommandProcessingResult approveEntry(Long id);
    Long rejectEntry(Long id);
    Long deleteEntry(Long makerCheckerId);
}
