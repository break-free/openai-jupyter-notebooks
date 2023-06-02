
package org.apache.fineract.accounting.journalentry.data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.portfolio.shareaccounts.data.ShareAccountTransactionEnumData;
@RequiredArgsConstructor
@Getter
public class SharesTransactionDTO {
    private final Long officeId;
    private final Long paymentTypeId;
    private final String transactionId;
    private final LocalDate transactionDate;
    private final ShareAccountTransactionEnumData transactionType;
    private final ShareAccountTransactionEnumData transactionStatus;
    private final BigDecimal amount;
    private final BigDecimal chargeAmount;
    private final List<ChargePaymentDTO> feePayments;
}
