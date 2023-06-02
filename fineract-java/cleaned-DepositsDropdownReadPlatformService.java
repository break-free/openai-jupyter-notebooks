
package org.apache.fineract.portfolio.savings.service;
import java.util.Collection;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
public interface DepositsDropdownReadPlatformService {
    Collection<EnumOptionData> retrievePreClosurePenalInterestOnTypeOptions();
    Collection<EnumOptionData> maturityInstructionOptions();
}
