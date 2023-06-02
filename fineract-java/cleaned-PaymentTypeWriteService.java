
package org.apache.fineract.portfolio.paymenttype.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface PaymentTypeWriteService {
    CommandProcessingResult createPaymentType(JsonCommand command);
    CommandProcessingResult updatePaymentType(Long id, JsonCommand command);
    CommandProcessingResult deletePaymentType(Long id);
}
