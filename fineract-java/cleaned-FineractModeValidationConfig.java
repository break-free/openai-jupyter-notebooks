
package org.apache.fineract.infrastructure.core.config;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
@Conditional(FineractModeValidationCondition.class)
public class FineractModeValidationConfig implements InitializingBean {
    private final ApplicationContext applicationContext;
    @Override
    public void afterPropertiesSet() throws Exception {
        terminateApplication();
    }
    private void terminateApplication() {
        log.error(
                "The Fineract instance type is not configured properly. At least one of these environment variables should be true: FINERACT_MODE_READ_ENABLED, FINERACT_MODE_WRITE_ENABLED, FINERACT_MODE_BATCH_ENABLED");
        ((ConfigurableApplicationContext) this.applicationContext).close();
    }
}
