
package org.apache.fineract.infrastructure.creditbureau.domain;
import org.apache.fineract.portfolio.loanproduct.domain.LoanProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface CreditBureauLoanProductMappingRepository
        extends JpaRepository<CreditBureauLoanProductMapping, Long>, JpaSpecificationExecutor<CreditBureauLoanProductMapping> {
    CreditBureauLoanProductMapping findOneByLoanProduct(LoanProduct loanProduct);
    CreditBureauLoanProductMapping findOneByLoanProductId(Long loanProductID);
}
