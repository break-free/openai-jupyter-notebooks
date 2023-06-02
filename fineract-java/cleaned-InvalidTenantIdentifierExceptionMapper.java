
package org.apache.fineract.infrastructure.core.exceptionmapper;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.apache.fineract.infrastructure.core.data.ApiGlobalErrorResponse;
import org.apache.fineract.infrastructure.security.exception.InvalidTenantIdentifierException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Provider
@Component
@Scope("singleton")
@Slf4j
public class InvalidTenantIdentifierExceptionMapper implements ExceptionMapper<InvalidTenantIdentifierException> {
    @Override
    public Response toResponse(@SuppressWarnings("unused") final InvalidTenantIdentifierException exception) {
        log.warn("Exception: {}, Message: {}", exception.getClass().getName(), exception.getMessage());
        return Response.status(Status.UNAUTHORIZED).entity(ApiGlobalErrorResponse.invalidTenantIdentifier())
                .type(MediaType.APPLICATION_JSON).build();
    }
}
