
package org.apache.fineract.useradministration.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.infrastructure.core.exception.PlatformDataIntegrityException;
import org.apache.fineract.useradministration.api.PasswordPreferencesApiConstants;
import org.apache.fineract.useradministration.data.PasswordPreferencesDataValidator;
import org.apache.fineract.useradministration.domain.PasswordValidationPolicy;
import org.apache.fineract.useradministration.domain.PasswordValidationPolicyRepository;
import org.apache.fineract.useradministration.exception.PasswordValidationPolicyNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class PasswordPreferencesWritePlatformServiceJpaRepositoryImpl implements PasswordPreferencesWritePlatformService {
    private static final Logger LOG = LoggerFactory.getLogger(PasswordPreferencesWritePlatformServiceJpaRepositoryImpl.class);
    private final PasswordValidationPolicyRepository validationRepository;
    private final PasswordPreferencesDataValidator dataValidator;
    @Autowired
    public PasswordPreferencesWritePlatformServiceJpaRepositoryImpl(final PasswordValidationPolicyRepository validationPolicyRepository,
            final PasswordPreferencesDataValidator dataValidator) {
        this.validationRepository = validationPolicyRepository;
        this.dataValidator = dataValidator;
    }
    @Transactional
    @Override
    public CommandProcessingResult updatePreferences(final JsonCommand command) {
        this.dataValidator.validateForUpdate(command.json());
        Long validationPolicyId = command.longValueOfParameterNamed(PasswordPreferencesApiConstants.VALIDATION_POLICY_ID);
        try {
            final List<PasswordValidationPolicy> validationPolicies = this.validationRepository.findAll();
            Map<String, Object> changes = new HashMap<>(1);
            boolean found = false;
            for (PasswordValidationPolicy policy : validationPolicies) {
                if (policy.getId().equals(validationPolicyId)) {
                    found = true;
                    if (!policy.isActive()) {
                        changes = policy.activate();
                    }
                } else if (policy.isActive() && !policy.getId().equals(validationPolicyId)) {
                    policy.deActivate();
                }
            }
            if (!found) {
                throw new PasswordValidationPolicyNotFoundException(validationPolicyId);
            }
            if (!changes.isEmpty()) {
                this.validationRepository.saveAll(validationPolicies);
                this.validationRepository.flush();
            }
            return new CommandProcessingResultBuilder() 
                    .withCommandId(command.commandId()) 
                    .with(changes) 
                    .build();
        } catch (final JpaSystemException | DataIntegrityViolationException dve) {
            LOG.error("Error occured.", dve);
            throw new PlatformDataIntegrityException("error.msg.password.validation.policy.unknown.data.integrity.issue",
                    "Unknown data integrity issue with resource.", dve);
        }
    }
}
