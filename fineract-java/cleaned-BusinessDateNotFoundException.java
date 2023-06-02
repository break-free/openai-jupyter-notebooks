
package org.apache.fineract.infrastructure.businessdate.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class BusinessDateNotFoundException extends AbstractPlatformResourceNotFoundException {
    public BusinessDateNotFoundException(String globalisationMessageCode, String defaultUserMessage, Object... defaultUserMessageArgs) {
        super(globalisationMessageCode, defaultUserMessage, defaultUserMessageArgs);
    }
    public static BusinessDateNotFoundException notExist(final String type, Throwable... e) {
        return new BusinessDateNotFoundException("error.msg.businessdate.type.not.exist",
                "Business date with type `" + type + "` does not exist.", type, e);
    }
    public static BusinessDateNotFoundException notFound(final String type, Throwable... e) {
        return new BusinessDateNotFoundException("error.msg.businessdate.not.found",
                "Business date with type `" + type + "` does not found.", type, e);
    }
}
