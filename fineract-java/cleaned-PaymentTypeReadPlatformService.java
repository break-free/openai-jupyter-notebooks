
package org.apache.fineract.portfolio.paymenttype.service;
import java.util.Collection;
import org.apache.fineract.portfolio.paymenttype.data.PaymentTypeData;
public interface PaymentTypeReadPlatformService {
    Collection<PaymentTypeData> retrieveAllPaymentTypes();
    PaymentTypeData retrieveOne(Long paymentTypeId);
}
