
package org.apache.fineract.infrastructure.hooks.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
public class HookNotFoundException extends AbstractPlatformResourceNotFoundException {
    public HookNotFoundException(final String name) {
        super("error.msg.hook.not.found", "Hook with name `" + name + "` does not exist", name);
    }
    public HookNotFoundException(final Long hookId) {
        super("error.msg.hook.identifier.not.found", "Hook with identifier `" + hookId + "` does not exist", hookId);
    }
    public HookNotFoundException(Long hookId, EmptyResultDataAccessException e) {
        super("error.msg.hook.identifier.not.found", "Hook with identifier `" + hookId + "` does not exist", hookId, e);
    }
}
