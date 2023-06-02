
package org.apache.fineract.organisation.provisioning.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class ProvisioningCriteriaCannotBeDeletedException extends AbstractPlatformDomainRuleException {
    public ProvisioningCriteriaCannotBeDeletedException(Long criteriaId) {
        super("error.msg.provisioningcriteria.id.usedin.provisioning.entries",
                "Provisioningcriteria with identifier " + criteriaId + "associated in journal entries");
    }
}
