
package org.apache.fineract.infrastructure.core.exceptionmapper;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.apache.fineract.commands.exception.UnsupportedCommandException;
import org.apache.fineract.infrastructure.core.data.ApiGlobalErrorResponse;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Provider
@Component
@Scope("singleton")
@Slf4j
public class UnsupportedCommandExceptionMapper implements ExceptionMapper<UnsupportedCommandException> {
    @Override
    public Response toResponse(final UnsupportedCommandException exception) {
        final List<ApiParameterError> errors = new ArrayList<>();
        final StringBuilder validationErrorCode = new StringBuilder("error.msg.command.unsupported");
        final StringBuilder defaultEnglishMessage = new StringBuilder("The command ").append(exception.getUnsupportedCommandName())
                .append(" is not supported.");
        log.warn("Exception: {}, Message: {}", exception.getClass().getName(), defaultEnglishMessage);
        final ApiParameterError error = ApiParameterError.parameterError(validationErrorCode.toString(), defaultEnglishMessage.toString(),
                exception.getUnsupportedCommandName(), exception.getUnsupportedCommandName());
        errors.add(error);
        final ApiGlobalErrorResponse invalidParameterError = ApiGlobalErrorResponse
                .badClientRequest("validation.msg.validation.errors.exist", "Validation errors exist.", errors);
        return Response.status(Status.BAD_REQUEST).entity(invalidParameterError).type(MediaType.APPLICATION_JSON).build();
    }
}
