
package org.apache.fineract.interoperation.exception;
import javax.validation.constraints.NotNull;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.apache.fineract.interoperation.domain.InteropIdentifierType;
public class InteropAccountNotFoundException extends AbstractPlatformResourceNotFoundException {
    public InteropAccountNotFoundException(@NotNull InteropIdentifierType idType, @NotNull String idValue, String subIdOrType) {
        super("error.msg.interop.account.not.found",
                "Account not found for identifier " + idType + "/" + idValue + (subIdOrType == null ? "" : ("/" + subIdOrType)));
    }
}
