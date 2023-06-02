
package org.apache.fineract.portfolio.floatingrates.service;
import java.util.List;
import org.apache.fineract.portfolio.floatingrates.data.FloatingRateData;
import org.apache.fineract.portfolio.floatingrates.data.InterestRatePeriodData;
public interface FloatingRatesReadPlatformService {
    List<FloatingRateData> retrieveAll();
    List<FloatingRateData> retrieveLookupActive();
    FloatingRateData retrieveOne(Long floatingRateId);
    List<FloatingRateData> retrieveAllActive();
    FloatingRateData retrieveBaseLendingRate();
    List<InterestRatePeriodData> retrieveInterestRatePeriods(Long productId);
}
