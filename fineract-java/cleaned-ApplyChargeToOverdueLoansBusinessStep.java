
package org.apache.fineract.cob.loan;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.infrastructure.configuration.domain.ConfigurationDomainService;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.apache.fineract.portfolio.loanaccount.loanschedule.data.OverdueLoanScheduleData;
import org.apache.fineract.portfolio.loanaccount.service.LoanReadPlatformService;
import org.apache.fineract.portfolio.loanaccount.service.LoanWritePlatformService;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class ApplyChargeToOverdueLoansBusinessStep implements LoanCOBBusinessStep {
    private final ConfigurationDomainService configurationDomainService;
    private final LoanReadPlatformService loanReadPlatformService;
    private final LoanWritePlatformService loanWritePlatformService;
    @Override
    public Loan execute(Loan input) {
        final Long penaltyWaitPeriodValue = configurationDomainService.retrievePenaltyWaitPeriod();
        final Boolean backdatePenalties = configurationDomainService.isBackdatePenaltiesEnabled();
        final Collection<OverdueLoanScheduleData> overdueLoanScheduledInstallments = loanReadPlatformService
                .retrieveAllLoansWithOverdueInstallments(penaltyWaitPeriodValue, backdatePenalties);
        Map<Long, List<OverdueLoanScheduleData>> groupedOverdueData = overdueLoanScheduledInstallments.stream()
                .collect(Collectors.groupingBy(OverdueLoanScheduleData::getLoanId));
        for (Long loanId : groupedOverdueData.keySet()) {
            loanWritePlatformService.applyOverdueChargesForLoan(input.getId(), groupedOverdueData.get(loanId));
        }
        return input;
    }
    @Override
    public String getEnumStyledName() {
        return "APPLY_CHARGE_TO_OVERDUE_LOANS";
    }
    @Override
    public String getHumanReadableName() {
        return "Apply charge to overdue loans";
    }
}
