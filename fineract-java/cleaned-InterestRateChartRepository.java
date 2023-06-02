
package org.apache.fineract.portfolio.interestratechart.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface InterestRateChartRepository extends JpaRepository<InterestRateChart, Long>, JpaSpecificationExecutor<InterestRateChart> {
}
