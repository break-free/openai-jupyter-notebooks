
package org.apache.fineract.portfolio.self.spm.api;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.spm.api.SpmApiResource;
import org.apache.fineract.spm.data.SurveyData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@Path("/self/surveys")
@Component
@Scope("singleton")
@Tag(name = "Self Spm", description = "")
public class SelfSpmApiResource {
    private final PlatformSecurityContext securityContext;
    private final SpmApiResource spmApiResource;
    @Autowired
    public SelfSpmApiResource(final PlatformSecurityContext securityContext, final SpmApiResource spmApiResource) {
        this.securityContext = securityContext;
        this.spmApiResource = spmApiResource;
    }
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @Transactional
    public List<SurveyData> fetchAllSurveys() {
        securityContext.authenticatedUser();
        final Boolean isActive = true;
        return this.spmApiResource.fetchAllSurveys(isActive);
    }
}
