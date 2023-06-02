
package org.apache.fineract.portfolio.savings.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface SavingsProductRepository extends JpaRepository<SavingsProduct, Long>, JpaSpecificationExecutor<SavingsProduct> {
}
