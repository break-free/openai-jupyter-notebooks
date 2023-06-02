
package org.apache.fineract;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
@CucumberContextConfiguration
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application-test.properties")
@WebAppConfiguration
@ContextConfiguration(classes = TestConfiguration.class)
public class TestSuite {
}
