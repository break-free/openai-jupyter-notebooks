
package org.apache.fineract.infrastructure.core.config;
import java.util.Optional;
import org.apache.fineract.infrastructure.instancemode.api.FineractInstanceModeConstants;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
public class EnableFineractEventsCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        boolean isReadModeEnabled = Optional.ofNullable(
                context.getEnvironment().getProperty(FineractInstanceModeConstants.FINERACT_MODE_READ_ENABLE_PROPERTY, Boolean.class))
                .orElse(true);
        boolean isWriteModeEnabled = Optional.ofNullable(
                context.getEnvironment().getProperty(FineractInstanceModeConstants.FINERACT_MODE_WRITE_ENABLE_PROPERTY, Boolean.class))
                .orElse(true);
        boolean isBatchModeEnabled = Optional.ofNullable(
                context.getEnvironment().getProperty(FineractInstanceModeConstants.FINERACT_MODE_BATCH_ENABLE_PROPERTY, Boolean.class))
                .orElse(true);
        return !isReadModeEnabled && (isWriteModeEnabled || isBatchModeEnabled);
    }
}
