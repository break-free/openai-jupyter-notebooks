
package org.apache.fineract.portfolio.loanproduct.productmix.data;
import java.util.Collection;
import org.apache.fineract.portfolio.loanproduct.data.LoanProductData;
public class ProductMixData {
    private final Long productId;
    private final String productName;
    private final Collection<LoanProductData> restrictedProducts;
    private final Collection<LoanProductData> allowedProducts;
    @SuppressWarnings("unused")
    private final Collection<LoanProductData> productOptions;
    public ProductMixData(final Long productId, final String productName, final Collection<LoanProductData> restrictedProducts,
            final Collection<LoanProductData> allowedProducts, final Collection<LoanProductData> productOptions) {
        this.productId = productId;
        this.productName = productName;
        this.restrictedProducts = restrictedProducts;
        this.allowedProducts = allowedProducts;
        this.productOptions = productOptions;
    }
    public static ProductMixData template(final Collection<LoanProductData> productOptions) {
        return new ProductMixData(null, null, null, null, productOptions);
    }
    public static ProductMixData withTemplateOptions(final ProductMixData productMixData,
            final Collection<LoanProductData> productOptions) {
        return new ProductMixData(productMixData.productId, productMixData.productName, productMixData.restrictedProducts,
                productMixData.allowedProducts, productOptions);
    }
    public static ProductMixData withDetails(final Long productId, final String productName,
            final Collection<LoanProductData> restrictedProducts, final Collection<LoanProductData> allowedProducts) {
        return new ProductMixData(productId, productName, restrictedProducts, allowedProducts, null);
    }
    public static ProductMixData withRestrictedOptions(final Collection<LoanProductData> restrictedProducts,
            final Collection<LoanProductData> allowedProducts) {
        return new ProductMixData(null, null, restrictedProducts, allowedProducts, null);
    }
}
