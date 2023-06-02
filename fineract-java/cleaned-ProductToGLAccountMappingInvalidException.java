
package org.apache.fineract.accounting.producttoaccountmapping.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class ProductToGLAccountMappingInvalidException extends AbstractPlatformDomainRuleException {
    public ProductToGLAccountMappingInvalidException(final String paramName, final String accountName, final Long accountId,
            final String actualAccountCategory, final String expectedAccountCategory) {
        super("error.msg." + paramName + ".invalid.account.type",
                "Passed in GLAccount " + paramName + " with Id " + accountId + "maps to the account " + accountName + " of type "
                        + actualAccountCategory + ", the expected account type was one among" + expectedAccountCategory,
                paramName, accountId, accountName, actualAccountCategory, expectedAccountCategory);
    }
}
