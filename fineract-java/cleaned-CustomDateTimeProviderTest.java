
package org.apache.fineract.infrastructure.core.auditing;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;
import org.apache.fineract.infrastructure.core.domain.FineractPlatformTenant;
import org.apache.fineract.infrastructure.core.service.DateUtils;
import org.apache.fineract.infrastructure.core.service.ThreadLocalContextUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class CustomDateTimeProviderTest {
    @BeforeEach
    public void init() {
        ThreadLocalContextUtil.setTenant(new FineractPlatformTenant(1L, "default", "Default", "Asia/Kolkata", null));
    }
    @Test
    public void instanceDateProvider() {
        Optional<TemporalAccessor> dateTimeProvider = CustomDateTimeProvider.INSTANCE.getNow();
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        assertTrue(dateTimeProvider.isPresent());
        assertTrue(dateTimeProvider.get() instanceof LocalDateTime);
        assertEquals(now.getYear(), ((LocalDateTime) dateTimeProvider.get()).getYear());
        assertEquals(now.getMonth(), ((LocalDateTime) dateTimeProvider.get()).getMonth());
        assertEquals(now.getDayOfMonth(), ((LocalDateTime) dateTimeProvider.get()).getDayOfMonth());
        assertEquals(now.getHour(), ((LocalDateTime) dateTimeProvider.get()).getHour());
        assertEquals(now.getMinute(), ((LocalDateTime) dateTimeProvider.get()).getMinute());
    }
    @Test
    public void tenantDateProvider() {
        Optional<TemporalAccessor> dateTimeProvider = CustomDateTimeProvider.TENANT.getNow();
        OffsetDateTime now = OffsetDateTime.now(DateUtils.getDateTimeZoneOfTenant());
        assertTrue(dateTimeProvider.isPresent());
        assertTrue(dateTimeProvider.get() instanceof OffsetDateTime);
        assertEquals(now.getYear(), ((OffsetDateTime) dateTimeProvider.get()).getYear());
        assertEquals(now.getMonth(), ((OffsetDateTime) dateTimeProvider.get()).getMonth());
        assertEquals(now.getDayOfMonth(), ((OffsetDateTime) dateTimeProvider.get()).getDayOfMonth());
        assertEquals(now.getHour(), ((OffsetDateTime) dateTimeProvider.get()).getHour());
        assertEquals(now.getMinute(), ((OffsetDateTime) dateTimeProvider.get()).getMinute());
    }
}
