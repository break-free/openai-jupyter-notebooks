
package org.apache.fineract.accounting.closure.service;
import java.time.LocalDate;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.fineract.accounting.closure.api.GLClosureJsonInputParams;
import org.apache.fineract.accounting.closure.command.GLClosureCommand;
import org.apache.fineract.accounting.closure.domain.GLClosure;
import org.apache.fineract.accounting.closure.domain.GLClosureRepository;
import org.apache.fineract.accounting.closure.exception.GLClosureDuplicateException;
import org.apache.fineract.accounting.closure.exception.GLClosureInvalidDeleteException;
import org.apache.fineract.accounting.closure.exception.GLClosureInvalidException;
import org.apache.fineract.accounting.closure.exception.GLClosureInvalidException.GlClosureInvalidReason;
import org.apache.fineract.accounting.closure.exception.GLClosureNotFoundException;
import org.apache.fineract.accounting.closure.serialization.GLClosureCommandFromApiJsonDeserializer;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.infrastructure.core.exception.PlatformDataIntegrityException;
import org.apache.fineract.infrastructure.core.service.DateUtils;
import org.apache.fineract.organisation.office.domain.Office;
import org.apache.fineract.organisation.office.domain.OfficeRepositoryWrapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
@Slf4j
public class GLClosureWritePlatformServiceJpaRepositoryImpl implements GLClosureWritePlatformService {
    private final GLClosureRepository glClosureRepository;
    private final OfficeRepositoryWrapper officeRepositoryWrapper;
    private final GLClosureCommandFromApiJsonDeserializer fromApiJsonDeserializer;
    @Transactional
    @Override
    public CommandProcessingResult createGLClosure(final JsonCommand command) {
        try {
            final GLClosureCommand closureCommand = this.fromApiJsonDeserializer.commandFromApiJson(command.json());
            closureCommand.validateForCreate();
            final Long officeId = command.longValueOfParameterNamed(GLClosureJsonInputParams.OFFICE_ID.getValue());
            final Office office = this.officeRepositoryWrapper.findOneWithNotFoundDetection(officeId);
            final LocalDate todaysDate = DateUtils.getBusinessLocalDate();
            final LocalDate closureDate = command.localDateValueOfParameterNamed(GLClosureJsonInputParams.CLOSING_DATE.getValue());
            if (closureDate.isAfter(todaysDate)) {
                throw new GLClosureInvalidException(GlClosureInvalidReason.FUTURE_DATE, closureDate);
            }
            final GLClosure latestGLClosure = this.glClosureRepository.getLatestGLClosureByBranch(officeId);
            if (latestGLClosure != null) {
                if (latestGLClosure.getClosingDate().isAfter(closureDate)) {
                    throw new GLClosureInvalidException(GlClosureInvalidReason.ACCOUNTING_CLOSED, latestGLClosure.getClosingDate());
                }
            }
            final GLClosure glClosure = GLClosure.fromJson(office, command);
            this.glClosureRepository.saveAndFlush(glClosure);
            return new CommandProcessingResultBuilder().withCommandId(command.commandId()).withOfficeId(officeId)
                    .withEntityId(glClosure.getId()).build();
        } catch (final JpaSystemException | DataIntegrityViolationException dve) {
            final Throwable throwable = dve.getMostSpecificCause();
            handleGLClosureIntegrityIssues(command, throwable, dve);
            return CommandProcessingResult.empty();
        }
    }
    @Transactional
    @Override
    public CommandProcessingResult updateGLClosure(final Long glClosureId, final JsonCommand command) {
        final GLClosureCommand closureCommand = this.fromApiJsonDeserializer.commandFromApiJson(command.json());
        closureCommand.validateForUpdate();
        final GLClosure glClosure = this.glClosureRepository.findById(glClosureId)
                .orElseThrow(() -> new GLClosureNotFoundException(glClosureId));
        final Map<String, Object> changesOnly = glClosure.update(command);
        if (!changesOnly.isEmpty()) {
            this.glClosureRepository.saveAndFlush(glClosure);
        }
        return new CommandProcessingResultBuilder().withCommandId(command.commandId()).withOfficeId(glClosure.getOffice().getId())
                .withEntityId(glClosure.getId()).with(changesOnly).build();
    }
    @Transactional
    @Override
    public CommandProcessingResult deleteGLClosure(final Long glClosureId) {
        final GLClosure glClosure = this.glClosureRepository.findById(glClosureId)
                .orElseThrow(() -> new GLClosureNotFoundException(glClosureId));
        final LocalDate closureDate = glClosure.getClosingDate();
        final GLClosure latestGLClosure = this.glClosureRepository.getLatestGLClosureByBranch(glClosure.getOffice().getId());
        if (latestGLClosure.getClosingDate().isAfter(closureDate)) {
            throw new GLClosureInvalidDeleteException(latestGLClosure.getOffice().getId(), latestGLClosure.getOffice().getName(),
                    latestGLClosure.getClosingDate());
        }
        this.glClosureRepository.delete(glClosure);
        return new CommandProcessingResultBuilder().withOfficeId(glClosure.getOffice().getId()).withEntityId(glClosure.getId()).build();
    }
    private void handleGLClosureIntegrityIssues(final JsonCommand command, final Throwable realCause, NonTransientDataAccessException dve) {
        if (realCause.getMessage().contains("office_id_closing_date")) {
            throw new GLClosureDuplicateException(command.longValueOfParameterNamed(GLClosureJsonInputParams.OFFICE_ID.getValue()),
                    command.localDateValueOfParameterNamed(GLClosureJsonInputParams.CLOSING_DATE.getValue()));
        }
        log.error("Error occured.", dve);
        throw new PlatformDataIntegrityException("error.msg.glClosure.unknown.data.integrity.issue",
                "Unknown data integrity issue with resource GL Closure: " + realCause.getMessage());
    }
}
