
package org.apache.fineract.accounting.producttoaccountmapping.data;
import java.io.Serializable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.accounting.glaccount.data.GLAccountData;
import org.apache.fineract.portfolio.charge.data.ChargeData;
@RequiredArgsConstructor
@Getter
public class ChargeToGLAccountMapper implements Serializable {
    private final ChargeData charge;
    private final GLAccountData incomeAccount;
}
