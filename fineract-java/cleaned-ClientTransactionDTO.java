
package org.apache.fineract.accounting.journalentry.data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.portfolio.client.domain.ClientTransactionType;
@RequiredArgsConstructor
@Getter
public class ClientTransactionDTO {
    private final Long clientId;
    private final Long officeId;
    private final Long paymentTypeId;
    private final Long transactionId;
    private final LocalDate transactionDate;
    private final EnumOptionData transactionType;
    private final String currencyCode;
    private final BigDecimal amount;
    private final boolean reversed;
    private final boolean accountingEnabled;
    private final List<ClientChargePaymentDTO> chargePayments;
    public boolean isChargePayment() {
        return ClientTransactionType.PAY_CHARGE.getValue().equals(this.transactionType.getId().intValue());
    }
}
