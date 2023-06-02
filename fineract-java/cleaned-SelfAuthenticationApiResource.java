
package org.apache.fineract.portfolio.self.security.api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.fineract.infrastructure.security.api.AuthenticationApiResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Scope("singleton")
@ConditionalOnProperty("fineract.security.basicauth.enabled")
@Path("/self/authentication")
@Tag(name = "Self Authentication", description = "Authenticates the credentials provided and returns the set roles and permissions allowed")
public class SelfAuthenticationApiResource {
    private final AuthenticationApiResource authenticationApiResource;
    @Autowired
    public SelfAuthenticationApiResource(final AuthenticationApiResource authenticationApiResource) {
        this.authenticationApiResource = authenticationApiResource;
    }
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @Operation(summary = "Verify authentication", description = "Authenticates the credentials provided and returns the set roles and permissions allowed.\n\n"
            + "Please visit this link for more info - https:
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = SelfAuthenticationApiResourceSwagger.PostSelfAuthenticationResponse.class))) })
    public String authenticate(final String apiRequestBodyAsJson) {
        return this.authenticationApiResource.authenticate(apiRequestBodyAsJson, true);
    }
}
