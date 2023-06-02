
package org.apache.fineract.mix.api;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.sql.Date;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.apache.fineract.mix.data.XBRLData;
import org.apache.fineract.mix.service.XBRLBuilder;
import org.apache.fineract.mix.service.XBRLResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Path("/mixreport")
@Component
@Scope("singleton")
@Tag(name = "Mix Report", description = "")
public class MixReportApiResource {
    private final XBRLResultService xbrlResultService;
    private final XBRLBuilder xbrlBuilder;
    @Autowired
    public MixReportApiResource(final XBRLResultService xbrlResultService, final XBRLBuilder xbrlBuilder) {
        this.xbrlResultService = xbrlResultService;
        this.xbrlBuilder = xbrlBuilder;
    }
    @GET
    @Produces({ MediaType.APPLICATION_XML })
    public String retrieveXBRLReport(@QueryParam("startDate") final Date startDate, @QueryParam("endDate") final Date endDate,
            @QueryParam("currency") final String currency) {
        final XBRLData data = this.xbrlResultService.getXBRLResult(startDate, endDate, currency);
        return this.xbrlBuilder.build(data);
    }
}
