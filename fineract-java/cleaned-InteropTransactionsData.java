
package org.apache.fineract.interoperation.data;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.savings.domain.SavingsAccount;
import org.apache.fineract.portfolio.savings.domain.SavingsAccountTransaction;
public class InteropTransactionsData extends CommandProcessingResult {
    List<InteropTransactionData> transactions;
    public InteropTransactionsData(Long entityId, List<InteropTransactionData> transactions) {
        super(entityId);
        this.transactions = transactions;
    }
    public static InteropTransactionsData build(SavingsAccount account, @NotNull Predicate<SavingsAccountTransaction> filter) {
        if (account == null) {
            return null;
        }
        List<InteropTransactionData> trans = account.getTransactions().stream().filter(filter).sorted((t1, t2) -> {
            int i = t2.getDateOf().compareTo(t1.getDateOf());
            return i != 0 ? i : Long.signum(t2.getId() - t1.getId());
        }).map(InteropTransactionData::build).collect(Collectors.toList());
        return new InteropTransactionsData(account.getId(), trans);
    }
}
