
package org.apache.fineract.portfolio.paymenttype.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long>, JpaSpecificationExecutor<PaymentType> {
}
