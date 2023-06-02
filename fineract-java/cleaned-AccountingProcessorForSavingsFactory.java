
package org.apache.fineract.accounting.journalentry.service;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.accounting.journalentry.data.SavingsDTO;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class AccountingProcessorForSavingsFactory {
    private final ApplicationContext applicationContext;
    public AccountingProcessorForSavings determineProcessor(final SavingsDTO savingsDTO) {
        AccountingProcessorForSavings accountingProcessorForSavings = null;
        if (savingsDTO.isCashBasedAccountingEnabled()) {
            accountingProcessorForSavings = this.applicationContext.getBean("cashBasedAccountingProcessorForSavings",
                    AccountingProcessorForSavings.class);
        }
        return accountingProcessorForSavings;
    }
}
