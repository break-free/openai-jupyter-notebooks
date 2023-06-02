
package org.apache.fineract.infrastructure.creditbureau.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.infrastructure.creditbureau.domain.CreditBureau;
import org.apache.fineract.infrastructure.creditbureau.domain.CreditBureauRepository;
import org.apache.fineract.infrastructure.creditbureau.domain.OrganisationCreditBureau;
import org.apache.fineract.infrastructure.creditbureau.domain.OrganisationCreditBureauRepository;
import org.apache.fineract.infrastructure.creditbureau.serialization.CreditBureauCommandFromApiJsonDeserializer;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class OrganisationCreditBureauWritePlatflormServiceImpl implements OrganisationCreditBureauWritePlatflormService {
    private final PlatformSecurityContext context;
    private final OrganisationCreditBureauRepository organisationCreditBureauRepository;
    private final CreditBureauRepository creditBureauRepository;
    private final CreditBureauCommandFromApiJsonDeserializer fromApiJsonDeserializer;
    @Autowired
    public OrganisationCreditBureauWritePlatflormServiceImpl(final PlatformSecurityContext context,
            final OrganisationCreditBureauRepository organisationCreditBureauRepository,
            final CreditBureauRepository creditBureauRepository, final CreditBureauCommandFromApiJsonDeserializer fromApiJsonDeserializer) {
        this.context = context;
        this.organisationCreditBureauRepository = organisationCreditBureauRepository;
        this.creditBureauRepository = creditBureauRepository;
        this.fromApiJsonDeserializer = fromApiJsonDeserializer;
    }
    @Override
    public CommandProcessingResult addOrganisationCreditBureau(Long creditBureauId, JsonCommand command) {
        this.context.authenticatedUser();
        this.fromApiJsonDeserializer.validateForCreate(command.json(), creditBureauId);
        final CreditBureau creditBureau = this.creditBureauRepository.getReferenceById(creditBureauId);
        final OrganisationCreditBureau organisationCreditBureau = OrganisationCreditBureau.fromJson(command, creditBureau);
        this.organisationCreditBureauRepository.saveAndFlush(organisationCreditBureau);
        return new CommandProcessingResultBuilder().withCommandId(command.commandId()).withEntityId(organisationCreditBureau.getId())
                .build();
    }
    @Transactional
    @Override
    public CommandProcessingResult updateCreditBureau(JsonCommand command) {
        this.context.authenticatedUser();
        this.fromApiJsonDeserializer.validateForUpdate(command.json());
        final long creditbureauID = command.longValueOfParameterNamed("creditBureauId");
        final boolean isActive = command.booleanPrimitiveValueOfParameterNamed("isActive");
        final OrganisationCreditBureau orgcb = organisationCreditBureauRepository.getReferenceById(creditbureauID);
        orgcb.setIsActive(isActive);
        organisationCreditBureauRepository.saveAndFlush(orgcb);
        return new CommandProcessingResultBuilder().withCommandId(command.commandId()).withEntityId(orgcb.getId()).build();
    }
}
