
package org.apache.fineract.infrastructure.security.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.infrastructure.security.constants.TwoFactorConfigurationConstants;
@Entity
@Table(name = "twofactor_configuration", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }, name = "name_UNIQUE") })
public class TwoFactorConfiguration extends AbstractPersistableCustom {
    @Column(name = "name", nullable = false, length = 32)
    private String name;
    @Column(name = "value", nullable = true, length = 1024)
    private String value;
    public String getName() {
        return name;
    }
    public String getStringValue() {
        return value;
    }
    public Boolean getBooleanValue() {
        return BooleanUtils.toBooleanObject(value);
    }
    public Integer getIntegerValue() {
        try {
            return NumberUtils.createInteger(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    public Object getObjectValue() {
        if (TwoFactorConfigurationConstants.NUMBER_PARAMETERS.contains(name)) {
            return getIntegerValue();
        }
        if (TwoFactorConfigurationConstants.BOOLEAN_PARAMETERS.contains(name)) {
            return getBooleanValue();
        }
        return getStringValue();
    }
    public void setStringValue(String value) {
        this.value = value;
    }
    public void setBooleanValue(boolean value) {
        this.value = String.valueOf(value);
    }
    public void setIntegerValue(long value) {
        this.value = String.valueOf(value);
    }
}
