
package org.apache.fineract.accounting.rule.domain;
import org.apache.fineract.accounting.rule.exception.AccountingRuleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AccountingRuleRepositoryWrapper {
    private final AccountingRuleRepository repository;
    @Autowired
    public AccountingRuleRepositoryWrapper(final AccountingRuleRepository repository) {
        this.repository = repository;
    }
    public AccountingRule findOneWithNotFoundDetection(final Long id) {
        return this.repository.findById(id).orElseThrow(() -> new AccountingRuleNotFoundException(id));
    }
}
