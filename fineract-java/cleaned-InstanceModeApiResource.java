
package org.apache.fineract.infrastructure.instancemode.api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.fineract.infrastructure.core.config.FineractProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
@Profile("test")
@Component
@Path("/instance-mode")
@Tag(name = "Instance Mode", description = "Instance mode changing API")
@RequiredArgsConstructor
@Slf4j
public class InstanceModeApiResource implements InitializingBean {
    private final FineractProperties fineractProperties;
    @Override
    public void afterPropertiesSet() throws Exception {
        log.warn("------------------------------------------------------------");
        log.warn("                                                            ");
        log.warn("DO NOT USE THIS IN PRODUCTION!");
        log.warn("Instance type changing feature is enabled");
        log.warn("DO NOT USE THIS IN PRODUCTION!");
        log.warn("                                                            ");
        log.warn("------------------------------------------------------------");
    }
    @PUT
    @Consumes({ MediaType.APPLICATION_JSON })
    @Operation(summary = "Changes the Fineract instance mode", description = "")
    @RequestBody(required = true, content = @Content(schema = @Schema(implementation = InstanceModeApiResourceSwagger.ChangeInstanceModeRequest.class)))
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "OK") })
    public Response changeMode(InstanceModeApiResourceSwagger.ChangeInstanceModeRequest request) {
        log.warn("------------------------------------------------------------");
        log.warn("                                                            ");
        log.warn("Changing instance mode according to the request parameters {}", request);
        log.warn("                                                            ");
        log.warn("------------------------------------------------------------");
        fineractProperties.getMode().setReadEnabled(request.isReadEnabled());
        fineractProperties.getMode().setWriteEnabled(request.isWriteEnabled());
        fineractProperties.getMode().setBatchWorkerEnabled(request.isBatchWorkerEnabled());
        fineractProperties.getMode().setBatchManagerEnabled(request.isBatchManagerEnabled());
        return Response.ok().build();
    }
}
