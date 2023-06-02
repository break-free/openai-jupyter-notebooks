
package org.apache.fineract.portfolio.loanaccount.guarantor.data;
import org.apache.fineract.portfolio.loanaccount.data.LoanTransactionData;
import org.apache.fineract.portfolio.savings.data.DepositAccountOnHoldTransactionData;
public final class GuarantorTransactionData {
    @SuppressWarnings("unused")
    private final Long id;
    @SuppressWarnings("unused")
    private final DepositAccountOnHoldTransactionData onHoldTransactionData;
    @SuppressWarnings("unused")
    private final LoanTransactionData loanTransactionData;
    @SuppressWarnings("unused")
    private final boolean reversed;
    private GuarantorTransactionData(final Long id, final DepositAccountOnHoldTransactionData onHoldTransactionData,
            final LoanTransactionData loanTransactionData, final boolean reversed) {
        this.id = id;
        this.onHoldTransactionData = onHoldTransactionData;
        this.loanTransactionData = loanTransactionData;
        this.reversed = reversed;
    }
    public static GuarantorTransactionData instance(final Long id, final DepositAccountOnHoldTransactionData onHoldTransactionData,
            final LoanTransactionData loanTransactionData, final boolean reversed) {
        return new GuarantorTransactionData(id, onHoldTransactionData, loanTransactionData, reversed);
    }
}
