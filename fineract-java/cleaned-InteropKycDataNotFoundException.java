
package org.apache.fineract.interoperation.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class InteropKycDataNotFoundException extends AbstractPlatformResourceNotFoundException {
    public InteropKycDataNotFoundException(Long clientId, Exception e) {
        super("error.msg.interop.kyc.data.not.found", "No KYC data found for clientID " + clientId, e);
    }
}
