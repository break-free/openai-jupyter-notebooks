
package org.apache.fineract.portfolio.account.domain;
import java.util.Collection;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.apache.fineract.portfolio.savings.domain.SavingsAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface StandingInstructionRepository
        extends JpaRepository<AccountTransferStandingInstruction, Long>, JpaSpecificationExecutor<AccountTransferStandingInstruction> {
    String FIND_BY_LOAN_AND_STATUS_QUERY = "select accountTransferStandingInstruction "
            + "from AccountTransferStandingInstruction accountTransferStandingInstruction "
            + "where accountTransferStandingInstruction.status = :status "
            + "and (accountTransferStandingInstruction.accountTransferDetails.toLoanAccount = :loan "
            + "or accountTransferStandingInstruction.accountTransferDetails.fromLoanAccount = :loan)";
    String FIND_BY_SAVINGS_AND_STATUS_QUERY = "select accountTransferStandingInstruction "
            + "from AccountTransferStandingInstruction accountTransferStandingInstruction "
            + "where accountTransferStandingInstruction.status = :status "
            + "and (accountTransferStandingInstruction.accountTransferDetails.toSavingsAccount = :savingsAccount "
            + "or accountTransferStandingInstruction.accountTransferDetails.fromSavingsAccount = :savingsAccount)";
    @Query(FIND_BY_LOAN_AND_STATUS_QUERY)
    Collection<AccountTransferStandingInstruction> findByLoanAccountAndStatus(@Param("loan") Loan loan, @Param("status") Integer status);
    @Query(FIND_BY_SAVINGS_AND_STATUS_QUERY)
    Collection<AccountTransferStandingInstruction> findBySavingsAccountAndStatus(@Param("savingsAccount") SavingsAccount savingsAccount,
            @Param("status") Integer status);
}
