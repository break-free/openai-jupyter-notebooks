
package org.apache.fineract.infrastructure.campaigns.sms.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class SmsRuntimeException extends AbstractPlatformDomainRuleException {
    public SmsRuntimeException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}
