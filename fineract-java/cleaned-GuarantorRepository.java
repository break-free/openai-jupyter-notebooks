
package org.apache.fineract.portfolio.loanaccount.guarantor.domain;
import java.util.List;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface GuarantorRepository extends JpaRepository<Guarantor, Long>, JpaSpecificationExecutor<Guarantor> {
    Guarantor findByLoanAndId(Loan loan, Long id);
    List<Guarantor> findByLoan(Loan loan);
}
