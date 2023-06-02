
package org.apache.fineract.interoperation.data;
import javax.validation.constraints.NotNull;
import org.apache.fineract.interoperation.domain.InteropIdentifier;
import org.apache.fineract.interoperation.domain.InteropIdentifierType;
public class InteropIdentifierData {
    @NotNull
    private final InteropIdentifierType idType;
    @NotNull
    private final String idValue;
    private final String subIdOrType;
    public InteropIdentifierData(@NotNull InteropIdentifierType idType, @NotNull String idValue, String subIdOrType) {
        this.idType = idType;
        this.idValue = idValue;
        this.subIdOrType = subIdOrType;
    }
    protected InteropIdentifierData(@NotNull InteropIdentifierType idType, @NotNull String idValue) {
        this(idType, idValue, null);
    }
    public InteropIdentifierType getIdType() {
        return idType;
    }
    public String getIdValue() {
        return idValue;
    }
    public String getSubIdOrType() {
        return subIdOrType;
    }
    public static InteropIdentifierData build(InteropIdentifier identifier) {
        return new InteropIdentifierData(identifier.getType(), identifier.getValue(), identifier.getSubType());
    }
}
