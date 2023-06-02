
package org.apache.fineract.portfolio.interestratechart;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
public interface InterestIncentiveApiConstants {
    String idParamName = "id";
    String entityTypeParamName = "entityType";
    String attributeNameParamName = "attributeName";
    String conditionTypeParamName = "conditionType";
    String attributeValueParamName = "attributeValue";
    String incentiveTypeparamName = "incentiveType";
    String amountParamName = "amount";
    String deleteParamName = "delete";
    String INCENTIVE_RESOURCE_NAME = "interest.rate.incentives";
    Set<String> INTERESTRATE_INCENTIVE_RESPONSE_DATA_PARAMETERS = new HashSet<>(Arrays.asList(idParamName, entityTypeParamName,
            attributeNameParamName, conditionTypeParamName, attributeValueParamName, incentiveTypeparamName, amountParamName));
}
