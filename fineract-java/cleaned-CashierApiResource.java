
package org.apache.fineract.organisation.teller.api;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.apache.fineract.infrastructure.core.serialization.DefaultToApiJsonSerializer;
import org.apache.fineract.infrastructure.core.service.DateUtils;
import org.apache.fineract.organisation.teller.data.CashierData;
import org.apache.fineract.organisation.teller.service.TellerManagementReadPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Path("cashiers")
@Component
@Scope("singleton")
@Tag(name = "Cashiers", description = "")
public class CashierApiResource {
    private final DefaultToApiJsonSerializer<CashierData> jsonSerializer;
    private final TellerManagementReadPlatformService readPlatformService;
    @Autowired
    public CashierApiResource(DefaultToApiJsonSerializer<CashierData> jsonSerializer,
            TellerManagementReadPlatformService readPlatformService) {
        this.jsonSerializer = jsonSerializer;
        this.readPlatformService = readPlatformService;
    }
    @GET
    @Consumes({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    public String getCashierData(@QueryParam("officeId") final Long officeId, @QueryParam("tellerId") final Long tellerId,
            @QueryParam("staffId") final Long staffId, @QueryParam("date") final String date) {
        final DateTimeFormatter dateFormatter = DateTimeFormatter.BASIC_ISO_DATE;
        final LocalDate dateRestriction = (date != null ? LocalDate.parse(date, dateFormatter) : DateUtils.getBusinessLocalDate());
        final Collection<CashierData> allCashiers = this.readPlatformService.getCashierData(officeId, tellerId, staffId, dateRestriction);
        return this.jsonSerializer.serialize(allCashiers);
    }
}
