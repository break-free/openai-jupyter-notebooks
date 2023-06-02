
package org.apache.fineract.accounting.journalentry.data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.portfolio.savings.data.SavingsAccountTransactionEnumData;
@RequiredArgsConstructor
@Getter
public class SavingsTransactionDTO {
    private final Long officeId;
    private final Long paymentTypeId;
    private final String transactionId;
    private final LocalDate transactionDate;
    private final SavingsAccountTransactionEnumData transactionType;
    private final BigDecimal amount;
    private final boolean reversed;
    private final List<ChargePaymentDTO> feePayments;
    private final List<ChargePaymentDTO> penaltyPayments;
    private final BigDecimal overdraftAmount;
    private final boolean isAccountTransfer;
    private final List<TaxPaymentDTO> taxPayments;
    public boolean isOverdraftTransaction() {
        return this.overdraftAmount != null && this.overdraftAmount.doubleValue() > 0;
    }
}
