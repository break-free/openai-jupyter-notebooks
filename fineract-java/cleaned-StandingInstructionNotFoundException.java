
package org.apache.fineract.portfolio.account.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class StandingInstructionNotFoundException extends AbstractPlatformResourceNotFoundException {
    public StandingInstructionNotFoundException(final Long id) {
        super("error.msg.standinginstruction.id.invalid", "AccountTransferStandingInstruction with identifier " + id + " does not exist",
                id);
    }
}
