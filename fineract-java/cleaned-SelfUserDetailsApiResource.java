
package org.apache.fineract.portfolio.self.security.api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.fineract.infrastructure.security.api.UserDetailsApiResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Path("/self/userdetails")
@Component
@ConditionalOnProperty("fineract.security.oauth.enabled")
@Scope("singleton")
@Tag(name = "Self User Details", description = "")
public class SelfUserDetailsApiResource {
    private final UserDetailsApiResource userDetailsApiResource;
    @Autowired
    public SelfUserDetailsApiResource(final UserDetailsApiResource userDetailsApiResource) {
        this.userDetailsApiResource = userDetailsApiResource;
    }
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    @Operation(summary = "Fetch authenticated user details", description = "Checks the Authentication and returns the set roles and permissions allowed\n\n"
            + "For more info visit this link - https:
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = SelfUserDetailsApiResourceSwagger.GetSelfUserDetailsResponse.class))) })
    public String fetchAuthenticatedUserData() {
        return this.userDetailsApiResource.fetchAuthenticatedUserData();
    }
}
