
package org.apache.fineract.portfolio.paymentdetail.domain;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface PaymentDetailRepository extends JpaRepository<PaymentDetail, Long>, JpaSpecificationExecutor<Loan> {
}
