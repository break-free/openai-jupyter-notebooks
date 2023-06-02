
package org.apache.fineract.accounting.glaccount.domain;
import java.util.List;
import org.apache.fineract.accounting.trialbalance.exception.TrialBalanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class TrialBalanceRepositoryWrapper {
    private final TrialBalanceRepository repository;
    @Autowired
    public TrialBalanceRepositoryWrapper(final TrialBalanceRepository repository) {
        this.repository = repository;
    }
    public List<TrialBalance> findNewByOfficeAndAccount(final Long officeId, final Long accountId) {
        final List<TrialBalance> trialBalanceList = this.repository.findNewByOfficeAndAccount(officeId, accountId);
        if (trialBalanceList == null) {
            throw new TrialBalanceNotFoundException(officeId, accountId);
        }
        return trialBalanceList;
    }
    public void save(final List<TrialBalance> tbRows) {
        this.repository.saveAll(tbRows);
    }
}
