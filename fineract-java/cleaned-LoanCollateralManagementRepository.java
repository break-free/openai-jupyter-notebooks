
package org.apache.fineract.portfolio.loanaccount.domain;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
public interface LoanCollateralManagementRepository
        extends JpaRepository<LoanCollateralManagement, Long>, JpaSpecificationExecutor<LoanCollateralManagement> {
    @Query("select loanCollaterals from LoanCollateralManagement loanCollaterals where loanCollaterals.loan=:loan")
    List<LoanCollateralManagement> findByLoan(Loan loan);
}
