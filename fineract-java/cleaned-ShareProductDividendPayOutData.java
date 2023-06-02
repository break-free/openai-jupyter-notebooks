
package org.apache.fineract.portfolio.shareproducts.data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.portfolio.shareaccounts.data.ShareAccountDividendData;
public class ShareProductDividendPayOutData {
    @SuppressWarnings("unused")
    private final Long id;
    @SuppressWarnings("unused")
    private final ShareProductData productData;
    @SuppressWarnings("unused")
    private final BigDecimal amount;
    @SuppressWarnings("unused")
    private final EnumOptionData status;
    @SuppressWarnings("unused")
    private final LocalDate dividendPeriodStartDate;
    @SuppressWarnings("unused")
    private final LocalDate dividendPeriodEndDate;
    @SuppressWarnings("unused")
    private final Collection<ShareAccountDividendData> accountDividendsData;
    public ShareProductDividendPayOutData(final Long id, final ShareProductData productData, final BigDecimal amount,
            LocalDate dividendStartDate, final LocalDate dividendEndDate, final Collection<ShareAccountDividendData> accountDividendsData,
            final EnumOptionData status) {
        this.id = id;
        this.productData = productData;
        this.amount = amount;
        this.dividendPeriodEndDate = dividendEndDate;
        this.accountDividendsData = accountDividendsData;
        this.dividendPeriodStartDate = dividendStartDate;
        this.status = status;
    }
}
