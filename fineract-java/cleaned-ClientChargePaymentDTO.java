
package org.apache.fineract.accounting.journalentry.data;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Getter
public class ClientChargePaymentDTO {
    private final Long chargeId;
    private final BigDecimal amount;
    private final Long clientChargeId;
    private final boolean isPenalty;
    private final Long incomeAccountId;
}
