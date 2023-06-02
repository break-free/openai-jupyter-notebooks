
package org.apache.fineract.accounting.financialactivityaccount.data;
import lombok.Data;
import org.apache.fineract.accounting.glaccount.domain.GLAccountType;
@Data
public class FinancialActivityData {
    private final Integer id;
    private final String name;
    private final GLAccountType mappedGLAccountType;
}
