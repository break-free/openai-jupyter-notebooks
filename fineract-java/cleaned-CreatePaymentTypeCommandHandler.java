
package org.apache.fineract.portfolio.paymenttype.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.paymenttype.service.PaymentTypeWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "PAYMENTTYPE", action = "CREATE")
public class CreatePaymentTypeCommandHandler implements NewCommandSourceHandler {
    private final PaymentTypeWriteService paymentTypeWriteService;
    @Autowired
    public CreatePaymentTypeCommandHandler(final PaymentTypeWriteService paymentTypeWriteService) {
        this.paymentTypeWriteService = paymentTypeWriteService;
    }
    @Override
    @Transactional
    public CommandProcessingResult processCommand(JsonCommand command) {
        return this.paymentTypeWriteService.createPaymentType(command);
    }
}
