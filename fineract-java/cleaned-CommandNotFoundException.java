
package org.apache.fineract.commands.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class CommandNotFoundException extends AbstractPlatformResourceNotFoundException {
    public CommandNotFoundException(final Long id) {
        super("error.msg.command.id.invalid", "Audit with identifier " + id + " does not exist", id);
    }
}
