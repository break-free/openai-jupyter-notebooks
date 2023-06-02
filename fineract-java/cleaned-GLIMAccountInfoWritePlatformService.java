
package org.apache.fineract.portfolio.loanaccount.service;
import java.math.BigDecimal;
import org.apache.fineract.portfolio.group.domain.Group;
import org.apache.fineract.portfolio.loanaccount.domain.GroupLoanIndividualMonitoringAccount;
public interface GLIMAccountInfoWritePlatformService {
    void setIsAcceptingChild(GroupLoanIndividualMonitoringAccount glimAccount);
    void resetIsAcceptingChild(GroupLoanIndividualMonitoringAccount glimAccount);
    void incrementChildAccountCount(GroupLoanIndividualMonitoringAccount glimAccount);
    void addGLIMAccountInfo(String accountNumber, Group group, BigDecimal principalAmount, Long childAccountsCount,
            Boolean isAcceptingChild, Integer loanStatus, BigDecimal applicationId);
}
