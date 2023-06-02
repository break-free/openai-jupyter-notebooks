
package org.apache.fineract.portfolio.paymenttype.service;
import java.util.Map;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.infrastructure.core.exception.PlatformDataIntegrityException;
import org.apache.fineract.portfolio.paymenttype.api.PaymentTypeApiResourceConstants;
import org.apache.fineract.portfolio.paymenttype.data.PaymentTypeDataValidator;
import org.apache.fineract.portfolio.paymenttype.domain.PaymentType;
import org.apache.fineract.portfolio.paymenttype.domain.PaymentTypeRepository;
import org.apache.fineract.portfolio.paymenttype.domain.PaymentTypeRepositoryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
@Service
public class PaymentTypeWriteServiceImpl implements PaymentTypeWriteService {
    private final PaymentTypeRepository repository;
    private final PaymentTypeRepositoryWrapper repositoryWrapper;
    private final PaymentTypeDataValidator fromApiJsonDeserializer;
    @Autowired
    public PaymentTypeWriteServiceImpl(PaymentTypeRepository repository, PaymentTypeRepositoryWrapper repositoryWrapper,
            PaymentTypeDataValidator fromApiJsonDeserializer) {
        this.repository = repository;
        this.repositoryWrapper = repositoryWrapper;
        this.fromApiJsonDeserializer = fromApiJsonDeserializer;
    }
    @Override
    public CommandProcessingResult createPaymentType(JsonCommand command) {
        this.fromApiJsonDeserializer.validateForCreate(command.json());
        String name = command.stringValueOfParameterNamed(PaymentTypeApiResourceConstants.NAME);
        String description = command.stringValueOfParameterNamed(PaymentTypeApiResourceConstants.DESCRIPTION);
        Boolean isCashPayment = command.booleanObjectValueOfParameterNamed(PaymentTypeApiResourceConstants.ISCASHPAYMENT);
        Long position = command.longValueOfParameterNamed(PaymentTypeApiResourceConstants.POSITION);
        PaymentType newPaymentType = PaymentType.create(name, description, isCashPayment, position);
        this.repository.saveAndFlush(newPaymentType);
        return new CommandProcessingResultBuilder().withCommandId(command.commandId()).withEntityId(newPaymentType.getId()).build();
    }
    @Override
    public CommandProcessingResult updatePaymentType(Long paymentTypeId, JsonCommand command) {
        this.fromApiJsonDeserializer.validateForUpdate(command.json());
        final PaymentType paymentType = this.repositoryWrapper.findOneWithNotFoundDetection(paymentTypeId);
        final Map<String, Object> changes = paymentType.update(command);
        if (!changes.isEmpty()) {
            this.repository.save(paymentType);
        }
        return new CommandProcessingResultBuilder().withCommandId(command.commandId()).withEntityId(command.entityId()).build();
    }
    @Override
    public CommandProcessingResult deletePaymentType(Long paymentTypeId) {
        final PaymentType paymentType = this.repositoryWrapper.findOneWithNotFoundDetection(paymentTypeId);
        try {
            this.repository.delete(paymentType);
            this.repository.flush();
        } catch (final JpaSystemException | DataIntegrityViolationException e) {
            final Throwable throwable = e.getMostSpecificCause();
            handleDataIntegrityIssues(throwable, e);
        }
        return new CommandProcessingResultBuilder().withEntityId(paymentType.getId()).build();
    }
    private void handleDataIntegrityIssues(final Throwable realCause, final Exception dve) {
        if (realCause.getMessage().contains("acc_product_mapping")) {
            throw new PlatformDataIntegrityException("error.msg.payment.type.association.exist",
                    "cannot.delete.payment.type.with.association");
        } else if (realCause.getMessage().contains("payment_type_id")) {
            throw new PlatformDataIntegrityException("error.msg.payment.type.association.exist",
                    "cannot.delete.payment.type.with.association");
        }
        throw new PlatformDataIntegrityException("error.msg.paymenttypes.unknown.data.integrity.issue",
                "Unknown data integrity issue with resource.");
    }
}
