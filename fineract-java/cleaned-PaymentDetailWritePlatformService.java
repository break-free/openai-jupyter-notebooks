
package org.apache.fineract.portfolio.paymentdetail.service;
import java.util.Map;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.portfolio.paymentdetail.domain.PaymentDetail;
public interface PaymentDetailWritePlatformService {
    PaymentDetail createAndPersistPaymentDetail(JsonCommand command, Map<String, Object> changes);
    PaymentDetail createPaymentDetail(JsonCommand command, Map<String, Object> changes);
    PaymentDetail persistPaymentDetail(PaymentDetail paymentDetail);
}
