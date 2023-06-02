
package org.apache.fineract.portfolio.charge.domain;
import org.apache.fineract.portfolio.charge.exception.ChargeIsNotActiveException;
import org.apache.fineract.portfolio.charge.exception.ChargeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ChargeRepositoryWrapper {
    private final ChargeRepository repository;
    @Autowired
    public ChargeRepositoryWrapper(final ChargeRepository repository) {
        this.repository = repository;
    }
    public Charge findOneWithNotFoundDetection(final Long id) {
        final Charge chargeDefinition = this.repository.findById(id).orElseThrow(() -> new ChargeNotFoundException(id));
        if (chargeDefinition.isDeleted()) {
            throw new ChargeNotFoundException(id);
        }
        if (!chargeDefinition.isActive()) {
            throw new ChargeIsNotActiveException(id, chargeDefinition.getName());
        }
        return chargeDefinition;
    }
}
