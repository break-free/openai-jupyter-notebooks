
package org.apache.fineract.accounting.journalentry.service;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.accounting.journalentry.data.SharesDTO;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class AccountingProcessorForSharesFactory {
    private final ApplicationContext applicationContext;
    public AccountingProcessorForShares determineProcessor(final SharesDTO sharesDTO) {
        AccountingProcessorForShares accountingProcessorForShares = null;
        if (sharesDTO.isCashBasedAccountingEnabled()) {
            accountingProcessorForShares = this.applicationContext.getBean("cashBasedAccountingProcessorForShares",
                    AccountingProcessorForShares.class);
        }
        return accountingProcessorForShares;
    }
}
