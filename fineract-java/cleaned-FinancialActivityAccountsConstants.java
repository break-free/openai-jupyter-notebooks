
package org.apache.fineract.accounting.financialactivityaccount.api;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
public final class FinancialActivityAccountsConstants {
    private FinancialActivityAccountsConstants() {
    }
    private static final String idParamName = "id";
    private static final String factivityDataParamName = "financialActivityData";
    private static final String glAccountDataParamName = "glAccountData";
    private static final String glAccountOptionsParamName = "glAccountOptions";
    private static final String financialActivityOptionsParamName = "financialActivityOptions";
    public static final String resourceNameForPermission = "FINANCIALACTIVITYACCOUNT";
    static final Set<String> RESPONSE_DATA_PARAMETERS = new HashSet<>(Arrays.asList(idParamName, factivityDataParamName,
            glAccountDataParamName, glAccountOptionsParamName, financialActivityOptionsParamName));
}
