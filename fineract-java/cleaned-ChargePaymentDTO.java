
package org.apache.fineract.accounting.journalentry.data;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Getter
public class ChargePaymentDTO {
    private final Long chargeId;
    private final BigDecimal amount;
    private final Long loanChargeId;
}
