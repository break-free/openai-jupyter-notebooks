
package org.apache.fineract.portfolio.account.api;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.apache.fineract.portfolio.account.AccountDetailConstants;
import org.apache.fineract.portfolio.account.data.AccountTransferData;
public final class AccountTransfersApiConstants {
    private AccountTransfersApiConstants() {
    }
    public static final String ACCOUNT_TRANSFER_RESOURCE_NAME = "accounttransfer";
    public static final String transferDateParamName = "transferDate";
    public static final String transferAmountParamName = "transferAmount";
    public static final String transferDescriptionParamName = "transferDescription";
    public static final String currencyParamName = "currency";
    static final Set<String> RESPONSE_DATA_PARAMETERS = new HashSet<>(
            Arrays.asList(AccountDetailConstants.idParamName, transferDescriptionParamName, currencyParamName));
}
