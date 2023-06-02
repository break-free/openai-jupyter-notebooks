
package org.apache.fineract.infrastructure.entityaccess.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class NotOfficeSpecificProductException extends AbstractPlatformDomainRuleException {
    public NotOfficeSpecificProductException(final Long productId, final Long officeId) {
        super("error.msg.office.product.not.found",
                "Product with productId " + productId + " not office Specific Product in offfice with officeId", officeId);
    }
}
