
package org.apache.fineract.portfolio.paymenttype.domain;
import org.apache.fineract.portfolio.paymenttype.exception.PaymentTypeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class PaymentTypeRepositoryWrapper {
    private final PaymentTypeRepository repository;
    @Autowired
    public PaymentTypeRepositoryWrapper(final PaymentTypeRepository repository) {
        this.repository = repository;
    }
    public PaymentType findOneWithNotFoundDetection(final Long id) {
        return this.repository.findById(id).orElseThrow(() -> new PaymentTypeNotFoundException(id));
    }
}
