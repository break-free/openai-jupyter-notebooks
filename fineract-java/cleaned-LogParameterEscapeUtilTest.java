
package org.apache.fineract.infrastructure.security.utils;
import static org.junit.jupiter.api.Assertions.assertEquals;
import io.cucumber.java8.En;
public class LogParameterEscapeUtilTest implements En {
    private String logParameter;
    private String escapedLogParameter;
    public LogParameterEscapeUtilTest() {
        Given("A simple log message without any special character", () -> {
            logParameter = "This is a very simple String without any special character.";
        });
        Given("A log message with new line, carriage return and tab characters", () -> {
            logParameter = "This String contains new line\n, carriage return\r and tab\t characters.";
        });
        When("Log parameter escape util escaping the special characters", () -> {
            escapedLogParameter = LogParameterEscapeUtil.escapeLogParameter(logParameter);
        });
        Then("The log message stays as it is", () -> {
            assertEquals(logParameter, escapedLogParameter);
        });
        Then("The escape util changes the special characters to `_`", () -> {
            assertEquals("This String contains new line_, carriage return_ and tab_ characters.", escapedLogParameter);
        });
    }
}
