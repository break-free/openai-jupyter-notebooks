
package org.apache.fineract.portfolio.savings.domain;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface GSIMRepositoy
        extends JpaRepository<GroupSavingsIndividualMonitoring, Long>, JpaSpecificationExecutor<GroupSavingsIndividualMonitoring> {
    GroupSavingsIndividualMonitoring findOneByIsAcceptingChild(boolean acceptingChild);
    GroupSavingsIndividualMonitoring findOneByIsAcceptingChildAndApplicationId(boolean acceptingChild, BigDecimal applicationId);
    GroupSavingsIndividualMonitoring findOneByAccountNumber(String accountNumber);
}
