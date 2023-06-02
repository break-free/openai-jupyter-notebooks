
package org.apache.fineract.organisation.provisioning.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class ProvisioningCriteriaNotFoundException extends AbstractPlatformResourceNotFoundException {
    public ProvisioningCriteriaNotFoundException(final Long id) {
        super("error.msg.provisioning.criteria.id.invalid", "Provisioning Criteria with identifier " + id + " does not exist", id);
    }
    public ProvisioningCriteriaNotFoundException(Long id, EmptyResultDataAccessException e) {
        super("error.msg.provisioning.criteria.id.invalid", "Provisioning Criteria with identifier " + id + " does not exist", id, e);
    }
}
