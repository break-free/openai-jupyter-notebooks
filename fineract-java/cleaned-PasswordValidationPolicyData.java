
package org.apache.fineract.useradministration.data;
import java.io.Serializable;
public class PasswordValidationPolicyData implements Serializable {
    @SuppressWarnings("unused")
    private final Long id;
    @SuppressWarnings("unused")
    private final String description;
    @SuppressWarnings("unused")
    private final boolean active;
    @SuppressWarnings("unused")
    private final String key;
    public PasswordValidationPolicyData(final Long id, final Boolean active, final String description, final String key) {
        this.id = id;
        this.active = active;
        this.description = description;
        this.key = key;
    }
}
