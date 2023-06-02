
package org.springframework.data.auditing;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import org.apache.fineract.infrastructure.businessdate.domain.BusinessDateType;
import org.apache.fineract.infrastructure.core.domain.AbstractAuditableCustom;
import org.apache.fineract.infrastructure.core.domain.AbstractAuditableWithUTCDateTimeCustom;
import org.apache.fineract.infrastructure.core.domain.FineractPlatformTenant;
import org.apache.fineract.infrastructure.core.service.DateUtils;
import org.apache.fineract.infrastructure.core.service.ThreadLocalContextUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mapping.context.PersistentEntities;
public class CustomAuditingHandlerTest {
    @BeforeEach
    public void init() {
        ThreadLocalContextUtil.setTenant(new FineractPlatformTenant(1L, "default", "Default", "Asia/Kolkata", null));
        ThreadLocalContextUtil
                .setBusinessDates(new HashMap<>(Map.of(BusinessDateType.BUSINESS_DATE, LocalDate.now(ZoneId.of("Asia/Kolkata")))));
    }
    @Test
    public void markCreated() {
        Auditor auditor = Mockito.mock(Auditor.class);
        MappingContext mappingContext = Mockito.mock(MappingContext.class);
        CustomAuditingHandler testInstance = new CustomAuditingHandler(PersistentEntities.of(mappingContext));
        AbstractAuditableWithUTCDateTimeCustom targetObject = Mockito.spy(AbstractAuditableWithUTCDateTimeCustom.class);
        targetObject = testInstance.markCreated(auditor, targetObject);
        OffsetDateTime now = OffsetDateTime.now(DateUtils.getDateTimeZoneOfTenant());
        assertTrue(targetObject.getCreatedDate().isPresent());
        assertEquals(now.getYear(), targetObject.getCreatedDate().get().getYear());
        assertEquals(now.getMonth(), targetObject.getCreatedDate().get().getMonth());
        assertEquals(now.getDayOfMonth(), targetObject.getCreatedDate().get().getDayOfMonth());
        assertEquals(now.getHour(), targetObject.getCreatedDate().get().getHour());
        assertEquals(now.getMinute(), targetObject.getCreatedDate().get().getMinute());
    }
    @Test
    public void markModified() {
        Auditor auditor = Mockito.mock(Auditor.class);
        MappingContext mappingContext = Mockito.mock(MappingContext.class);
        CustomAuditingHandler testInstance = new CustomAuditingHandler(PersistentEntities.of(mappingContext));
        AbstractAuditableWithUTCDateTimeCustom targetObject = Mockito.spy(AbstractAuditableWithUTCDateTimeCustom.class);
        targetObject = testInstance.markModified(auditor, targetObject);
        OffsetDateTime now = OffsetDateTime.now(DateUtils.getDateTimeZoneOfTenant());
        assertTrue(targetObject.getLastModifiedDate().isPresent());
        assertEquals(now.getYear(), targetObject.getLastModifiedDate().get().getYear());
        assertEquals(now.getMonth(), targetObject.getLastModifiedDate().get().getMonth());
        assertEquals(now.getDayOfMonth(), targetObject.getLastModifiedDate().get().getDayOfMonth());
        assertEquals(now.getHour(), targetObject.getLastModifiedDate().get().getHour());
        assertEquals(now.getMinute(), targetObject.getLastModifiedDate().get().getMinute());
    }
    @Test
    public void markModifiedOldDateTimeProvider() {
        Auditor auditor = Mockito.mock(Auditor.class);
        MappingContext mappingContext = Mockito.mock(MappingContext.class);
        CustomAuditingHandler testInstance = new CustomAuditingHandler(PersistentEntities.of(mappingContext));
        AbstractAuditableCustom targetObject = Mockito.spy(AbstractAuditableCustom.class);
        targetObject = testInstance.markModified(auditor, targetObject);
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        assertTrue(targetObject.getLastModifiedDate().isPresent());
        assertEquals(now.getYear(), targetObject.getLastModifiedDate().get().getYear());
        assertEquals(now.getMonth(), targetObject.getLastModifiedDate().get().getMonth());
        assertEquals(now.getDayOfMonth(), targetObject.getLastModifiedDate().get().getDayOfMonth());
        assertEquals(now.getHour(), targetObject.getLastModifiedDate().get().getHour());
        assertEquals(now.getMinute(), targetObject.getLastModifiedDate().get().getMinute());
    }
    @Test
    public void markCreatedOldDateTimeProvider() {
        Auditor auditor = Mockito.mock(Auditor.class);
        MappingContext mappingContext = Mockito.mock(MappingContext.class);
        CustomAuditingHandler testInstance = new CustomAuditingHandler(PersistentEntities.of(mappingContext));
        AbstractAuditableCustom targetObject = Mockito.spy(AbstractAuditableCustom.class);
        targetObject = testInstance.markCreated(auditor, targetObject);
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        assertTrue(targetObject.getCreatedDate().isPresent());
        assertEquals(now.getYear(), targetObject.getCreatedDate().get().getYear());
        assertEquals(now.getMonth(), targetObject.getCreatedDate().get().getMonth());
        assertEquals(now.getDayOfMonth(), targetObject.getCreatedDate().get().getDayOfMonth());
        assertEquals(now.getHour(), targetObject.getCreatedDate().get().getHour());
        assertEquals(now.getMinute(), targetObject.getCreatedDate().get().getMinute());
    }
}
