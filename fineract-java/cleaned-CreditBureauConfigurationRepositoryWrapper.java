
package org.apache.fineract.infrastructure.creditbureau.domain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CreditBureauConfigurationRepositoryWrapper {
    private final CreditBureauConfigurationRepository creditBureauConfigurationRepository;
    @Autowired
    public CreditBureauConfigurationRepositoryWrapper(final CreditBureauConfigurationRepository creditBureauConfigurationRepository) {
        this.creditBureauConfigurationRepository = creditBureauConfigurationRepository;
    }
    public CreditBureauConfiguration getCreditBureauConfigData(final Integer creditBureauID, final String parameterName) {
        return this.creditBureauConfigurationRepository.getCreditBureauConfigData(creditBureauID, parameterName);
    }
}
