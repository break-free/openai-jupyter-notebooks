
package org.apache.fineract.infrastructure.dataqueries.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class DatatableSystemErrorException extends AbstractPlatformDomainRuleException {
    public DatatableSystemErrorException(final String msg) {
        super("error.msg.datatable.system.error", msg, msg);
    }
}
