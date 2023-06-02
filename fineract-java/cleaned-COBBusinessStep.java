
package org.apache.fineract.cob;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
public interface COBBusinessStep<T extends AbstractPersistableCustom> {
    T execute(T input);
    String getEnumStyledName();
    String getHumanReadableName();
}
