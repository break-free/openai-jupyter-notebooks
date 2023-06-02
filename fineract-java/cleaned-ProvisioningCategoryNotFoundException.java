
package org.apache.fineract.organisation.provisioning.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class ProvisioningCategoryNotFoundException extends AbstractPlatformResourceNotFoundException {
    public ProvisioningCategoryNotFoundException(final Long id) {
        super("error.msg.provisioning.id.invalid", "Provisioning Category with identifier " + id + " does not exist", id);
    }
}
