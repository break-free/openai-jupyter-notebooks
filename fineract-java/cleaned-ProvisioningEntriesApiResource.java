
package org.apache.fineract.accounting.provisioning.api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.accounting.provisioning.constant.ProvisioningEntriesApiConstants;
import org.apache.fineract.accounting.provisioning.data.LoanProductProvisioningEntryData;
import org.apache.fineract.accounting.provisioning.data.ProvisioningEntryData;
import org.apache.fineract.accounting.provisioning.service.ProvisioningEntriesReadPlatformService;
import org.apache.fineract.commands.domain.CommandWrapper;
import org.apache.fineract.commands.service.CommandWrapperBuilder;
import org.apache.fineract.commands.service.PortfolioCommandSourceWritePlatformService;
import org.apache.fineract.infrastructure.core.api.ApiRequestParameterHelper;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.exception.UnrecognizedQueryParamException;
import org.apache.fineract.infrastructure.core.serialization.ApiRequestJsonSerializationSettings;
import org.apache.fineract.infrastructure.core.serialization.DefaultToApiJsonSerializer;
import org.apache.fineract.infrastructure.core.service.Page;
import org.apache.fineract.infrastructure.core.service.SearchParameters;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Path("/provisioningentries")
@Component
@Scope("singleton")
@Tag(name = "Provisioning Entries", description = "This defines the Provisioning Entries for all active loan products\n" + "\n"
        + "Field Descriptions\n" + "date\n" + "Date on which day provisioning entries should be created\n" + "createjournalentries\n"
        + "Boolean variable whether to add journal entries for generated provisioning entries\n")
@RequiredArgsConstructor
public class ProvisioningEntriesApiResource {
    private static final Set<String> PROVISIONING_ENTRY_PARAMETERS = new HashSet<>(
            Arrays.asList(ProvisioningEntriesApiConstants.PROVISIONINGENTRY_PARAM, ProvisioningEntriesApiConstants.ENTRIES_PARAM));
    private static final Set<String> ALL_PROVISIONING_ENTRIES = new HashSet<>(
            Arrays.asList(ProvisioningEntriesApiConstants.PROVISIONINGENTRY_PARAM));
    private final PlatformSecurityContext platformSecurityContext;
    private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;
    private final DefaultToApiJsonSerializer<ProvisioningEntryData> toApiJsonSerializer;
    private final DefaultToApiJsonSerializer<Object> entriesApiJsonSerializer;
    private final ProvisioningEntriesReadPlatformService provisioningEntriesReadPlatformService;
    private final ApiRequestParameterHelper apiRequestParameterHelper;
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @Operation(summary = "Create new Provisioning Entries", description = "Creates a new Provisioning Entries\n" + "\n"
            + "Mandatory Fields\n" + "date\n" + "dateFormat\n" + "locale\n" + "Optional Fields\n" + "createjournalentries")
    @RequestBody(content = @Content(schema = @Schema(implementation = ProvisioningEntriesApiResourceSwagger.PostProvisioningEntriesRequest.class)))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ProvisioningEntriesApiResourceSwagger.PostProvisioningEntriesResponse.class))) })
    public String createProvisioningEntries(@Parameter(hidden = true) final String apiRequestBodyAsJson) {
        CommandWrapper commandWrapper = null;
        this.platformSecurityContext.authenticatedUser();
        commandWrapper = new CommandWrapperBuilder().createProvisioningEntries().withJson(apiRequestBodyAsJson).build();
        final CommandProcessingResult commandProcessingResult = this.commandsSourceWritePlatformService.logCommandSource(commandWrapper);
        return this.toApiJsonSerializer.serialize(commandProcessingResult);
    }
    @POST
    @Path("{entryId}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @Operation(summary = "Recreates Provisioning Entry", description = "Recreates Provisioning Entry | createjournalentry.")
    @RequestBody(content = @Content(schema = @Schema(implementation = ProvisioningEntriesApiResourceSwagger.PutProvisioningEntriesRequest.class)))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ProvisioningEntriesApiResourceSwagger.PutProvisioningEntriesResponse.class))) })
    public String modifyProvisioningEntry(@PathParam("entryId") @Parameter(description = "entryId") final Long entryId,
            @QueryParam("command") @Parameter(description = "command=createjournalentry\ncommand=recreateprovisioningentry") final String commandParam,
            @Parameter(hidden = true) final String apiRequestBodyAsJson) {
        CommandWrapper commandWrapper = null;
        this.platformSecurityContext.authenticatedUser();
        if ("createjournalentry".equals(commandParam)) {
            commandWrapper = new CommandWrapperBuilder().createProvisioningJournalEntries(entryId).withJson(apiRequestBodyAsJson).build();
            final CommandProcessingResult commandProcessingResult = this.commandsSourceWritePlatformService
                    .logCommandSource(commandWrapper);
            return this.toApiJsonSerializer.serialize(commandProcessingResult);
        } else if ("recreateprovisioningentry".equals(commandParam)) {
            commandWrapper = new CommandWrapperBuilder().reCreateProvisioningEntries(entryId).withJson(apiRequestBodyAsJson).build();
            final CommandProcessingResult commandProcessingResult = this.commandsSourceWritePlatformService
                    .logCommandSource(commandWrapper);
            return this.toApiJsonSerializer.serialize(commandProcessingResult);
        }
        throw new UnrecognizedQueryParamException("command", commandParam);
    }
    @GET
    @Path("{entryId}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @Operation(summary = "Retrieves a Provisioning Entry", description = "Returns the details of a generated Provisioning Entry.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ProvisioningEntryData.class))) })
    public String retrieveProvisioningEntry(@PathParam("entryId") @Parameter(description = "entryId") final Long entryId,
            @Context final UriInfo uriInfo) {
        platformSecurityContext.authenticatedUser();
        ProvisioningEntryData data = this.provisioningEntriesReadPlatformService.retrieveProvisioningEntryData(entryId);
        final ApiRequestJsonSerializationSettings settings = this.apiRequestParameterHelper.process(uriInfo.getQueryParameters());
        return this.toApiJsonSerializer.serialize(settings, data, PROVISIONING_ENTRY_PARAMETERS);
    }
    @GET
    @Path("entries")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = LoanProductProvisioningEntryData.class))) })
    public String retrieveProviioningEntries(@QueryParam("entryId") final Long entryId, @QueryParam("offset") final Integer offset,
            @QueryParam("limit") final Integer limit, @QueryParam("officeId") final Long officeId,
            @QueryParam("productId") final Long productId, @QueryParam("categoryId") final Long categoryId,
            @Context final UriInfo uriInfo) {
        this.platformSecurityContext.authenticatedUser();
        SearchParameters params = SearchParameters.forProvisioningEntries(entryId, officeId, productId, categoryId, offset, limit);
        Page<LoanProductProvisioningEntryData> entries = this.provisioningEntriesReadPlatformService.retrieveProvisioningEntries(params);
        final ApiRequestJsonSerializationSettings settings = this.apiRequestParameterHelper.process(uriInfo.getQueryParameters());
        return this.entriesApiJsonSerializer.serialize(settings, entries, PROVISIONING_ENTRY_PARAMETERS);
    }
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @Operation(summary = "List all Provisioning Entries")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProvisioningEntryData.class)))) })
    public String retrieveAllProvisioningEntries(@QueryParam("offset") @Parameter(description = "offset") final Integer offset,
            @QueryParam("limit") @Parameter(description = "limit") final Integer limit, @Context final UriInfo uriInfo) {
        platformSecurityContext.authenticatedUser();
        Page<ProvisioningEntryData> data = this.provisioningEntriesReadPlatformService.retrieveAllProvisioningEntries(offset, limit);
        final ApiRequestJsonSerializationSettings settings = this.apiRequestParameterHelper.process(uriInfo.getQueryParameters());
        return this.entriesApiJsonSerializer.serialize(settings, data, ALL_PROVISIONING_ENTRIES);
    }
}
