
package org.apache.fineract.infrastructure.core.serialization;
public interface FromApiJsonDeserializer<T> {
    T commandFromApiJson(String json);
}
