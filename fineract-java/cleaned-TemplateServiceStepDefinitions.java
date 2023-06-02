
package org.apache.fineract.template.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.cucumber.java8.En;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.fineract.template.domain.Template;
import org.apache.fineract.template.domain.TemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
public class TemplateServiceStepDefinitions implements En {
    @Autowired
    private TemplateMergeService tms;
    private String template;
    private Map<String, Object> data;
    private String result;
    public TemplateServiceStepDefinitions() {
        Given("/^A mustache template file (.*)$/", (String file) -> {
            template = IOUtils.resourceToString("templates/" + file, StandardCharsets.UTF_8,
                    TemplateServiceStepDefinitions.class.getClassLoader());
        });
        Given("/^A JSON data file (.*)$/", (String file) -> {
            data = parse(IOUtils.resourceToString("templates/" + file, StandardCharsets.UTF_8,
                    TemplateServiceStepDefinitions.class.getClassLoader()));
        });
        When("The user merges the template with data", () -> {
            result = compile(template, data);
        });
        Then("/^The result should match the content of file (.*)$/", (String file) -> {
            String expected = IOUtils.resourceToString("results/" + file, StandardCharsets.UTF_8,
                    TemplateServiceStepDefinitions.class.getClassLoader());
            assertEquals(expected, result);
        });
    }
    private String compile(String templateText, Map<String, Object> scope) throws IOException {
        List<TemplateMapper> mappers = new ArrayList<>();
        Template template = new Template("TemplateName", templateText, null, null, mappers);
        return tms.compile(template, scope);
    }
    private Map<String, Object> parse(String data) {
        Gson gson = new Gson();
        Type ssMap = new TypeToken<Map<String, Object>>() {}.getType();
        JsonElement json = JsonParser.parseString(data);
        return gson.fromJson(json, ssMap);
    }
}
