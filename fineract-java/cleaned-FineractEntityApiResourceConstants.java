
package org.apache.fineract.infrastructure.entityaccess.api;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
public final class FineractEntityApiResourceConstants {
    private FineractEntityApiResourceConstants() {
    }
    public static final String FINERACT_ENTITY_RESOURCE_NAME = "FineractEntity";
    public static final String mappingTypes = "mappingTypes";
    public static final String mapId = "mapId";
    public static final String relId = "relId";
    public static final String fromEnityType = "fromId";
    public static final String toEntityType = "toId";
    public static final String startDate = "startDate";
    public static final String endDate = "endDate";
    public static final String LOCALE = "locale";
    public static final String DATE_FORMAT = "dateFormat";
    public static final String OFFICE_ACCESS_TO_LOAN_PRODUCTS = " office_access_to_loan_products ";
    public static final String OFFICE_ACCESS_TO_SAVINGS_PRODUCTS = " office_access_to_savings_products ";
    public static final String OFFICE_ACCESS_TO_CHARGES_FEES = " office_access_to_fees/charges ";
    public static final String ROLE_ACCESS_TO_LOAN_PRODUCTS = " role_access_to_loan_products ";
    public static final String ROLE_ACCESS_TO_SAVINGS_PRODUCTS = " role_access_to_savings_products ";
    static final Set<String> RESPONSE_DATA_PARAMETERS = new HashSet<>(Arrays.asList(mappingTypes));
    static final Set<String> FETCH_ENTITY_TO_ENTITY_MAPPINGS = new HashSet<>(Arrays.asList(mapId, relId, fromEnityType, toEntityType));
}
