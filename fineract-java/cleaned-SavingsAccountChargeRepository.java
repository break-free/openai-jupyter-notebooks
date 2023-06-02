
package org.apache.fineract.portfolio.savings.domain;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface SavingsAccountChargeRepository
        extends JpaRepository<SavingsAccountCharge, Long>, JpaSpecificationExecutor<SavingsAccountCharge> {
    @Query("select sac from SavingsAccountCharge sac where sac.id =:id and sac.savingsAccount.id = :savingsAccountId")
    SavingsAccountCharge findByIdAndSavingsAccountId(@Param("id") Long id, @Param("savingsAccountId") Long savingsAccountId);
    @Query("select sac from SavingsAccountCharge sac where sac.dueDate <=:transactionDate and sac.waived = false and sac.paid=false order by sac.dueDate")
    List<SavingsAccountCharge> findPendingCharges(@Param("transactionDate") LocalDate transactionDate);
}
