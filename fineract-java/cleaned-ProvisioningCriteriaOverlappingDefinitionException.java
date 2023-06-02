
package org.apache.fineract.organisation.provisioning.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class ProvisioningCriteriaOverlappingDefinitionException extends AbstractPlatformResourceNotFoundException {
    public ProvisioningCriteriaOverlappingDefinitionException() {
        super("error.msg.provisioning.criteria.definitions.are.overlapping", "Provisioning Criteria definitions are overlapping ");
    }
}
