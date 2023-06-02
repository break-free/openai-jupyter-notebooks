
package org.apache.fineract.portfolio.loanaccount.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface LoanChargePaidByRepository extends JpaRepository<LoanChargePaidBy, Long>, JpaSpecificationExecutor<LoanCharge> {
    @Query("select lp from LoanChargePaidBy lp where lp.loanCharge=:loanCharge and lp.installmentNumber=:installmentNumber")
    LoanChargePaidBy getLoanChargePaidByLoanCharge(@Param("loanCharge") LoanCharge loanCharge,
            @Param("installmentNumber") Integer installmentNo);
}
