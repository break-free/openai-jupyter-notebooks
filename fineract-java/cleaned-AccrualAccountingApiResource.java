
package org.apache.fineract.accounting.accrual.api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.commands.domain.CommandWrapper;
import org.apache.fineract.commands.service.CommandWrapperBuilder;
import org.apache.fineract.commands.service.PortfolioCommandSourceWritePlatformService;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.serialization.DefaultToApiJsonSerializer;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Path("/runaccruals")
@Component
@Scope("singleton")
@Tag(name = "Periodic Accrual Accounting", description = "Periodic Accrual is to accrue the loan income till the specific date or till batch job scheduled time.\n")
@RequiredArgsConstructor
public class AccrualAccountingApiResource {
    private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;
    private final DefaultToApiJsonSerializer<String> apiJsonSerializerService;
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @Operation(summary = "Executes Periodic Accrual Accounting", method = "POST", description = "Mandatory Fields\n" + "\n" + "tillDate\n")
    @RequestBody(required = true, content = @Content(schema = @Schema(implementation = AccrualAccountingApiResourceSwagger.PostRunaccrualsRequest.class)))
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "OK") })
    public String executePeriodicAccrualAccounting(@Parameter(hidden = true) final String jsonRequestBody) {
        final CommandWrapper commandRequest = new CommandWrapperBuilder().excuteAccrualAccounting().withJson(jsonRequestBody).build();
        final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);
        return this.apiJsonSerializerService.serialize(result);
    }
}
