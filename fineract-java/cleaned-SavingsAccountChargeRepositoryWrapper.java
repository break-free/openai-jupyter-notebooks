
package org.apache.fineract.portfolio.savings.domain;
import java.time.LocalDate;
import java.util.List;
import org.apache.fineract.portfolio.charge.exception.SavingsAccountChargeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class SavingsAccountChargeRepositoryWrapper {
    private final SavingsAccountChargeRepository repository;
    @Autowired
    public SavingsAccountChargeRepositoryWrapper(final SavingsAccountChargeRepository repository) {
        this.repository = repository;
    }
    public SavingsAccountCharge findOneWithNotFoundDetection(final Long id) {
        return this.repository.findById(id).orElseThrow(() -> new SavingsAccountChargeNotFoundException(id));
    }
    public SavingsAccountCharge findOneWithNotFoundDetection(final Long id, final Long savingsAccountId) {
        final SavingsAccountCharge savingsAccountCharge = this.repository.findByIdAndSavingsAccountId(id, savingsAccountId);
        if (savingsAccountCharge == null) {
            throw new SavingsAccountChargeNotFoundException(id);
        }
        return savingsAccountCharge;
    }
    public List<SavingsAccountCharge> findPendingCharges(final LocalDate transactionDate) {
        return this.repository.findPendingCharges(transactionDate);
    }
    public void save(final SavingsAccountCharge savingsAccountCharge) {
        this.repository.save(savingsAccountCharge);
    }
    public void save(final Iterable<SavingsAccountCharge> savingsAccountCharges) {
        this.repository.saveAll(savingsAccountCharges);
    }
    public void saveAndFlush(final SavingsAccountCharge savingsAccountCharge) {
        this.repository.saveAndFlush(savingsAccountCharge);
    }
    public void delete(final SavingsAccountCharge savingsAccountCharge) {
        this.repository.delete(savingsAccountCharge);
    }
}
