
package org.apache.fineract.infrastructure.survey.service;
import java.util.List;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.infrastructure.core.exception.PlatformDataIntegrityException;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.infrastructure.survey.data.LikelihoodDataValidator;
import org.apache.fineract.infrastructure.survey.domain.Likelihood;
import org.apache.fineract.infrastructure.survey.domain.LikelihoodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
@Service
public class WriteLikelihoodServiceImpl implements WriteLikelihoodService {
    private static final Logger LOG = LoggerFactory.getLogger(WriteLikelihoodServiceImpl.class);
    private final PlatformSecurityContext context;
    private final LikelihoodDataValidator likelihoodDataValidator;
    private final LikelihoodRepository repository;
    @Autowired
    WriteLikelihoodServiceImpl(final PlatformSecurityContext context, final LikelihoodDataValidator likelihoodDataValidator,
            final LikelihoodRepository repository) {
        this.context = context;
        this.likelihoodDataValidator = likelihoodDataValidator;
        this.repository = repository;
    }
    @Override
    public CommandProcessingResult update(Long likelihoodId, JsonCommand command) {
        this.context.authenticatedUser();
        try {
            this.likelihoodDataValidator.validateForUpdate(command);
            final Likelihood likelihood = this.repository.findById(likelihoodId).orElse(null);
            if (!likelihood.update(command).isEmpty()) {
                this.repository.save(likelihood);
                if (likelihood.isActivateCommand(command)) {
                    List<Likelihood> likelihoods = this.repository.findByPpiNameAndLikeliHoodId(likelihood.getPpiName(),
                            likelihood.getId());
                    for (Likelihood aLikelihood : likelihoods) {
                        aLikelihood.disable();
                    }
                    this.repository.saveAll(likelihoods);
                }
            }
            return new CommandProcessingResultBuilder().withCommandId(command.commandId()).withEntityId(likelihood.getId()).build();
        } catch (final JpaSystemException | DataIntegrityViolationException dve) {
            final Throwable throwable = dve.getMostSpecificCause();
            handleDataIntegrityIssues(throwable, dve);
            return CommandProcessingResult.empty();
        }
    }
    private void handleDataIntegrityIssues(final Throwable realCause, final NonTransientDataAccessException dve) {
        LOG.error("Error occured.", dve);
        throw new PlatformDataIntegrityException("error.msg.likelihood.unknown.data.integrity.issue",
                "Unknown data integrity issue with resource: " + realCause.getMessage());
    }
}
