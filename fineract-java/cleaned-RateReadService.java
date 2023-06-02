
package org.apache.fineract.portfolio.rate.service;
import java.util.Collection;
import java.util.List;
import org.apache.fineract.portfolio.rate.data.RateData;
public interface RateReadService {
    Collection<RateData> retrieveAllRates();
    Collection<RateData> retrieveLoanApplicableRates();
    RateData retrieveOne(Long rateId);
    RateData retrieveByName(String name);
    List<RateData> retrieveProductLoanRates(Long loanId);
    List<RateData> retrieveLoanRates(Long loanId);
}
