
package org.apache.fineract.accounting.journalentry.data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.fineract.portfolio.loanaccount.data.LoanTransactionEnumData;
@RequiredArgsConstructor
@Getter
public class LoanTransactionDTO {
    private final Long officeId;
    private final Long paymentTypeId;
    private final String transactionId;
    private final LocalDate transactionDate;
    private final LoanTransactionEnumData transactionType;
    private final BigDecimal amount;
    private final BigDecimal principal;
    private final BigDecimal interest;
    private final BigDecimal fees;
    private final BigDecimal penalties;
    private final BigDecimal overPayment;
    private final boolean reversed;
    private final List<ChargePaymentDTO> penaltyPayments;
    private final List<ChargePaymentDTO> feePayments;
    private final boolean isAccountTransfer;
    @Setter
    private boolean isLoanToLoanTransfer;
}
