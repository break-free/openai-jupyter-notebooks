
package org.apache.fineract.template.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class TemplateNotFoundException extends AbstractPlatformResourceNotFoundException {
    public TemplateNotFoundException(final Long id) {
        super("error.msg.template.id.invalid", "Template with identifier " + id + " does not exist", id);
    }
}
