
package org.apache.fineract.infrastructure.core;
import static org.junit.jupiter.api.Assertions.assertThrows;
import io.cucumber.java8.En;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.infrastructure.core.exception.MultiException;
public class MultiExceptionStepDefinitions implements En {
    private List<Throwable> exceptions = new ArrayList<>();
    private MultiException multiException;
    public MultiExceptionStepDefinitions() {
        Given("/^A multi exception with exceptions (.*) and (.*)$/", (String exception1, String exception2) -> {
            if (!StringUtils.isBlank(exception1)) {
                this.exceptions.add(Class.forName(exception1).asSubclass(Throwable.class).getDeclaredConstructor().newInstance());
            }
            if (!StringUtils.isBlank(exception2)) {
                this.exceptions.add(Class.forName(exception2).asSubclass(Throwable.class).getDeclaredConstructor().newInstance());
            }
        });
        Then("/^A (.*) should be thrown$/", (String expected) -> {
            assertThrows(Class.forName(expected).asSubclass(Throwable.class), () -> {
                throw new MultiException(exceptions);
            });
        });
    }
}
