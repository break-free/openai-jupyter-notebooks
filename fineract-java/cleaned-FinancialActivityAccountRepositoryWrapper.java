
package org.apache.fineract.accounting.financialactivityaccount.domain;
import java.util.List;
import org.apache.fineract.accounting.financialactivityaccount.exception.FinancialActivityAccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class FinancialActivityAccountRepositoryWrapper {
    private final FinancialActivityAccountRepository repository;
    @Autowired
    public FinancialActivityAccountRepositoryWrapper(final FinancialActivityAccountRepository repository) {
        this.repository = repository;
    }
    public FinancialActivityAccount findOneWithNotFoundDetection(final Long id) {
        return this.repository.findById(id).orElseThrow(() -> new FinancialActivityAccountNotFoundException(id));
    }
    public FinancialActivityAccount findByFinancialActivityTypeWithNotFoundDetection(final int financialActivityType) {
        FinancialActivityAccount financialActivityAccount = this.repository.findByFinancialActivityType(financialActivityType);
        if (financialActivityAccount == null) {
            throw new FinancialActivityAccountNotFoundException(financialActivityType);
        }
        return financialActivityAccount;
    }
    public List<FinancialActivityAccount> findAll() {
        return this.repository.findAll();
    }
    public void save(final FinancialActivityAccount entity) {
        this.repository.save(entity);
    }
    public void saveAndFlush(final FinancialActivityAccount entity) {
        this.repository.saveAndFlush(entity);
    }
    public void delete(final FinancialActivityAccount entity) {
        this.repository.delete(entity);
    }
}
