
package org.apache.fineract.organisation.holiday.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class HolidayDateException extends AbstractPlatformDomainRuleException {
    public HolidayDateException(final String postFix, final String defaultUserMessage, final Object... defaultUserMessageArgs) {
        super("error.msg.holiday." + postFix, defaultUserMessage, defaultUserMessageArgs);
    }
}
