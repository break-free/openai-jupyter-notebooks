
package org.apache.fineract.spm.service;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;
import org.apache.fineract.infrastructure.core.exception.PlatformDataIntegrityException;
import org.apache.fineract.infrastructure.core.service.DateUtils;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.spm.domain.Survey;
import org.apache.fineract.spm.domain.SurveyRepository;
import org.apache.fineract.spm.domain.SurveyValidator;
import org.apache.fineract.spm.exception.SurveyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
@Service
public class SpmService {
    private final PlatformSecurityContext securityContext;
    private final SurveyRepository surveyRepository;
    private final SurveyValidator surveyValidator;
    @Autowired
    public SpmService(final PlatformSecurityContext securityContext, final SurveyRepository surveyRepository,
            final SurveyValidator surveyValidator) {
        this.securityContext = securityContext;
        this.surveyRepository = surveyRepository;
        this.surveyValidator = surveyValidator;
    }
    public List<Survey> fetchValidSurveys() {
        this.securityContext.authenticatedUser();
        return this.surveyRepository.fetchActiveSurveys(DateUtils.getLocalDateTimeOfSystem());
    }
    public List<Survey> fetchAllSurveys() {
        this.securityContext.authenticatedUser();
        return this.surveyRepository.fetchAllSurveys();
    }
    public Survey findById(final Long id) {
        this.securityContext.authenticatedUser();
        return this.surveyRepository.findById(id).orElseThrow(() -> new SurveyNotFoundException(id));
    }
    public Survey createSurvey(final Survey survey) {
        this.securityContext.authenticatedUser();
        this.surveyValidator.validate(survey);
        final Survey previousSurvey = this.surveyRepository.findByKey(survey.getKey(), DateUtils.getLocalDateTimeOfSystem());
        if (previousSurvey != null) {
            this.deactivateSurvey(previousSurvey.getId());
        }
        LocalDate validFrom = getStartOfToday();
        survey.setValidFrom(validFrom);
        survey.setValidTo(validFrom.plusYears(100));
        try {
            this.surveyRepository.saveAndFlush(survey);
        } catch (final EntityExistsException dve) {
            handleDataIntegrityIssues(dve, dve, survey.getKey());
        } catch (final DataIntegrityViolationException dve) {
            handleDataIntegrityIssues(dve.getMostSpecificCause(), dve, survey.getKey());
        } catch (final JpaSystemException dve) {
            handleDataIntegrityIssues(dve.getMostSpecificCause(), dve, survey.getKey());
        } catch (final PersistenceException dve) {
            handleDataIntegrityIssues(dve, dve, survey.getKey());
        }
        return survey;
    }
    public Survey updateSurvey(final Survey survey) {
        try {
            this.surveyValidator.validate(survey);
            this.surveyRepository.saveAndFlush(survey);
        } catch (final EntityExistsException dve) {
            handleDataIntegrityIssues(dve, dve, survey.getKey());
        } catch (final DataIntegrityViolationException dve) {
            handleDataIntegrityIssues(dve.getMostSpecificCause(), dve, survey.getKey());
        } catch (final JpaSystemException dve) {
            handleDataIntegrityIssues(dve.getMostSpecificCause(), dve, survey.getKey());
        } catch (final PersistenceException dve) {
            handleDataIntegrityIssues(dve, dve, survey.getKey());
        }
        return survey;
    }
    public void deactivateSurvey(final Long id) {
        this.securityContext.authenticatedUser();
        final Survey survey = findById(id);
        final LocalDate dateTime = getStartOfToday().minusDays(1);
        survey.setValidTo(dateTime);
        this.surveyRepository.saveAndFlush(survey);
    }
    public void activateSurvey(final Long id) {
        this.securityContext.authenticatedUser();
        final Survey survey = findById(id);
        LocalDate validFrom = getStartOfToday();
        survey.setValidFrom(validFrom);
        survey.setValidTo(validFrom.plusYears(100));
        this.surveyRepository.saveAndFlush(survey);
    }
    public static LocalDate getStartOfToday() {
        return DateUtils.getLocalDateOfTenant();
    }
    private void handleDataIntegrityIssues(final Throwable realCause, final Exception dve, String key) {
        if (realCause.getMessage().contains("m_survey_scorecards")) {
            throw new PlatformDataIntegrityException("error.msg.survey.cannot.be.modified.as.used.in.client.survey",
                    "Survey can not be edited as it is already used in client survey", "name", key);
        }
        if (realCause.getMessage().contains("key")) {
            throw new PlatformDataIntegrityException("error.msg.survey.duplicate.key", "Survey with key already exists", "name", key);
        }
        throw new PlatformDataIntegrityException("error.msg.survey.unknown.data.integrity.issue",
                "Unknown data integrity issue with resource: " + realCause.getMessage());
    }
}
