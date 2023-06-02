
package org.apache.fineract.portfolio.loanaccount.domain;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface GLIMAccountInfoRepository
        extends JpaRepository<GroupLoanIndividualMonitoringAccount, Long>, JpaSpecificationExecutor<GroupLoanIndividualMonitoringAccount> {
    GroupLoanIndividualMonitoringAccount findOneByIsAcceptingChild(boolean acceptingChild);
    GroupLoanIndividualMonitoringAccount findOneByIsAcceptingChildAndApplicationId(boolean acceptingChild, BigDecimal applicationId);
    GroupLoanIndividualMonitoringAccount findOneByAccountNumber(String accountNumber);
}
