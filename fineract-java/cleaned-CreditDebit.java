
package org.apache.fineract.accounting.journalentry.data;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Getter
public class CreditDebit {
    private final Long glAccountId;
    private final BigDecimal amount;
}
