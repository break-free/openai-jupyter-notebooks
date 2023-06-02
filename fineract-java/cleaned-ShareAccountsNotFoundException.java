
package org.apache.fineract.portfolio.shareproducts.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class ShareAccountsNotFoundException extends AbstractPlatformResourceNotFoundException {
    public ShareAccountsNotFoundException(final Long productId) {
        super("error.msg.shareproduct.shareaccounts.not.defined", " No Share Accounts found for this prodct " + productId, productId);
    }
}
