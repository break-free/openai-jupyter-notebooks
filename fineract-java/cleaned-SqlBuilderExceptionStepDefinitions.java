
package org.apache.fineract.infrastructure.sqlbuilder;
import static org.apache.fineract.infrastructure.sqlbuilder.SqlBuilderUtil.transform;
import static org.junit.jupiter.api.Assertions.assertEquals;
import io.cucumber.java8.En;
import org.apache.fineract.infrastructure.security.utils.SQLBuilder;
public class SqlBuilderExceptionStepDefinitions implements En {
    private SQLBuilder sqlBuilder;
    private Throwable throwable;
    public SqlBuilderExceptionStepDefinitions() {
        Given("/^An illegal criteria (.*) with argument (.*)$/", (String criteria, String argument) -> {
            sqlBuilder = new SQLBuilder();
            try {
                sqlBuilder.addCriteria(criteria, transform(argument));
            } catch (Throwable t) {
                this.throwable = t;
            }
        });
        Then("/^The builder should throw an exception (.*) with message (.*)$/", (String exception, String message) -> {
            assertEquals(Class.forName(exception), throwable.getClass());
        });
    }
}
