
package org.apache.fineract.portfolio.client.domain;
public enum LegalForm {
    PERSON(1, "legalFormType.person"),
    ENTITY(2, "legalFormType.entity");
    private final Integer value;
    private final String code;
    LegalForm(final Integer value, final String code) {
        this.value = value;
        this.code = code;
    }
    public Integer getValue() {
        return this.value;
    }
    public String getCode() {
        return this.code;
    }
    public static LegalForm fromInt(final Integer type) {
        LegalForm legalForm = null;
        switch (type) {
            case 1:
                legalForm = LegalForm.PERSON;
            break;
            case 2:
                legalForm = LegalForm.ENTITY;
            break;
        }
        return legalForm;
    }
    public boolean isPerson() {
        return this.value.equals(LegalForm.PERSON.getValue());
    }
    public boolean isEntity() {
        return this.value.equals(LegalForm.ENTITY.getValue());
    }
}
