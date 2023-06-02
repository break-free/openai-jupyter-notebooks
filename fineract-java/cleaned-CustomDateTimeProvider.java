
package org.apache.fineract.infrastructure.core.auditing;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;
import org.apache.fineract.infrastructure.core.service.DateUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.auditing.DateTimeProvider;
public enum CustomDateTimeProvider implements DateTimeProvider {
    INSTANCE, TENANT;
    @NotNull
    @Override
    public Optional<TemporalAccessor> getNow() {
        switch (this) {
            case INSTANCE -> {
                return Optional.of(LocalDateTime.now(ZoneId.systemDefault()));
            }
            case TENANT -> {
                return Optional.of(OffsetDateTime.now(DateUtils.getDateTimeZoneOfTenant()));
            }
        }
        throw new UnsupportedOperationException(this + " is not supported!");
    }
}
