
package org.apache.fineract.portfolio.account.api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.apache.fineract.commands.domain.CommandWrapper;
import org.apache.fineract.commands.service.CommandWrapperBuilder;
import org.apache.fineract.commands.service.PortfolioCommandSourceWritePlatformService;
import org.apache.fineract.infrastructure.core.api.ApiRequestParameterHelper;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.serialization.ApiRequestJsonSerializationSettings;
import org.apache.fineract.infrastructure.core.serialization.DefaultToApiJsonSerializer;
import org.apache.fineract.infrastructure.core.service.Page;
import org.apache.fineract.infrastructure.core.service.SearchParameters;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.portfolio.account.data.AccountTransferData;
import org.apache.fineract.portfolio.account.service.AccountTransfersReadPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Path("/accounttransfers")
@Component
@Scope("singleton")
@Tag(name = "Account Transfers", description = "Ability to be able to transfer monetary funds from one account to another.\n\nNote: At present only savings account to savings account transfers are supported.")
public class AccountTransfersApiResource {
    private final PlatformSecurityContext context;
    private final DefaultToApiJsonSerializer<AccountTransferData> toApiJsonSerializer;
    private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;
    private final ApiRequestParameterHelper apiRequestParameterHelper;
    private final AccountTransfersReadPlatformService accountTransfersReadPlatformService;
    @Autowired
    public AccountTransfersApiResource(final PlatformSecurityContext context,
            final DefaultToApiJsonSerializer<AccountTransferData> toApiJsonSerializer,
            final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService,
            final ApiRequestParameterHelper apiRequestParameterHelper,
            final AccountTransfersReadPlatformService accountTransfersReadPlatformService) {
        this.context = context;
        this.toApiJsonSerializer = toApiJsonSerializer;
        this.commandsSourceWritePlatformService = commandsSourceWritePlatformService;
        this.apiRequestParameterHelper = apiRequestParameterHelper;
        this.accountTransfersReadPlatformService = accountTransfersReadPlatformService;
    }
    @GET
    @Path("template")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @Operation(summary = "Retrieve Account Transfer Template", description = "This is a convenience resource. It can be useful when building maintenance user interface screens for client applications. The template data returned consists of any or all of:\n\n"
            + "\n\n" + "Field Defaults\n\n" + "Allowed Value Lists\n\n" + "Example Requests:\n\n" + "\n\n"
            + "accounttransfers/template?fromAccountType=2&fromOfficeId=1\n\n" + "\n\n"
            + "accounttransfers/template?fromAccountType=2&fromOfficeId=1&fromClientId=1\n\n" + "\n\n"
            + "accounttransfers/template?fromClientId=1&fromAccountType=2&fromAccountId=1")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = AccountTransfersApiResourceSwagger.GetAccountTransfersTemplateResponse.class))) })
    public String template(@QueryParam("fromOfficeId") @Parameter(description = "fromOfficeId") final Long fromOfficeId,
            @QueryParam("fromClientId") @Parameter(description = "fromClientId") final Long fromClientId,
            @QueryParam("fromAccountId") @Parameter(description = "fromAccountId") final Long fromAccountId,
            @QueryParam("fromAccountType") @Parameter(description = "fromAccountType") final Integer fromAccountType,
            @QueryParam("toOfficeId") @Parameter(description = "toOfficeId") final Long toOfficeId,
            @QueryParam("toClientId") @Parameter(description = "toClientId") final Long toClientId,
            @QueryParam("toAccountId") @Parameter(description = "toAccountId") final Long toAccountId,
            @QueryParam("toAccountType") @Parameter(description = "toAccountType") final Integer toAccountType,
            @Context final UriInfo uriInfo) {
        this.context.authenticatedUser().validateHasReadPermission(AccountTransfersApiConstants.ACCOUNT_TRANSFER_RESOURCE_NAME);
        final AccountTransferData transferData = this.accountTransfersReadPlatformService.retrieveTemplate(fromOfficeId, fromClientId,
                fromAccountId, fromAccountType, toOfficeId, toClientId, toAccountId, toAccountType);
        final ApiRequestJsonSerializationSettings settings = this.apiRequestParameterHelper.process(uriInfo.getQueryParameters());
        return this.toApiJsonSerializer.serialize(settings, transferData, AccountTransfersApiConstants.RESPONSE_DATA_PARAMETERS);
    }
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @Operation(summary = "Create new Transfer", description = "Ability to create new transfer of monetary funds from one account to another.")
    @RequestBody(required = true, content = @Content(schema = @Schema(implementation = AccountTransfersApiResourceSwagger.PostAccountTransfersRequest.class)))
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = AccountTransfersApiResourceSwagger.PostAccountTransfersResponse.class)))
    public String create(@Parameter(hidden = true) final String apiRequestBodyAsJson) {
        final CommandWrapper commandRequest = new CommandWrapperBuilder().createAccountTransfer().withJson(apiRequestBodyAsJson).build();
        final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);
        return this.toApiJsonSerializer.serialize(result);
    }
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @Operation(summary = "List account transfers", description = "Lists account's transfers\n\n" + "Example Requests:\n\n" + "\n\n"
            + "accounttransfers")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = AccountTransfersApiResourceSwagger.GetAccountTransfersResponse.class))) })
    public String retrieveAll(@Context final UriInfo uriInfo,
            @QueryParam("sqlSearch") @Parameter(description = "sqlSearch") final String sqlSearch,
            @QueryParam("externalId") @Parameter(description = "externalId") final String externalId,
            @QueryParam("offset") @Parameter(description = "offset") final Integer offset,
            @QueryParam("limit") @Parameter(example = "limit") final Integer limit,
            @QueryParam("orderBy") @Parameter(description = "orderBy") final String orderBy,
            @QueryParam("sortOrder") @Parameter(description = "sortOrder") final String sortOrder,
            @QueryParam("accountDetailId") @Parameter(description = "accountDetailId") final Long accountDetailId) {
        this.context.authenticatedUser().validateHasReadPermission(AccountTransfersApiConstants.ACCOUNT_TRANSFER_RESOURCE_NAME);
        final SearchParameters searchParameters = SearchParameters.forAccountTransfer(sqlSearch, externalId, offset, limit, orderBy,
                sortOrder);
        final Page<AccountTransferData> transfers = this.accountTransfersReadPlatformService.retrieveAll(searchParameters, accountDetailId);
        final ApiRequestJsonSerializationSettings settings = this.apiRequestParameterHelper.process(uriInfo.getQueryParameters());
        return this.toApiJsonSerializer.serialize(settings, transfers, AccountTransfersApiConstants.RESPONSE_DATA_PARAMETERS);
    }
    @GET
    @Path("{transferId}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @Operation(summary = "Retrieve account transfer", description = "Retrieves account transfer\n\n" + "Example Requests :\n\n" + "\n\n"
            + "accounttransfers/1")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = AccountTransfersApiResourceSwagger.GetAccountTransfersResponse.GetAccountTransfersPageItems.class))) })
    public String retrieveOne(@PathParam("transferId") @Parameter(description = "transferId") final Long transferId,
            @Context final UriInfo uriInfo) {
        this.context.authenticatedUser().validateHasReadPermission(AccountTransfersApiConstants.ACCOUNT_TRANSFER_RESOURCE_NAME);
        final AccountTransferData transfer = this.accountTransfersReadPlatformService.retrieveOne(transferId);
        final ApiRequestJsonSerializationSettings settings = this.apiRequestParameterHelper.process(uriInfo.getQueryParameters());
        return this.toApiJsonSerializer.serialize(settings, transfer, AccountTransfersApiConstants.RESPONSE_DATA_PARAMETERS);
    }
    @GET
    @Path("templateRefundByTransfer")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @Operation(summary = "Retrieve Refund of an Active Loan by Transfer Template", description = "Retrieves Refund of an Active Loan by Transfer Template"
            + "Example Requests :\n\n" + "\n\n"
            + "accounttransfers/templateRefundByTransfer?fromAccountId=2&fromAccountType=1& toAccountId=1&toAccountType=2&toClientId=1&toOfficeId=1")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = AccountTransfersApiResourceSwagger.GetAccountTransfersTemplateRefundByTransferResponse.class))) })
    public String templateRefundByTransfer(@QueryParam("fromOfficeId") @Parameter(description = "fromOfficeId") final Long fromOfficeId,
            @QueryParam("fromClientId") @Parameter(description = "fromClientId") final Long fromClientId,
            @QueryParam("fromAccountId") @Parameter(description = "fromAccountId") final Long fromAccountId,
            @QueryParam("fromAccountType") @Parameter(description = "fromAccountType") final Integer fromAccountType,
            @QueryParam("toOfficeId") @Parameter(description = "toOfficeId") final Long toOfficeId,
            @QueryParam("toClientId") @Parameter(description = "toClientId") final Long toClientId,
            @QueryParam("toAccountId") @Parameter(description = "toAccountId") final Long toAccountId,
            @QueryParam("toAccountType") @Parameter(description = "toAccountType") final Integer toAccountType,
            @Context final UriInfo uriInfo) {
        this.context.authenticatedUser().validateHasReadPermission(AccountTransfersApiConstants.ACCOUNT_TRANSFER_RESOURCE_NAME);
        final AccountTransferData transferData = this.accountTransfersReadPlatformService.retrieveRefundByTransferTemplate(fromOfficeId,
                fromClientId, fromAccountId, fromAccountType, toOfficeId, toClientId, toAccountId, toAccountType);
        final ApiRequestJsonSerializationSettings settings = this.apiRequestParameterHelper.process(uriInfo.getQueryParameters());
        return this.toApiJsonSerializer.serialize(settings, transferData, AccountTransfersApiConstants.RESPONSE_DATA_PARAMETERS);
    }
    @POST
    @Path("refundByTransfer")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @Operation(summary = "Refund of an Active Loan by Transfer", description = "Ability to refund an active loan by transferring to a savings account.")
    @RequestBody(required = true, content = @Content(schema = @Schema(implementation = AccountTransfersApiResourceSwagger.PostAccountTransfersRefundByTransferRequest.class)))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = AccountTransfersApiResourceSwagger.PostAccountTransfersRefundByTransferResponse.class))) })
    public String templateRefundByTransferPost(@Parameter(hidden = true) final String apiRequestBodyAsJson) {
        final CommandWrapper commandRequest = new CommandWrapperBuilder().refundByTransfer().withJson(apiRequestBodyAsJson).build();
        final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);
        return this.toApiJsonSerializer.serialize(result);
    }
}
