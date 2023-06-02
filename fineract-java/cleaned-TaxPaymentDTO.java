
package org.apache.fineract.accounting.journalentry.data;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Getter
public class TaxPaymentDTO {
    private final Long debitAccountId;
    private final Long creditAccountId;
    private final BigDecimal amount;
}
