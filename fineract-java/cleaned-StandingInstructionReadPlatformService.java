
package org.apache.fineract.portfolio.account.service;
import java.util.Collection;
import org.apache.fineract.infrastructure.core.service.Page;
import org.apache.fineract.portfolio.account.data.StandingInstructionDTO;
import org.apache.fineract.portfolio.account.data.StandingInstructionData;
import org.apache.fineract.portfolio.account.data.StandingInstructionDuesData;
public interface StandingInstructionReadPlatformService {
    StandingInstructionData retrieveTemplate(Long fromOfficeId, Long fromClientId, Long fromAccountId, Integer fromAccountType,
            Long toOfficeId, Long toClientId, Long toAccountId, Integer toAccountType, Integer transferType);
    Page<StandingInstructionData> retrieveAll(StandingInstructionDTO standingInstructionDTO);
    StandingInstructionData retrieveOne(Long instructionId);
    Collection<StandingInstructionData> retrieveAll(Integer status);
    StandingInstructionDuesData retriveLoanDuesData(Long loanId);
}
