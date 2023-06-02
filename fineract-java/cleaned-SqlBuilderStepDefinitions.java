
package org.apache.fineract.infrastructure.sqlbuilder;
import static org.apache.fineract.infrastructure.sqlbuilder.SqlBuilderUtil.transform;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import io.cucumber.java8.En;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.infrastructure.security.utils.SQLBuilder;
public class SqlBuilderStepDefinitions implements En {
    private SQLBuilder sqlBuilder;
    private List<Object> expectedArguments;
    public SqlBuilderStepDefinitions() {
        Given("/^A criteria (.*), (.*), (.*), (.*) and arguments (.*), (.*), (.*), (.*)$/", (String criteria1, String criteria2,
                String criteria3, String criteria4, String argument1, String argument2, String argument3, String argument4) -> {
            sqlBuilder = new SQLBuilder();
            expectedArguments = new ArrayList<>();
            if (!StringUtils.isEmpty(criteria1)) {
                sqlBuilder.addCriteria(criteria1, transform(argument1));
            }
            if (!StringUtils.isEmpty(criteria2)) {
                sqlBuilder.addCriteria(criteria2, transform(argument2));
            }
            if (!StringUtils.isEmpty(criteria3)) {
                sqlBuilder.addCriteria(criteria3, transform(argument3));
            }
            if (!StringUtils.isEmpty(criteria4)) {
                sqlBuilder.addCriteria(criteria4, transform(argument4));
            }
            if (!StringUtils.isBlank(argument1)) {
                expectedArguments.add(transform(argument1));
            }
            if (!StringUtils.isBlank(argument2)) {
                expectedArguments.add(transform(argument2));
            }
            if (!StringUtils.isBlank(argument3)) {
                expectedArguments.add(transform(argument3));
            }
            if (!StringUtils.isBlank(argument4)) {
                expectedArguments.add(transform(argument4));
            }
        });
        Then("/^The template should match expected template (.*)$/", (String template) -> {
            assertEquals(template.trim(), sqlBuilder.getSQLTemplate().trim());
        });
        Then("/^The arguments should match expected arguments (.*)$/", (String argLine) -> {
            assertArrayEquals(expectedArguments.toArray(new Object[0]), sqlBuilder.getArguments(), sqlBuilder.toString());
        });
        Then("/^The builder should match (.*)$/", (String expected) -> {
            if (!StringUtils.isBlank(expected)) {
                assertEquals(expected, sqlBuilder.toString());
            }
        });
    }
}
