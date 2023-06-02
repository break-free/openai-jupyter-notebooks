
package org.apache.fineract.accounting.provisioning.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class ProvisioningEntryNotfoundException extends AbstractPlatformResourceNotFoundException {
    public ProvisioningEntryNotfoundException(Long id) {
        super("error.msg.provisioningentry.id.invalid", "ProvisioningEntry with identifier " + id + " does not exist", id);
    }
}
