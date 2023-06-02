
package org.apache.fineract.mix.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class XBRLMappingInvalidException extends AbstractPlatformDomainRuleException {
    public XBRLMappingInvalidException(final String msg) {
        super("error.msg.xbrl.report.mapping.invalid.id", "Mapping does not exist", msg);
    }
}
