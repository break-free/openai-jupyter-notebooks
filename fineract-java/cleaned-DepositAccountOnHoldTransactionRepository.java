
package org.apache.fineract.portfolio.savings.domain;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface DepositAccountOnHoldTransactionRepository
        extends JpaRepository<DepositAccountOnHoldTransaction, Long>, JpaSpecificationExecutor<DepositAccountOnHoldTransaction> {
    List<DepositAccountOnHoldTransaction> findBySavingsAccountAndReversedFalseOrderByCreatedDateAsc(SavingsAccount account);
}
