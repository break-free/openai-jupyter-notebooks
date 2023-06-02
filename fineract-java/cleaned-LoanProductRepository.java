
package org.apache.fineract.portfolio.loanproduct.domain;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface LoanProductRepository extends JpaRepository<LoanProduct, Long>, JpaSpecificationExecutor<LoanProduct> {
    @Query("select loanProduct from LoanProduct loanProduct, IN(loanProduct.charges) charge where charge.id = :chargeId")
    List<LoanProduct> retrieveLoanProductsByChargeId(@Param("chargeId") Long chargeId);
}
