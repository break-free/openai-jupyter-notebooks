
package org.apache.fineract.accounting.provisioning.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class NoProvisioningCriteriaDefinitionFound extends AbstractPlatformResourceNotFoundException {
    public NoProvisioningCriteriaDefinitionFound() {
        super("error.msg.no.provisioning.criteria.definitions.found", "No Provisioning Criteria Definitions are found");
    }
}
