
package org.apache.fineract.infrastructure.hooks.service;
import java.util.Collection;
import java.util.List;
import org.apache.fineract.infrastructure.hooks.data.HookData;
import org.apache.fineract.infrastructure.hooks.domain.Hook;
public interface HookReadPlatformService {
    Collection<HookData> retrieveAllHooks();
    HookData retrieveHook(Long hookId);
    List<Hook> retrieveHooksByEvent(String entityName, String actionName);
    HookData retrieveNewHookDetails(String templateName);
}
