
package org.apache.fineract.infrastructure.core.serialization;
public abstract class AbstractFromApiJsonDeserializer<T> implements FromApiJsonDeserializer<T> {
    @Override
    public abstract T commandFromApiJson(String json);
}
