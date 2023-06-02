
package org.apache.fineract.infrastructure.hooks.listener;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.fineract.infrastructure.core.domain.FineractContext;
import org.apache.fineract.infrastructure.core.service.ThreadLocalContextUtil;
import org.apache.fineract.infrastructure.hooks.domain.Hook;
import org.apache.fineract.infrastructure.hooks.event.HookEvent;
import org.apache.fineract.infrastructure.hooks.event.HookEventSource;
import org.apache.fineract.infrastructure.hooks.processor.HookProcessor;
import org.apache.fineract.infrastructure.hooks.processor.HookProcessorProvider;
import org.apache.fineract.infrastructure.hooks.service.HookReadPlatformService;
import org.apache.fineract.useradministration.domain.AppUser;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
@Slf4j
public class FineractHookListener implements HookListener {
    private final HookProcessorProvider hookProcessorProvider;
    private final HookReadPlatformService hookReadPlatformService;
    @Override
    public void onApplicationEvent(final HookEvent event) {
        ThreadLocalContextUtil.init(event.getContext());
        final AppUser appUser = event.getAppUser();
        final HookEventSource hookEventSource = (HookEventSource) event.getSource();
        final FineractContext fineractContext = event.getContext();
        final String entityName = hookEventSource.getEntityName();
        final String actionName = hookEventSource.getActionName();
        final String payload = event.getPayload();
        final List<Hook> hooks = this.hookReadPlatformService.retrieveHooksByEvent(hookEventSource.getEntityName(),
                hookEventSource.getActionName());
        for (final Hook hook : hooks) {
            final HookProcessor processor = this.hookProcessorProvider.getProcessor(hook);
            try {
                processor.process(hook, payload, entityName, actionName, fineractContext);
            } catch (Throwable e) {
                log.error("Hook {} failed in HookProcessor {} for tenantIdentifier/user {}/{}, entityName: {}, actionName: {}, payload {} ",
                        hook.getId(), processor.getClass().getSimpleName(), fineractContext.getTenantContext().getTenantIdentifier(),
                        appUser.getDisplayName(), entityName, actionName, payload, e);
            }
        }
    }
}
