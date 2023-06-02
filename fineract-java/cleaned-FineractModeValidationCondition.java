
package org.apache.fineract.infrastructure.core.config;
import java.util.Optional;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
public class FineractModeValidationCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        boolean isReadModeEnabled = Optional.ofNullable(context.getEnvironment().getProperty("fineract.mode.read-enabled", Boolean.class))
                .orElse(true);
        boolean isWriteModeEnabled = Optional.ofNullable(context.getEnvironment().getProperty("fineract.mode.write-enabled", Boolean.class))
                .orElse(true);
        boolean isBatchModeEnabled = Optional.ofNullable(context.getEnvironment().getProperty("fineract.mode.batch-enabled", Boolean.class))
                .orElse(true);
        return !isReadModeEnabled && !isWriteModeEnabled && !isBatchModeEnabled;
    }
}
