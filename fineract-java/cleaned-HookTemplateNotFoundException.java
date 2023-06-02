
package org.apache.fineract.infrastructure.hooks.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class HookTemplateNotFoundException extends AbstractPlatformResourceNotFoundException {
    public HookTemplateNotFoundException(final String name) {
        super("error.msg.template.not.found", "Template with name `" + name + "` does not exist", name);
    }
    public HookTemplateNotFoundException(final Long templateId) {
        super("error.msg.template.identifier.not.found", "Template with identifier `" + templateId + "` does not exist", templateId);
    }
}
