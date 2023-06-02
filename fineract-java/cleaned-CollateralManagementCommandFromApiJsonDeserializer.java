
package org.apache.fineract.portfolio.collateralmanagement.serialization;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
public class CollateralManagementCommandFromApiJsonDeserializer {
    private final FromJsonHelper fromApiJsonHelper;
    @Autowired
    public CollateralManagementCommandFromApiJsonDeserializer(final FromJsonHelper fromApiJsonHelper) {
        this.fromApiJsonHelper = fromApiJsonHelper;
    }
}
