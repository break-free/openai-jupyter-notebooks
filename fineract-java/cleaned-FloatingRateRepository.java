
package org.apache.fineract.portfolio.floatingrates.domain;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
public interface FloatingRateRepository extends JpaRepository<FloatingRate, Long>, JpaSpecificationExecutor<FloatingRate> {
    @Query("select floatingRate from FloatingRate floatingRate where floatingRate.isBaseLendingRate = true and floatingRate.isActive = true")
    FloatingRate retrieveBaseLendingRate();
    @Query("select floatingRate from FloatingRate floatingRate " + " inner join floatingRate.floatingRatePeriods as periods"
            + " where floatingRate.isActive = true " + " and periods.isActive = true "
            + " and periods.isDifferentialToBaseLendingRate = true")
    Collection<FloatingRate> retrieveFloatingRatesLinkedToBLR();
}
