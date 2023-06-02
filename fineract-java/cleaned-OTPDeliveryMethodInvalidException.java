
package org.apache.fineract.infrastructure.security.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class OTPDeliveryMethodInvalidException extends AbstractPlatformDomainRuleException {
    public OTPDeliveryMethodInvalidException() {
        super("error.msg.twofactor.otp.delivery.invalid",
                "The requested OTP delivery method " + "is not supported or not currently unavailable.");
    }
}
