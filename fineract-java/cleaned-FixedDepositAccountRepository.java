
package org.apache.fineract.portfolio.savings.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface FixedDepositAccountRepository
        extends JpaRepository<FixedDepositAccount, Long>, JpaSpecificationExecutor<FixedDepositAccount> {
}
