
package org.apache.fineract.template.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformException;
public class TemplateForbiddenException extends AbstractPlatformException {
    public TemplateForbiddenException(final String url) {
        super("error.msg.template.url.forbidden", "Template with url " + url + " not allowed");
    }
}
