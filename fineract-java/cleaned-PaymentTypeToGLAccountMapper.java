
package org.apache.fineract.accounting.producttoaccountmapping.data;
import java.io.Serializable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.accounting.glaccount.data.GLAccountData;
import org.apache.fineract.portfolio.paymenttype.data.PaymentTypeData;
@RequiredArgsConstructor
@Getter
public class PaymentTypeToGLAccountMapper implements Serializable {
    private final PaymentTypeData paymentType;
    private final GLAccountData fundSourceAccount;
}
