
package org.apache.fineract.portfolio.paymenttype.api;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
public final class PaymentTypeApiResourceConstants {
    private PaymentTypeApiResourceConstants() {
    }
    public static final String RESOURCE_NAME = "paymenttype";
    public static final String ENTITY_NAME = "PAYMENTTYPE";
    public static final String resourceNameForPermissions = "PAYMENT_TYPE";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String ISCASHPAYMENT = "isCashPayment";
    public static final String POSITION = "position";
    static final Set<String> RESPONSE_DATA_PARAMETERS = new HashSet<>(Arrays.asList(ID, NAME, DESCRIPTION, ISCASHPAYMENT));
}
