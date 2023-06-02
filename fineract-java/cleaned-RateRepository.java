
package org.apache.fineract.portfolio.rate.domain;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface RateRepository extends JpaRepository<Rate, Long>, JpaSpecificationExecutor<Rate> {
    Rate findByName(String name);
    List<Rate> findAllByActiveAndProductApply(boolean active, String productApply);
}
