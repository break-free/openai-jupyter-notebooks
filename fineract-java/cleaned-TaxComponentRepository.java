
package org.apache.fineract.portfolio.tax.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface TaxComponentRepository extends JpaRepository<TaxComponent, Long>, JpaSpecificationExecutor<TaxComponent> {
}
