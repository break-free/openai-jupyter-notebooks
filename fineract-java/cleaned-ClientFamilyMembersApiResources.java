
package org.apache.fineract.portfolio.client.api;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import org.apache.fineract.commands.domain.CommandWrapper;
import org.apache.fineract.commands.service.CommandWrapperBuilder;
import org.apache.fineract.commands.service.PortfolioCommandSourceWritePlatformService;
import org.apache.fineract.infrastructure.core.api.ApiRequestParameterHelper;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.serialization.ApiRequestJsonSerializationSettings;
import org.apache.fineract.infrastructure.core.serialization.ToApiJsonSerializer;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.portfolio.client.data.ClientFamilyMembersData;
import org.apache.fineract.portfolio.client.service.ClientFamilyMembersReadPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Path("/clients/{clientId}/familymembers")
@Component
@Scope("singleton")
@Tag(name = "Client Family Member", description = "")
public class ClientFamilyMembersApiResources {
    private final Set<String> responseDataParameters = new HashSet<>(Arrays.asList("id", "clientId", "firstName", "middleName", "lastName",
            "qualification", "relationship", "maritalStatus", "gender", "dateOfBirth", "profession", "clientFamilyMemberId"));
    private final String resourceNameForPermissions = "FamilyMembers";
    private final PlatformSecurityContext context;
    private final ClientFamilyMembersReadPlatformService readPlatformService;
    private final ToApiJsonSerializer<ClientFamilyMembersData> toApiJsonSerializer;
    private final ApiRequestParameterHelper apiRequestParameterHelper;
    private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;
    @Autowired
    public ClientFamilyMembersApiResources(final PlatformSecurityContext context,
            final ClientFamilyMembersReadPlatformService readPlatformService,
            final ToApiJsonSerializer<ClientFamilyMembersData> toApiJsonSerializer,
            final ApiRequestParameterHelper apiRequestParameterHelper,
            final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService) {
        this.context = context;
        this.readPlatformService = readPlatformService;
        this.toApiJsonSerializer = toApiJsonSerializer;
        this.apiRequestParameterHelper = apiRequestParameterHelper;
        this.commandsSourceWritePlatformService = commandsSourceWritePlatformService;
    }
    @GET
    @Path("/{familyMemberId}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String getFamilyMember(@Context final UriInfo uriInfo, @PathParam("familyMemberId") final Long familyMemberId,
            @PathParam("clientId") @Parameter(description = "clientId") final Long clientId) {
        this.context.authenticatedUser().validateHasReadPermission(this.resourceNameForPermissions);
        final ClientFamilyMembersData familyMembers = this.readPlatformService.getClientFamilyMember(familyMemberId);
        final ApiRequestJsonSerializationSettings settings = this.apiRequestParameterHelper.process(uriInfo.getQueryParameters());
        return this.toApiJsonSerializer.serialize(settings, familyMembers, this.responseDataParameters);
    }
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String getFamilyMembers(@Context final UriInfo uriInfo, @PathParam("clientId") final long clientId) {
        this.context.authenticatedUser().validateHasReadPermission(this.resourceNameForPermissions);
        final Collection<ClientFamilyMembersData> familyMembers = this.readPlatformService.getClientFamilyMembers(clientId);
        final ApiRequestJsonSerializationSettings settings = this.apiRequestParameterHelper.process(uriInfo.getQueryParameters());
        return this.toApiJsonSerializer.serialize(settings, familyMembers, this.responseDataParameters);
    }
    @GET
    @Path("/template")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String getTemplate(@Context final UriInfo uriInfo, @PathParam("clientId") final long clientId) {
        this.context.authenticatedUser().validateHasReadPermission(this.resourceNameForPermissions);
        final ClientFamilyMembersData options = this.readPlatformService.retrieveTemplate();
        final ApiRequestJsonSerializationSettings settings = this.apiRequestParameterHelper.process(uriInfo.getQueryParameters());
        return this.toApiJsonSerializer.serialize(settings, options, this.responseDataParameters);
    }
    @PUT
    @Path("/{familyMemberId}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String updateClientFamilyMembers(@PathParam("familyMemberId") final long familyMemberId, final String apiRequestBodyAsJson,
            @PathParam("clientId") @Parameter(description = "clientId") final Long clientId) {
        final CommandWrapper commandRequest = new CommandWrapperBuilder().updateFamilyMembers(familyMemberId).withJson(apiRequestBodyAsJson)
                .build();
        final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);
        return this.toApiJsonSerializer.serialize(result);
    }
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String addClientFamilyMembers(@PathParam("clientId") final long clientid, final String apiRequestBodyAsJson) {
        final CommandWrapper commandRequest = new CommandWrapperBuilder().addFamilyMembers(clientid).withJson(apiRequestBodyAsJson).build();
        final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);
        return this.toApiJsonSerializer.serialize(result);
    }
    @DELETE
    @Path("/{familyMemberId}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String deleteClientFamilyMembers(@PathParam("familyMemberId") final long familyMemberId, final String apiRequestBodyAsJson,
            @PathParam("clientId") @Parameter(description = "clientId") final Long clientId) {
        final CommandWrapper commandRequest = new CommandWrapperBuilder().deleteFamilyMembers(familyMemberId).withJson(apiRequestBodyAsJson)
                .build();
        final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);
        return this.toApiJsonSerializer.serialize(result);
    }
}
