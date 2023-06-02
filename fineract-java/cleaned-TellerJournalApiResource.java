
package org.apache.fineract.organisation.teller.api;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Collection;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.apache.fineract.infrastructure.core.serialization.DefaultToApiJsonSerializer;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.organisation.teller.data.TellerData;
import org.apache.fineract.organisation.teller.data.TellerJournalData;
import org.apache.fineract.organisation.teller.service.TellerManagementReadPlatformService;
import org.apache.fineract.organisation.teller.util.DateRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Path("cashiersjournal")
@Component
@Scope("singleton")
@Tag(name = "Cashier Journals", description = "")
public class TellerJournalApiResource {
    private final PlatformSecurityContext securityContext;
    private final DefaultToApiJsonSerializer<TellerData> jsonSerializer;
    private final TellerManagementReadPlatformService readPlatformService;
    @Autowired
    public TellerJournalApiResource(final PlatformSecurityContext securityContext,
            final DefaultToApiJsonSerializer<TellerData> jsonSerializer, final TellerManagementReadPlatformService readPlatformService) {
        this.securityContext = securityContext;
        this.jsonSerializer = jsonSerializer;
        this.readPlatformService = readPlatformService;
    }
    @GET
    @Consumes({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    public String getJournalData(@QueryParam("officeId") final Long officeId, @QueryParam("tellerId") final Long tellerId,
            @QueryParam("cashierId") final Long cashierId, @QueryParam("dateRange") final String dateRange) {
        final DateRange dateRangeHolder = DateRange.fromString(dateRange);
        final Collection<TellerJournalData> journals = this.readPlatformService.getJournals(officeId, tellerId, cashierId,
                dateRangeHolder.getStartDate(), dateRangeHolder.getEndDate());
        return this.jsonSerializer.serialize(journals);
    }
}
