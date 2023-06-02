
package org.apache.fineract.infrastructure.accountnumberformat.service;
import java.util.List;
import org.apache.fineract.infrastructure.accountnumberformat.data.AccountNumberFormatData;
import org.apache.fineract.infrastructure.accountnumberformat.domain.EntityAccountType;
public interface AccountNumberFormatReadPlatformService {
    List<AccountNumberFormatData> getAllAccountNumberFormats();
    AccountNumberFormatData getAccountNumberFormat(Long id);
    AccountNumberFormatData retrieveTemplate(EntityAccountType entityAccountTypeForTemplate);
}
