
package org.apache.fineract.accounting.accrual.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface AccrualAccountingWritePlatformService {
    CommandProcessingResult executeLoansPeriodicAccrual(JsonCommand command);
}
