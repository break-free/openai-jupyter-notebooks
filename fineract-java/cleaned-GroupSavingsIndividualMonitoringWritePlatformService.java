
package org.apache.fineract.portfolio.savings.service;
import java.math.BigDecimal;
import org.apache.fineract.portfolio.group.domain.Group;
import org.apache.fineract.portfolio.savings.domain.GroupSavingsIndividualMonitoring;
public interface GroupSavingsIndividualMonitoringWritePlatformService {
    void setIsAcceptingChild(GroupSavingsIndividualMonitoring gsimAccount);
    void resetIsAcceptingChild(GroupSavingsIndividualMonitoring gsimAccount);
    void incrementChildAccountCount(GroupSavingsIndividualMonitoring gsimAccount);
    GroupSavingsIndividualMonitoring addGSIMAccountInfo(String accountNumber, Group group, BigDecimal parentDeposit,
            Long childAccountsCount, Boolean isAcceptingChild, Integer loanStatus, BigDecimal applicationId);
}
