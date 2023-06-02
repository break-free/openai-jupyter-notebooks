
package org.apache.fineract.portfolio.paymentdetail.service;
import java.util.Map;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.portfolio.paymentdetail.PaymentDetailConstants;
import org.apache.fineract.portfolio.paymentdetail.domain.PaymentDetail;
import org.apache.fineract.portfolio.paymentdetail.domain.PaymentDetailRepository;
import org.apache.fineract.portfolio.paymenttype.domain.PaymentType;
import org.apache.fineract.portfolio.paymenttype.domain.PaymentTypeRepositoryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class PaymentDetailWritePlatformServiceJpaRepositoryImpl implements PaymentDetailWritePlatformService {
    private final PaymentDetailRepository paymentDetailRepository;
    private final PaymentTypeRepositoryWrapper paymentTyperepositoryWrapper;
    @Autowired
    public PaymentDetailWritePlatformServiceJpaRepositoryImpl(final PaymentDetailRepository paymentDetailRepository,
            final PaymentTypeRepositoryWrapper paymentTyperepositoryWrapper) {
        this.paymentDetailRepository = paymentDetailRepository;
        this.paymentTyperepositoryWrapper = paymentTyperepositoryWrapper;
    }
    @Override
    public PaymentDetail createPaymentDetail(final JsonCommand command, final Map<String, Object> changes) {
        final Long paymentTypeId = command.longValueOfParameterNamed(PaymentDetailConstants.paymentTypeParamName);
        if (paymentTypeId == null) {
            return null;
        }
        final PaymentType paymentType = this.paymentTyperepositoryWrapper.findOneWithNotFoundDetection(paymentTypeId);
        final PaymentDetail paymentDetail = PaymentDetail.generatePaymentDetail(paymentType, command, changes);
        return paymentDetail;
    }
    @Override
    @Transactional
    public PaymentDetail persistPaymentDetail(final PaymentDetail paymentDetail) {
        return this.paymentDetailRepository.save(paymentDetail);
    }
    @Override
    @Transactional
    public PaymentDetail createAndPersistPaymentDetail(final JsonCommand command, final Map<String, Object> changes) {
        final PaymentDetail paymentDetail = createPaymentDetail(command, changes);
        if (paymentDetail != null) {
            return persistPaymentDetail(paymentDetail);
        }
        return paymentDetail;
    }
}
