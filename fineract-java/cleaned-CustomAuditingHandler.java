
package org.springframework.data.auditing;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;
import org.apache.fineract.infrastructure.core.auditing.CustomDateTimeProvider;
import org.apache.fineract.infrastructure.core.domain.AbstractAuditableWithUTCDateTimeCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mapping.context.PersistentEntities;
import org.springframework.util.Assert;
public class CustomAuditingHandler extends AuditingHandler {
    private static final Logger LOG = LoggerFactory.getLogger(CustomAuditingHandler.class);
    private final AuditableBeanWrapperFactory factory;
    private boolean dateTimeForNow = true;
    private boolean modifyOnCreation = true;
    public CustomAuditingHandler(PersistentEntities entities) {
        super(entities);
        this.factory = new MappingAuditableBeanWrapperFactory(entities);
    }
    public CustomAuditingHandler(MappingContext<? extends PersistentEntity<?, ?>, ? extends PersistentProperty<?>> mappingContext,
            AuditorAware<?> auditorAware) {
        this(PersistentEntities.of(mappingContext));
        setAuditorAware(auditorAware);
    }
    private Optional<TemporalAccessor> touchDate(AuditableBeanWrapper<?> wrapper, boolean isNew) {
        Assert.notNull(wrapper, "AuditableBeanWrapper must not be null");
        DateTimeProvider dateTimeProvider = fetchDateTimeProvider(wrapper.getBean());
        Optional<TemporalAccessor> now = dateTimeProvider.getNow();
        Assert.notNull(now, () -> String.format("Now must not be null Returned by: %s", dateTimeProvider.getClass()));
        now.filter(__ -> isNew).ifPresent(wrapper::setCreatedDate);
        now.filter(__ -> !isNew || modifyOnCreation).ifPresent(wrapper::setLastModifiedDate);
        return now;
    }
    private DateTimeProvider fetchDateTimeProvider(Object bean) {
        return bean instanceof AbstractAuditableWithUTCDateTimeCustom ? CustomDateTimeProvider.TENANT : CustomDateTimeProvider.INSTANCE;
    }
    @Override
    <T> T markCreated(Auditor auditor, T source) {
        Assert.notNull(source, "Source entity must not be null");
        return touch(auditor, source, true);
    }
    @Override
    <T> T markModified(Auditor auditor, T source) {
        Assert.notNull(source, "Source entity must not be null");
        return touch(auditor, source, false);
    }
    private <T> T touch(Auditor auditor, T target, boolean isNew) {
        Optional<AuditableBeanWrapper<T>> wrapper = factory.getBeanWrapperFor(target);
        return wrapper.map(it -> {
            touchAuditor(auditor, it, isNew);
            Optional<TemporalAccessor> now = dateTimeForNow ? touchDate(it, isNew) : Optional.empty();
            if (LOG.isDebugEnabled()) {
                Object defaultedNow = now.map(Object::toString).orElse("not set");
                Object defaultedAuditor = auditor.isPresent() ? auditor.toString() : "unknown";
                LOG.debug("Touched {} - Last modification at {} by {}", target, defaultedNow, defaultedAuditor);
            }
            return it.getBean();
        }).orElse(target);
    }
    private void touchAuditor(Auditor auditor, AuditableBeanWrapper<?> wrapper, boolean isNew) {
        if (!auditor.isPresent()) {
            return;
        }
        Assert.notNull(wrapper, "AuditableBeanWrapper must not be null");
        if (isNew) {
            wrapper.setCreatedBy(auditor.getValue());
        }
        if (!isNew || modifyOnCreation) {
            wrapper.setLastModifiedBy(auditor.getValue());
        }
    }
}
