
package org.apache.fineract.commands.provider;
import static org.junit.jupiter.api.Assertions.assertEquals;
import io.cucumber.java8.En;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.springframework.beans.factory.annotation.Autowired;
public class CommandHandlerProviderStepDefinitions implements En {
    @Autowired
    private CommandHandlerProvider commandHandlerProvider;
    private NewCommandSourceHandler commandHandler;
    private CommandProcessingResult result;
    public CommandHandlerProviderStepDefinitions() {
        Given("/^A command handler for entity (.*) and action (.*)$/", (String entity, String action) -> {
            this.commandHandler = this.commandHandlerProvider.getHandler(entity, action);
        });
        When("The user processes the command with ID {long}", (Long id) -> {
            this.result = commandHandler
                    .processCommand(JsonCommand.fromExistingCommand(id, null, null, null, null, null, null, null, null, null, null));
        });
        Then("The command ID matches {long}", (Long id) -> {
            assertEquals(id, result.commandId());
        });
    }
}
