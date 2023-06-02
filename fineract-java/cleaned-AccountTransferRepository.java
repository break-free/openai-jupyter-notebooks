
package org.apache.fineract.portfolio.account.domain;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface AccountTransferRepository
        extends JpaRepository<AccountTransferTransaction, Long>, JpaSpecificationExecutor<AccountTransferTransaction> {
    @Query("select att from AccountTransferTransaction att where att.accountTransferDetails.fromLoanAccount.id= :accountNumber and att.reversed=false")
    List<AccountTransferTransaction> findByFromLoanId(@Param("accountNumber") Long accountNumber);
    @Query("select att from AccountTransferTransaction att where (att.accountTransferDetails.fromLoanAccount.id= :accountNumber or att.accountTransferDetails.toLoanAccount.id=:accountNumber) and att.reversed=false order by att.id desc")
    List<AccountTransferTransaction> findAllByLoanId(@Param("accountNumber") Long accountNumber);
    @Query("select att from AccountTransferTransaction att where att.toLoanTransaction.id= :loanTransactionId and att.reversed=false")
    AccountTransferTransaction findByToLoanTransactionId(@Param("loanTransactionId") Long loanTransactionId);
    @Query("select att from AccountTransferTransaction att where att.fromLoanTransaction.id IN :loanTransactions and att.reversed=false")
    List<AccountTransferTransaction> findByFromLoanTransactions(@Param("loanTransactions") Collection<Long> loanTransactions);
}
