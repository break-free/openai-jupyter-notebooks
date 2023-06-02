
package org.apache.fineract.organisation.monetary.api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
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
import org.apache.fineract.infrastructure.core.serialization.DefaultToApiJsonSerializer;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.organisation.monetary.data.ApplicationCurrencyConfigurationData;
import org.apache.fineract.organisation.monetary.service.OrganisationCurrencyReadPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Path("/currencies")
@Component
@Scope("singleton")
@Tag(name = "Currency", description = "Application related configuration around viewing/updating the currencies permitted for use within the MFI.")
public class CurrenciesApiResource {
    private final Set<String> responseDataParameters = new HashSet<>(Arrays.asList("selectedCurrencyOptions", "currencyOptions"));
    private final String resourceNameForPermissions = "CURRENCY";
    private final PlatformSecurityContext context;
    private final OrganisationCurrencyReadPlatformService readPlatformService;
    private final DefaultToApiJsonSerializer<ApplicationCurrencyConfigurationData> toApiJsonSerializer;
    private final ApiRequestParameterHelper apiRequestParameterHelper;
    private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;
    @Autowired
    public CurrenciesApiResource(final PlatformSecurityContext context, final OrganisationCurrencyReadPlatformService readPlatformService,
            final DefaultToApiJsonSerializer<ApplicationCurrencyConfigurationData> toApiJsonSerializer,
            final ApiRequestParameterHelper apiRequestParameterHelper,
            final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService) {
        this.context = context;
        this.readPlatformService = readPlatformService;
        this.toApiJsonSerializer = toApiJsonSerializer;
        this.apiRequestParameterHelper = apiRequestParameterHelper;
        this.commandsSourceWritePlatformService = commandsSourceWritePlatformService;
    }
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @Operation(summary = "Retrieve Currency Configuration", description = "Returns the list of currencies permitted for use AND the list of currencies not selected (but available for selection).\n"
            + "\n" + "Example Requests:\n" + "\n" + "currencies\n" + "\n" + "\n" + "currencies?fields=selectedCurrencyOptions")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = CurrenciesApiResourceSwagger.GetCurrenciesResponse.class))) })
    public String retrieveCurrencies(@Context final UriInfo uriInfo) {
        this.context.authenticatedUser().validateHasReadPermission(this.resourceNameForPermissions);
        final ApplicationCurrencyConfigurationData configurationData = this.readPlatformService.retrieveCurrencyConfiguration();
        final ApiRequestJsonSerializationSettings settings = this.apiRequestParameterHelper.process(uriInfo.getQueryParameters());
        return this.toApiJsonSerializer.serialize(settings, configurationData, this.responseDataParameters);
    }
    @PUT
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @Operation(summary = "Update Currency Configuration", description = "Updates the list of currencies permitted for use.")
    @RequestBody(required = true, content = @Content(schema = @Schema(implementation = CurrenciesApiResourceSwagger.PutCurrenciesRequest.class)))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = CurrenciesApiResourceSwagger.PutCurrenciesResponse.class))) })
    public String updateCurrencies(@Parameter(hidden = true) final String apiRequestBodyAsJson) {
        final CommandWrapper commandRequest = new CommandWrapperBuilder() 
                .updateCurrencies() 
                .withJson(apiRequestBodyAsJson) 
                .build();
        final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);
        return this.toApiJsonSerializer.serialize(result);
    }
}
