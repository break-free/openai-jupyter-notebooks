
package org.apache.fineract.accounting.journalentry.service;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.accounting.closure.domain.GLClosure;
import org.apache.fineract.accounting.journalentry.data.ClientTransactionDTO;
import org.apache.fineract.organisation.office.domain.Office;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class CashBasedAccountingProcessorForClientTransactions implements AccountingProcessorForClientTransactions {
    private final AccountingProcessorHelper helper;
    @Override
    public void createJournalEntriesForClientTransaction(ClientTransactionDTO clientTransactionDTO) {
        if (clientTransactionDTO.isAccountingEnabled()) {
            final GLClosure latestGLClosure = this.helper.getLatestClosureByBranch(clientTransactionDTO.getOfficeId());
            final LocalDate transactionDate = clientTransactionDTO.getTransactionDate();
            final Office office = this.helper.getOfficeById(clientTransactionDTO.getOfficeId());
            this.helper.checkForBranchClosures(latestGLClosure, transactionDate);
            if (clientTransactionDTO.isChargePayment()) {
                createJournalEntriesForChargePayments(clientTransactionDTO, office);
            }
        }
    }
    private void createJournalEntriesForChargePayments(final ClientTransactionDTO clientTransactionDTO, final Office office) {
        final Long clientId = clientTransactionDTO.getClientId();
        final String currencyCode = clientTransactionDTO.getCurrencyCode();
        final Long transactionId = clientTransactionDTO.getTransactionId();
        final LocalDate transactionDate = clientTransactionDTO.getTransactionDate();
        final BigDecimal amount = clientTransactionDTO.getAmount();
        final boolean isReversal = clientTransactionDTO.isReversed();
        if (amount != null && !(amount.compareTo(BigDecimal.ZERO) == 0)) {
            BigDecimal totalCreditedAmount = this.helper.createCreditJournalEntryOrReversalForClientPayments(office, currencyCode, clientId,
                    transactionId, transactionDate, isReversal, clientTransactionDTO.getChargePayments());
            this.helper.createDebitJournalEntryOrReversalForClientChargePayments(office, currencyCode, clientId, transactionId,
                    transactionDate, totalCreditedAmount, isReversal);
        }
    }
}
