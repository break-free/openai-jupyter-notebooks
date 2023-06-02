
package org.apache.fineract.portfolio.loanproduct.service;
import java.util.Collection;
import org.apache.fineract.portfolio.loanproduct.data.LoanProductBorrowerCycleVariationData;
import org.apache.fineract.portfolio.loanproduct.data.LoanProductData;
public interface LoanProductReadPlatformService {
    Collection<LoanProductData> retrieveAllLoanProducts();
    Collection<LoanProductData> retrieveAllLoanProductsForLookup(String inClass);
    Collection<LoanProductData> retrieveAllLoanProductsForLookup();
    Collection<LoanProductData> retrieveAllLoanProductsForLookup(boolean activeOnly);
    LoanProductData retrieveLoanProduct(Long productId);
    LoanProductData retrieveNewLoanProductDetails();
    Collection<LoanProductData> retrieveAllLoanProductsForCurrency(String currencyCode);
    Collection<LoanProductData> retrieveAvailableLoanProductsForMix();
    Collection<LoanProductData> retrieveRestrictedProductsForMix(Long productId);
    Collection<LoanProductData> retrieveAllowedProductsForMix(Long productId);
    Collection<LoanProductBorrowerCycleVariationData> retrieveLoanProductBorrowerCycleVariations(Long loanProductId);
    LoanProductData retrieveLoanProductFloatingDetails(Long loanProductId);
}
