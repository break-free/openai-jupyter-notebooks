
package org.apache.fineract.commands.exception;
import org.apache.fineract.commands.domain.CommandSource;
public class RollbackTransactionAsCommandIsNotApprovedByCheckerException extends RuntimeException {
    private final CommandSource commandSourceResult;
    public RollbackTransactionAsCommandIsNotApprovedByCheckerException(final CommandSource commandSourceResult) {
        this.commandSourceResult = commandSourceResult;
    }
    public CommandSource getCommandSourceResult() {
        return this.commandSourceResult;
    }
}
