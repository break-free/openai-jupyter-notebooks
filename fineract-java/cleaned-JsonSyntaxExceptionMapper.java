
package org.apache.fineract.infrastructure.core.exceptionmapper;
import com.google.gson.JsonSyntaxException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Provider
@Component
@Scope("singleton")
@Slf4j
public class JsonSyntaxExceptionMapper implements ExceptionMapper<JsonSyntaxException> {
    @Override
    public Response toResponse(final JsonSyntaxException exception) {
        final String globalisationMessageCode = "error.msg.invalid.request.body";
        final String defaultUserMessage = "The JSON syntax provided in the body of the request is invalid: " + exception.getMessage();
        log.warn("Exception: {}, Message: {}", exception.getClass().getName(), defaultUserMessage);
        final ApiParameterError error = ApiParameterError.generalError(globalisationMessageCode, defaultUserMessage);
        return Response.status(Status.BAD_REQUEST).entity(error).type(MediaType.APPLICATION_JSON).build();
    }
}
