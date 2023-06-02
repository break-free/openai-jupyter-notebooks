
package org.apache.fineract.infrastructure.core.serialization;
public interface CommandSerializer {
    String serializeCommandToJson(Object command);
}
