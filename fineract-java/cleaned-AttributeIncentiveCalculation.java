
package org.apache.fineract.portfolio.interestratechart.incentive;
import java.math.BigDecimal;
import org.apache.fineract.portfolio.common.domain.ConditionType;
public abstract class AttributeIncentiveCalculation {
    public abstract BigDecimal calculateIncentive(IncentiveDTO incentiveDTO);
    public boolean applyIncentive(ConditionType conditionType, Long attributeValue, Long actualValue) {
        boolean applyIncentive = false;
        int compareVal = actualValue.compareTo(attributeValue);
        switch (conditionType) {
            case LESSTHAN:
                applyIncentive = compareVal < 0;
            break;
            case EQUAL:
                applyIncentive = compareVal == 0;
            break;
            case NOT_EQUAL:
                applyIncentive = compareVal != 0;
            break;
            case GRETERTHAN:
                applyIncentive = compareVal > 0;
            break;
            default:
                applyIncentive = false;
            break;
        }
        return applyIncentive;
    }
}
