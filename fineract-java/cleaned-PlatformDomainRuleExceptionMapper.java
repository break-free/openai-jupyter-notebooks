
package org.apache.fineract.infrastructure.core.exceptionmapper;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.apache.fineract.infrastructure.core.data.ApiGlobalErrorResponse;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Provider
@Component
@Scope("singleton")
@Slf4j
public class PlatformDomainRuleExceptionMapper implements ExceptionMapper<AbstractPlatformDomainRuleException> {
    @Override
    public Response toResponse(final AbstractPlatformDomainRuleException exception) {
        log.warn("Exception: {}, Message: {}", exception.getClass().getName(), exception.getMessage());
        final ApiGlobalErrorResponse notFoundErrorResponse = ApiGlobalErrorResponse.domainRuleViolation(
                exception.getGlobalisationMessageCode(), exception.getDefaultUserMessage(), exception.getDefaultUserMessageArgs());
        return Response.status(Status.FORBIDDEN).entity(notFoundErrorResponse).type(MediaType.APPLICATION_JSON).build();
    }
}
