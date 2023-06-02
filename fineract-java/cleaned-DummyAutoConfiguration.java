
package org.apache.fineract.dummy.starter;
import org.apache.fineract.dummy.core.service.DummyService;
import org.apache.fineract.dummy.service.DummyServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
@ConditionalOnMissingBean(DummyService.class)
public class DummyAutoConfiguration {
    @Bean
    public DummyService dummyService() {
        return new DummyServiceImpl();
    }
}
