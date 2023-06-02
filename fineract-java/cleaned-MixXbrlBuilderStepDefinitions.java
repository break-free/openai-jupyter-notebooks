
package org.apache.fineract.mix.report;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import io.cucumber.java8.En;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.HashMap;
import org.apache.commons.io.IOUtils;
import org.apache.fineract.mix.data.MixTaxonomyData;
import org.apache.fineract.mix.data.NamespaceData;
import org.apache.fineract.mix.service.NamespaceReadPlatformServiceImpl;
import org.apache.fineract.mix.service.XBRLBuilder;
import org.apache.fineract.template.service.TemplateServiceStepDefinitions;
import org.mockito.ArgumentMatchers;
public class MixXbrlBuilderStepDefinitions implements En {
    private NamespaceReadPlatformServiceImpl readNamespaceService;
    private final XBRLBuilder xbrlBuilder = new XBRLBuilder();
    private Date start;
    private Date end;
    private String currency;
    private HashMap<MixTaxonomyData, BigDecimal> data = new HashMap<>();
    private String result;
    public MixXbrlBuilderStepDefinitions() {
        Given("/^The XBRL input parameters start date (.*), end date (.*), currency (.*), taxonomy (.*) and sample (.*)$/",
                (String start, String end, String currency, String taxonomy, String sample) -> {
                    readNamespaceService = mock(NamespaceReadPlatformServiceImpl.class);
                    lenient().when(this.readNamespaceService.retrieveNamespaceByPrefix(ArgumentMatchers.anyString()))
                            .thenReturn(new NamespaceData(1L, "mockedprefix", "mockedurl"));
                    this.start = Date.valueOf(start);
                    this.end = Date.valueOf(end);
                    this.currency = currency;
                    MixTaxonomyData taxonomyData = mock(MixTaxonomyData.class);
                    when(taxonomyData.getName()).thenReturn(taxonomy);
                    this.data.put(taxonomyData, BigDecimal.valueOf(Long.parseLong(sample)));
                });
        When("The user builds the XBRL report", () -> {
            result = this.xbrlBuilder.build(data, start, end, currency);
        });
        Then("/^The XBRL result should match (.*)$/", (String file) -> {
            String expected = IOUtils.resourceToString("results/" + file, StandardCharsets.UTF_8,
                    TemplateServiceStepDefinitions.class.getClassLoader());
            assertEquals(expected.trim(), result.trim());
        });
    }
}
