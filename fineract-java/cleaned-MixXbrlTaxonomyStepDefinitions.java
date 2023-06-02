
package org.apache.fineract.mix.report;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.mock;
import io.cucumber.java8.En;
import java.util.List;
import org.apache.fineract.mix.service.XBRLResultServiceImpl;
import org.assertj.core.util.Arrays;
import org.springframework.jdbc.core.JdbcTemplate;
public class MixXbrlTaxonomyStepDefinitions implements En {
    private XBRLResultServiceImpl readService;
    private String template;
    private List<String> result;
    public MixXbrlTaxonomyStepDefinitions() {
        Given("/^A XBRL template (.*)$/", (String template) -> {
            this.readService = new XBRLResultServiceImpl(mock(JdbcTemplate.class), null, null);
            this.template = template;
        });
        When("The user resolves GL codes", () -> {
            this.result = this.readService.getGLCodes(template);
        });
        Then("/^The result should contain (.*)$/", (String line) -> {
            assertArrayEquals(result.toArray(new String[0]), Arrays.array(line.split(",")));
        });
    }
}
