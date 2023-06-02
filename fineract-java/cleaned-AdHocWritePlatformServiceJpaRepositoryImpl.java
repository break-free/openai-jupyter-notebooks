
package org.apache.fineract.adhocquery.service;
import java.util.Map;
import org.apache.fineract.adhocquery.domain.AdHoc;
import org.apache.fineract.adhocquery.domain.AdHocRepository;
import org.apache.fineract.adhocquery.exception.AdHocNotFoundException;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.infrastructure.core.exception.PlatformDataIntegrityException;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class AdHocWritePlatformServiceJpaRepositoryImpl implements AdHocWritePlatformService {
    private static final Logger LOG = LoggerFactory.getLogger(AdHocWritePlatformServiceJpaRepositoryImpl.class);
    private final PlatformSecurityContext context;
    private final AdHocRepository adHocRepository;
    private final AdHocDataValidator adHocCommandFromApiJsonDeserializer;
    @Autowired
    public AdHocWritePlatformServiceJpaRepositoryImpl(final PlatformSecurityContext context, final AdHocRepository adHocRepository,
            final AdHocDataValidator adHocCommandFromApiJsonDeserializer) {
        this.context = context;
        this.adHocRepository = adHocRepository;
        this.adHocCommandFromApiJsonDeserializer = adHocCommandFromApiJsonDeserializer;
    }
    @Transactional
    @Override
    public CommandProcessingResult createAdHocQuery(final JsonCommand command) {
        try {
            this.context.authenticatedUser();
            this.adHocCommandFromApiJsonDeserializer.validateForCreate(command.json());
            final AdHoc entity = AdHoc.fromJson(command);
            this.adHocRepository.saveAndFlush(entity);
            return new CommandProcessingResultBuilder().withCommandId(command.commandId()).withEntityId(entity.getId()).build();
        } catch (final JpaSystemException | DataIntegrityViolationException dve) {
            final Throwable throwable = dve.getMostSpecificCause();
            handleDataIntegrityIssues(command, throwable, dve);
            return new CommandProcessingResultBuilder() 
                    .withCommandId(command.commandId()) 
                    .build();
        }
    }
    private void handleDataIntegrityIssues(final JsonCommand command, final Throwable realCause,
            final NonTransientDataAccessException dve) {
        if (realCause.getMessage().contains("unq_name")) {
            final String name = command.stringValueOfParameterNamed("name");
            throw new PlatformDataIntegrityException("error.msg.adhocquery.duplicate.name",
                    "AdHocQuery with name `" + name + "` already exists", "name", name);
        }
        LOG.error("Error occured.", dve);
        throw new PlatformDataIntegrityException("error.msg.adhocquery.unknown.data.integrity.issue",
                "Unknown data integrity issue with resource.");
    }
    @Transactional
    @Override
    public CommandProcessingResult updateAdHocQuery(final Long adHocId, final JsonCommand command) {
        try {
            this.context.authenticatedUser();
            this.adHocCommandFromApiJsonDeserializer.validateForUpdate(command.json());
            final AdHoc adHoc = this.adHocRepository.findById(adHocId).orElseThrow(() -> new AdHocNotFoundException(adHocId));
            final Map<String, Object> changes = adHoc.update(command);
            if (!changes.isEmpty()) {
                this.adHocRepository.saveAndFlush(adHoc);
            }
            return new CommandProcessingResultBuilder() 
                    .withCommandId(command.commandId()) 
                    .withEntityId(adHocId) 
                    .with(changes) 
                    .build();
        } catch (final JpaSystemException | DataIntegrityViolationException dve) {
            final Throwable throwable = dve.getMostSpecificCause();
            handleDataIntegrityIssues(command, throwable, dve);
            return new CommandProcessingResultBuilder() 
                    .withCommandId(command.commandId()) 
                    .build();
        }
    }
    @Transactional
    @Override
    public CommandProcessingResult deleteAdHocQuery(Long adHocId) {
        try {
            final AdHoc adHoc = this.adHocRepository.findById(adHocId).orElseThrow(() -> new AdHocNotFoundException(adHocId));
            this.adHocRepository.delete(adHoc);
            return new CommandProcessingResultBuilder().withEntityId(adHocId).build();
        } catch (final JpaSystemException | DataIntegrityViolationException e) {
            throw new PlatformDataIntegrityException("error.msg.unknown.data.integrity.issue",
                    "Unknown data integrity issue with resource: " + e.getMostSpecificCause(), e);
        }
    }
    @Transactional
    @Override
    public CommandProcessingResult disableAdHocQuery(Long adHocId) {
        try {
            final AdHoc adHoc = this.adHocRepository.findById(adHocId).orElseThrow(() -> new AdHocNotFoundException(adHocId));
            adHoc.disableActive();
            this.adHocRepository.save(adHoc);
            return new CommandProcessingResultBuilder().withEntityId(adHocId).build();
        } catch (final JpaSystemException | DataIntegrityViolationException e) {
            throw new PlatformDataIntegrityException("error.msg.unknown.data.integrity.issue",
                    "Unknown data integrity issue with resource: " + e.getMostSpecificCause(), e);
        }
    }
    @Transactional
    @Override
    public CommandProcessingResult enableAdHocQuery(Long adHocId) {
        try {
            final AdHoc adHoc = this.adHocRepository.findById(adHocId).orElseThrow(() -> new AdHocNotFoundException(adHocId));
            adHoc.enableActive();
            this.adHocRepository.save(adHoc);
            return new CommandProcessingResultBuilder().withEntityId(adHocId).build();
        } catch (final JpaSystemException | DataIntegrityViolationException e) {
            throw new PlatformDataIntegrityException("error.msg.unknown.data.integrity.issue",
                    "Unknown data integrity issue with resource: " + e.getMostSpecificCause(), e);
        }
    }
}
