
package org.apache.fineract.portfolio.loanaccount.domain;
import org.apache.fineract.portfolio.loanproduct.domain.LoanTransactionProcessingStrategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface LoanTransactionProcessingStrategyRepository
        extends JpaRepository<LoanTransactionProcessingStrategy, Long>, JpaSpecificationExecutor<LoanTransactionProcessingStrategy> {
}
