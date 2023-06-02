
package org.apache.fineract.commands.handler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface NewCommandSourceHandler {
    CommandProcessingResult processCommand(JsonCommand command);
}
