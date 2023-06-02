
package org.apache.fineract.portfolio.account.service;
import org.apache.fineract.infrastructure.core.service.Page;
import org.apache.fineract.portfolio.account.data.StandingInstructionDTO;
import org.apache.fineract.portfolio.account.data.StandingInstructionHistoryData;
public interface StandingInstructionHistoryReadPlatformService {
    Page<StandingInstructionHistoryData> retrieveAll(StandingInstructionDTO standingInstructionDTO);
}
