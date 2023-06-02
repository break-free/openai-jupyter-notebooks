
package org.apache.fineract.portfolio.savings.service;
import java.util.Arrays;
import java.util.Collection;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.portfolio.savings.DepositAccountOnClosureType;
import org.apache.fineract.portfolio.savings.PreClosurePenalInterestOnType;
import org.springframework.stereotype.Service;
@Service
public class DepositsDropdownReadPlatformServiceImpl implements DepositsDropdownReadPlatformService {
    @Override
    public Collection<EnumOptionData> retrievePreClosurePenalInterestOnTypeOptions() {
        return SavingsEnumerations.preClosurePenaltyInterestOnType(PreClosurePenalInterestOnType.values());
    }
    @Override
    public Collection<EnumOptionData> maturityInstructionOptions() {
        return Arrays.asList(SavingsEnumerations.depositAccountOnClosureType(DepositAccountOnClosureType.WITHDRAW_DEPOSIT),
                SavingsEnumerations.depositAccountOnClosureType(DepositAccountOnClosureType.TRANSFER_TO_SAVINGS),
                SavingsEnumerations.depositAccountOnClosureType(DepositAccountOnClosureType.REINVEST_PRINCIPAL_AND_INTEREST),
                SavingsEnumerations.depositAccountOnClosureType(DepositAccountOnClosureType.REINVEST_PRINCIPAL_ONLY));
    }
}
