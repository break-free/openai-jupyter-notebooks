
package org.apache.fineract.commands.provider;
import static org.junit.jupiter.api.Assertions.assertThrows;
import io.cucumber.java8.En;
import org.apache.fineract.commands.exception.UnsupportedCommandException;
import org.springframework.beans.factory.annotation.Autowired;
public class CommandHandlerExceptionStepDefinitions implements En {
    @Autowired
    private CommandHandlerProvider commandHandlerProvider;
    private String entity;
    private String action;
    public CommandHandlerExceptionStepDefinitions() {
        Given("/^A missing command handler for entity (.*) and action (.*)$/", (String entity, String action) -> {
            this.entity = entity;
            this.action = action;
        });
        Then("The system should throw an exception", () -> {
            assertThrows(UnsupportedCommandException.class, () -> {
                this.commandHandlerProvider.getHandler(entity, action);
            });
        });
    }
}
