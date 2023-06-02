
package org.apache.fineract.infrastructure.core.exceptionmapper;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.apache.fineract.infrastructure.core.data.ApiGlobalErrorResponse;
import org.apache.fineract.infrastructure.security.exception.NoAuthorizationException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Provider
@Component
@Scope("singleton")
@Slf4j
public class NoAuthorizationExceptionMapper implements ExceptionMapper<NoAuthorizationException> {
    @Override
    public Response toResponse(final NoAuthorizationException exception) {
        final String defaultUserMessage = exception.getMessage();
        log.warn("Exception: {}, Message: {}", exception.getClass().getName(), defaultUserMessage);
        return Response.status(Status.FORBIDDEN).entity(ApiGlobalErrorResponse.unAuthorized(defaultUserMessage))
                .type(MediaType.APPLICATION_JSON).build();
    }
}
