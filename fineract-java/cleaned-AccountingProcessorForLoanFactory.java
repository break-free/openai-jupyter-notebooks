
package org.apache.fineract.accounting.journalentry.service;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.accounting.journalentry.data.LoanDTO;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class AccountingProcessorForLoanFactory {
    private final ApplicationContext applicationContext;
    public AccountingProcessorForLoan determineProcessor(final LoanDTO loanDTO) {
        AccountingProcessorForLoan accountingProcessorForLoan = null;
        if (loanDTO.isCashBasedAccountingEnabled()) {
            accountingProcessorForLoan = this.applicationContext.getBean("cashBasedAccountingProcessorForLoan",
                    AccountingProcessorForLoan.class);
        } else if (loanDTO.isUpfrontAccrualBasedAccountingEnabled()) {
            accountingProcessorForLoan = this.applicationContext.getBean("accrualBasedAccountingProcessorForLoan",
                    AccountingProcessorForLoan.class);
        } else if (loanDTO.isPeriodicAccrualBasedAccountingEnabled()) {
            accountingProcessorForLoan = this.applicationContext.getBean("accrualBasedAccountingProcessorForLoan",
                    AccountingProcessorForLoan.class);
        }
        return accountingProcessorForLoan;
    }
}
