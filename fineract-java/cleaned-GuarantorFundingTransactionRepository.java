
package org.apache.fineract.portfolio.loanaccount.guarantor.domain;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface GuarantorFundingTransactionRepository
        extends JpaRepository<GuarantorFundingTransaction, Long>, JpaSpecificationExecutor<GuarantorFundingTransaction> {
    @Query("select ft from GuarantorFundingTransaction ft where ft.loanTransaction.id in :loanTransactions")
    List<GuarantorFundingTransaction> fetchGuarantorFundingTransactions(@Param("loanTransactions") List<Long> loanTransactions);
}
