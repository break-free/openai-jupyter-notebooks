
package org.apache.fineract.batch.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class ClientDetailsNotFoundException extends AbstractPlatformDomainRuleException {
    public ClientDetailsNotFoundException() {
        super("validation.msg.batch.jlg.no.clients.defined", "No Client details found", "");
    }
}
