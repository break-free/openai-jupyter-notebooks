
package org.apache.fineract.organisation.workingdays.service;
import java.text.ParseException;
import java.util.Map;
import net.fortuna.ical4j.model.property.RRule;
import net.fortuna.ical4j.validate.ValidationException;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.infrastructure.core.exception.PlatformDataIntegrityException;
import org.apache.fineract.organisation.workingdays.api.WorkingDaysApiConstants;
import org.apache.fineract.organisation.workingdays.data.WorkingDayValidator;
import org.apache.fineract.organisation.workingdays.domain.WorkingDays;
import org.apache.fineract.organisation.workingdays.domain.WorkingDaysRepositoryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class WorkingDaysWritePlatformServiceJpaRepositoryImpl implements WorkingDaysWritePlatformService {
    private final WorkingDaysRepositoryWrapper daysRepositoryWrapper;
    private final WorkingDayValidator fromApiJsonDeserializer;
    @Autowired
    public WorkingDaysWritePlatformServiceJpaRepositoryImpl(final WorkingDaysRepositoryWrapper daysRepositoryWrapper,
            final WorkingDayValidator fromApiJsonDeserializer) {
        this.daysRepositoryWrapper = daysRepositoryWrapper;
        this.fromApiJsonDeserializer = fromApiJsonDeserializer;
    }
    @Transactional
    @Override
    public CommandProcessingResult updateWorkingDays(JsonCommand command) {
        String recurrence = "";
        RRule rrule = null;
        try {
            this.fromApiJsonDeserializer.validateForUpdate(command.json());
            final WorkingDays workingDays = this.daysRepositoryWrapper.findOne();
            recurrence = command.stringValueOfParameterNamed(WorkingDaysApiConstants.recurrence);
            rrule = new RRule(recurrence);
            rrule.validate();
            Map<String, Object> changes = workingDays.update(command);
            this.daysRepositoryWrapper.saveAndFlush(workingDays);
            return new CommandProcessingResultBuilder().withCommandId(command.commandId()).withEntityId(workingDays.getId()).with(changes)
                    .build();
        } catch (final ValidationException e) {
            throw new PlatformDataIntegrityException("error.msg.invalid.recurring.rule",
                    "The Recurring Rule value: " + recurrence + " is not valid.", "recurrence", recurrence, e);
        } catch (final IllegalArgumentException | ParseException e) {
            throw new PlatformDataIntegrityException("error.msg.recurring.rule.parsing.error",
                    "Error in passing the Recurring Rule value: " + recurrence, "recurrence", e.getMessage(), e);
        }
    }
}
