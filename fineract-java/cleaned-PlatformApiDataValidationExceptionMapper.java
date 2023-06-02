
package org.apache.fineract.infrastructure.core.exceptionmapper;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.apache.fineract.infrastructure.core.data.ApiGlobalErrorResponse;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Provider
@Component
@Scope("singleton")
@Slf4j
public class PlatformApiDataValidationExceptionMapper implements ExceptionMapper<PlatformApiDataValidationException> {
    @Override
    public Response toResponse(final PlatformApiDataValidationException exception) {
        log.warn("Exception: {}, Message: {}, Errors: {}", exception.getClass().getName(), exception.getMessage(), exception.getErrors());
        final ApiGlobalErrorResponse dataValidationErrorResponse = ApiGlobalErrorResponse
                .badClientRequest(exception.getGlobalisationMessageCode(), exception.getDefaultUserMessage(), exception.getErrors());
        return Response.status(Status.BAD_REQUEST).entity(dataValidationErrorResponse).type(MediaType.APPLICATION_JSON).build();
    }
}
