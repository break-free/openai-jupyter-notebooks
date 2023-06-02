
package org.apache.fineract.infrastructure.survey.api;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.fineract.commands.domain.CommandWrapper;
import org.apache.fineract.commands.service.CommandWrapperBuilder;
import org.apache.fineract.commands.service.PortfolioCommandSourceWritePlatformService;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.serialization.DefaultToApiJsonSerializer;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.infrastructure.survey.data.LikelihoodData;
import org.apache.fineract.infrastructure.survey.service.ReadLikelihoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Path("/likelihood")
@Component
@Scope("singleton")
@Tag(name = "Likelihood", description = "")
public class LikelihoodApiResource {
    private final DefaultToApiJsonSerializer<LikelihoodData> toApiJsonSerializer;
    private final PlatformSecurityContext context;
    private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;
    private final ReadLikelihoodService readService;
    @Autowired
    LikelihoodApiResource(final PlatformSecurityContext context,
            final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService,
            final DefaultToApiJsonSerializer<LikelihoodData> toApiJsonSerializer, final ReadLikelihoodService readService) {
        this.context = context;
        this.commandsSourceWritePlatformService = commandsSourceWritePlatformService;
        this.toApiJsonSerializer = toApiJsonSerializer;
        this.readService = readService;
    }
    @GET
    @Path("{ppiName}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String retrieveAll(@PathParam("ppiName") final String ppiName) {
        this.context.authenticatedUser().validateHasReadPermission(PovertyLineApiConstants.POVERTY_LINE_RESOURCE_NAME);
        List<LikelihoodData> likelihoodData = this.readService.retrieveAll(ppiName);
        return this.toApiJsonSerializer.serialize(likelihoodData);
    }
    @GET
    @Path("{ppiName}/{likelihoodId}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String retrieve(@PathParam("likelihoodId") final Long likelihoodId, @PathParam("ppiName") final String ppiName) {
        this.context.authenticatedUser().validateHasReadPermission(PovertyLineApiConstants.POVERTY_LINE_RESOURCE_NAME);
        LikelihoodData likelihoodData = this.readService.retrieve(likelihoodId);
        return this.toApiJsonSerializer.serialize(likelihoodData);
    }
    @PUT
    @Path("{ppiName}/{likelihoodId}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String update(@PathParam("likelihoodId") final Long likelihoodId, final String apiRequestBodyAsJson,
            @PathParam("ppiName") final String ppiName) {
        this.context.authenticatedUser().validateHasReadPermission(PovertyLineApiConstants.POVERTY_LINE_RESOURCE_NAME);
        final CommandWrapper commandRequest = new CommandWrapperBuilder() 
                .updateLikelihood(likelihoodId) 
                .withJson(apiRequestBodyAsJson) 
                .build();
        final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);
        return this.toApiJsonSerializer.serialize(result);
    }
}
