
package org.apache.fineract.portfolio.shareaccounts.exceptions;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class IssueableSharesExceededException extends AbstractPlatformResourceNotFoundException {
    public IssueableSharesExceededException() {
        super("error.msg.shares.issuable.shares.exceeded", "Issueable Shares exceeded than product definition");
    }
}
