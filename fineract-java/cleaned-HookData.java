
package org.apache.fineract.infrastructure.hooks.data;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public final class HookData implements Serializable {
    private final Long id;
    private final String name;
    private final String displayName;
    private final Boolean isActive;
    private final LocalDate createdAt;
    private final LocalDate updatedAt;
    private final Long templateId;
    private final String templateName;
    private final List<Event> events;
    private final List<Field> config;
    private final List<HookTemplateData> templates;
    private final List<Grouping> groupings;
    public static HookData instance(final Long id, final String name, final String displayName, final boolean isActive,
            final LocalDate createdAt, final LocalDate updatedAt, final Long templateId, final List<Event> registeredEvents,
            final List<Field> config, final String templateName) {
        return new HookData(id, name, displayName, isActive, createdAt, updatedAt, templateId, templateName, registeredEvents, config, null,
                null);
    }
    public static HookData template(final List<HookTemplateData> templates, final List<Grouping> groupings) {
        return new HookData(null, null, null, null, null, null, null, null, null, null, templates, groupings);
    }
    public static HookData templateExisting(final HookData hookData, final List<HookTemplateData> templates,
            final List<Grouping> groupings) {
        return new HookData(hookData.id, hookData.name, hookData.displayName, hookData.isActive, hookData.createdAt, hookData.updatedAt,
                hookData.templateId, hookData.templateName, hookData.events, hookData.config, templates, groupings);
    }
}
