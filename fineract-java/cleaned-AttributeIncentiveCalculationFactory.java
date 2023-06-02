
package org.apache.fineract.portfolio.interestratechart.incentive;
public final class AttributeIncentiveCalculationFactory {
    private AttributeIncentiveCalculationFactory() {
    }
    public static AttributeIncentiveCalculation findAttributeIncentiveCalculation(InterestIncentiveEntityType entityType) {
        AttributeIncentiveCalculation attributeIncentiveCalculation = null;
        switch (entityType) {
            case CUSTOMER:
                attributeIncentiveCalculation = new ClientAttributeIncentiveCalculation();
            break;
            default:
            break;
        }
        return attributeIncentiveCalculation;
    }
}
