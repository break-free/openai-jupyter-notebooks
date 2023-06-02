
package org.apache.fineract.infrastructure;
import lombok.extern.slf4j.Slf4j;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.exception.PlatformDataIntegrityException;
import org.springframework.stereotype.Component;
@Component
@Slf4j
public class DataIntegrityErrorHandler {
    public void handleDataIntegrityIssues(final JsonCommand command, final Throwable realCause, final Exception dve, final String msgType,
            final String msgDescription) {
        if (realCause.getMessage().contains("external_id")) {
            final String externalId = command.stringValueOfParameterNamed("externalId");
            throw new PlatformDataIntegrityException("error.msg." + msgType + ".duplicate.externalId",
                    msgDescription + ": externalId `" + externalId + "` already exists", "externalId", externalId);
        }
        logAsErrorUnexpectedDataIntegrityException(dve);
        throw new PlatformDataIntegrityException("error.msg." + msgType + ".unknown.data.integrity.issue",
                "Unknown data integrity issue with resource: " + msgDescription);
    }
    private void logAsErrorUnexpectedDataIntegrityException(final Exception dve) {
        log.error("Error occured.", dve);
    }
}
