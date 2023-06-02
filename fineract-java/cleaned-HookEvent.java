
package org.apache.fineract.infrastructure.hooks.event;
import lombok.Getter;
import org.apache.fineract.infrastructure.core.domain.FineractContext;
import org.apache.fineract.infrastructure.core.domain.FineractEvent;
import org.apache.fineract.useradministration.domain.AppUser;
@Getter
public class HookEvent extends FineractEvent {
    private final String payload;
    private final AppUser appUser;
    public HookEvent(final HookEventSource source, final String payload, final AppUser appUser, FineractContext fineractContext) {
        super(source, fineractContext);
        this.payload = payload;
        this.appUser = appUser;
    }
}
