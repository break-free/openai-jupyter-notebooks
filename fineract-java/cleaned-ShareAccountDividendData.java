
package org.apache.fineract.portfolio.shareaccounts.data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
@SuppressWarnings("unused")
public class ShareAccountDividendData implements Serializable {
    private final Long id;
    private final LocalDate postedDate;
    private final ShareAccountData accountData;
    private final BigDecimal amount;
    private final EnumOptionData status;
    private final Long savingsTransactionId;
    public ShareAccountDividendData(final Long id, final ShareAccountData accountData, final BigDecimal amount) {
        this.id = id;
        this.accountData = accountData;
        this.amount = amount;
        this.status = null;
        this.savingsTransactionId = null;
        this.postedDate = null;
    }
    public ShareAccountDividendData(final Long id, final ShareAccountData accountData, final BigDecimal amount, final EnumOptionData status,
            final Long savingsTransactionId) {
        this.id = id;
        this.accountData = accountData;
        this.amount = amount;
        this.status = status;
        this.savingsTransactionId = savingsTransactionId;
        this.postedDate = null;
    }
    public ShareAccountDividendData(final Long id, final LocalDate postedDate, final ShareAccountData accountData, final BigDecimal amount,
            final EnumOptionData status, final Long savingsTransactionId) {
        this.id = id;
        this.accountData = accountData;
        this.amount = amount;
        this.status = status;
        this.savingsTransactionId = savingsTransactionId;
        this.postedDate = postedDate;
    }
}
