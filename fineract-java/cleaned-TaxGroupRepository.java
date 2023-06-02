
package org.apache.fineract.portfolio.tax.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface TaxGroupRepository extends JpaRepository<TaxGroup, Long>, JpaSpecificationExecutor<TaxGroup> {
}
