
package org.apache.fineract.infrastructure.businessdate.data;
import static org.junit.Assert.assertEquals;
import org.apache.fineract.infrastructure.businessdate.domain.BusinessDateType;
import org.junit.Test;
public class BusinessDataTypeTest {
    @Test
    public void typoCheck() {
        for (BusinessDateType businessDateType : BusinessDateType.values()) {
            switch (businessDateType) {
                case BUSINESS_DATE -> assertEquals("Business Date", businessDateType.getDescription());
                case COB_DATE -> assertEquals("Close of Business Date", businessDateType.getDescription());
            }
        }
    }
}
