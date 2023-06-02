
package org.apache.fineract.infrastructure.businessdate.api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.commands.domain.CommandWrapper;
import org.apache.fineract.commands.service.CommandWrapperBuilder;
import org.apache.fineract.commands.service.PortfolioCommandSourceWritePlatformService;
import org.apache.fineract.infrastructure.businessdate.data.BusinessDateData;
import org.apache.fineract.infrastructure.businessdate.service.BusinessDateReadPlatformService;
import org.apache.fineract.infrastructure.core.api.ApiRequestParameterHelper;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.serialization.ApiRequestJsonSerializationSettings;
import org.apache.fineract.infrastructure.core.serialization.DefaultToApiJsonSerializer;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.stereotype.Component;
@RequiredArgsConstructor
@Path("businessdate")
@Component
@Tag(name = "Business Date Management", description = "Business date management enables you to set up, fetch and adjust organisation business dates")
public class BusinessDateApiResource {
    private final ApiRequestParameterHelper parameterHelper;
    private final PlatformSecurityContext securityContext;
    private final DefaultToApiJsonSerializer<BusinessDateData> jsonSerializer;
    private final BusinessDateReadPlatformService readPlatformService;
    private final PortfolioCommandSourceWritePlatformService commandWritePlatformService;
    @GET
    @Consumes({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "List all business dates", description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = BusinessDateApiResourceSwagger.BusinessDateResponse.class)))) })
    public String getBusinessDates(@Context final UriInfo uriInfo) {
        securityContext.authenticatedUser().validateHasReadPermission("BUSINESS_DATE");
        final List<BusinessDateData> foundBusinessDates = this.readPlatformService.findAll();
        ApiRequestJsonSerializationSettings settings = parameterHelper.process(uriInfo.getQueryParameters());
        return this.jsonSerializer.serialize(settings, foundBusinessDates);
    }
    @GET
    @Path("{type}")
    @Consumes({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Retrieve a specific Business date", description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = BusinessDateApiResourceSwagger.BusinessDateResponse.class))) })
    public String getBusinessDate(@PathParam("type") @Parameter(description = "type") final String type, @Context final UriInfo uriInfo) {
        securityContext.authenticatedUser().validateHasReadPermission("BUSINESS_DATE");
        final BusinessDateData businessDate = this.readPlatformService.findByType(type);
        ApiRequestJsonSerializationSettings settings = parameterHelper.process(uriInfo.getQueryParameters());
        return this.jsonSerializer.serialize(settings, businessDate);
    }
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @Operation(summary = "Update Business Date", description = "")
    @RequestBody(required = true, content = @Content(schema = @Schema(implementation = BusinessDateApiResourceSwagger.BusinessDateRequest.class)))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = BusinessDateApiResourceSwagger.BusinessDateRequest.class))) })
    public String updateBusinessDate(final String jsonRequestBody, @Context UriInfo uriInfo) {
        securityContext.authenticatedUser().validateHasUpdatePermission("BUSINESS_DATE");
        final CommandWrapper commandRequest = new CommandWrapperBuilder().updateBusinessDate().withJson(jsonRequestBody).build();
        CommandProcessingResult result = commandWritePlatformService.logCommandSource(commandRequest);
        return jsonSerializer.serialize(result);
    }
}
