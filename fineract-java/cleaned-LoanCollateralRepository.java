
package org.apache.fineract.portfolio.collateral.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface LoanCollateralRepository extends JpaRepository<LoanCollateral, Long>, JpaSpecificationExecutor<LoanCollateral> {
    LoanCollateral findByLoanIdAndId(Long loanId, Long id);
}
