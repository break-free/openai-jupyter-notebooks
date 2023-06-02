
package org.apache.fineract.infrastructure.businessdate.domain;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface BusinessDateRepository extends JpaRepository<BusinessDate, Long>, JpaSpecificationExecutor<BusinessDate> {
    Optional<BusinessDate> findByType(BusinessDateType type);
}
