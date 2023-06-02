
package org.apache.fineract.commands.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class CommandNotAwaitingApprovalException extends AbstractPlatformResourceNotFoundException {
    public CommandNotAwaitingApprovalException(final Long id) {
        super("error.msg.command.id.not.awaiting.approval", "Audit with identifier " + id + " is Not Awaiting Approval", id);
    }
}
