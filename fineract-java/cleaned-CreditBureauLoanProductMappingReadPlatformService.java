
package org.apache.fineract.infrastructure.creditbureau.service;
import java.util.Collection;
import org.apache.fineract.infrastructure.creditbureau.data.CreditBureauLoanProductMappingData;
public interface CreditBureauLoanProductMappingReadPlatformService {
    Collection<CreditBureauLoanProductMappingData> readCreditBureauLoanProductMapping();
    Collection<CreditBureauLoanProductMappingData> fetchLoanProducts();
    CreditBureauLoanProductMappingData readMappingByLoanId(long loanProductId);
}
