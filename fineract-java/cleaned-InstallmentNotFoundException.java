
package org.apache.fineract.portfolio.loanaccount.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class InstallmentNotFoundException extends AbstractPlatformResourceNotFoundException {
    public InstallmentNotFoundException(final Long id) {
        super("error.msg.transaction.id.invalid", "Transaction with identifier " + id + " does not contain any installment");
    }
}
