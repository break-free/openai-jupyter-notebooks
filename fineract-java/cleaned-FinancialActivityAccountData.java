
package org.apache.fineract.accounting.financialactivityaccount.data;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.accounting.glaccount.data.GLAccountData;
@RequiredArgsConstructor
@Data
public class FinancialActivityAccountData {
    private final Long id;
    private final FinancialActivityData financialActivityData;
    private final GLAccountData glAccountData;
    private Map<String, List<GLAccountData>> glAccountOptions;
    private List<FinancialActivityData> financialActivityOptions;
    public FinancialActivityAccountData() {
        this.id = null;
        this.glAccountData = null;
        this.financialActivityData = null;
        this.glAccountOptions = null;
        this.financialActivityOptions = null;
    }
}
