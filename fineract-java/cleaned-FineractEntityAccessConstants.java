
package org.apache.fineract.infrastructure.entityaccess;
public final class FineractEntityAccessConstants {
    private FineractEntityAccessConstants() {
    }
    public static final String GLOBAL_CONFIG_FOR_OFFICE_SPECIFIC_PRODUCTS = "office-specific-products-enabled";
    public static final String GLOBAL_CONFIG_FOR_RESTRICT_PRODUCTS_TO_USER_OFFICE = "restrict-products-to-user-office";
    public static final String ENTITY_ACCESS_CODENAME = "Entity to Entity Access Types";
    public enum EntityAccessJSONinputParams {
        ENTITY_TYPE("entityType"), ENTITY_ID("entityId"), ENTITY_ACCESS_TYPE_ID("entityAccessTypeId"), SECOND_ENTITY_TYPE(
                "secondEntityType"), SECOND_ENTITY_ID("secondEntityId");
        private final String value;
        EntityAccessJSONinputParams(final String value) {
            this.value = value;
        }
        public String getValue() {
            return this.value;
        }
    }
}
