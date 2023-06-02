
package org.apache.fineract.infrastructure.core.serialization;
import java.util.Collection;
import java.util.Set;
import org.apache.fineract.infrastructure.core.service.Page;
public interface ToApiJsonSerializer<T> {
    String serialize(Object object);
    String serializePretty(boolean prettyOn, Object object);
    String serializeResult(Object object);
    String serialize(ApiRequestJsonSerializationSettings settings, Collection<T> collection);
    String serialize(ApiRequestJsonSerializationSettings settings, T single);
    String serialize(ApiRequestJsonSerializationSettings settings, Page<T> singleObject);
    String serialize(ApiRequestJsonSerializationSettings settings, Collection<T> collection, Set<String> supportedResponseParameters);
    String serialize(ApiRequestJsonSerializationSettings settings, T single, Set<String> supportedResponseParameters);
    String serialize(ApiRequestJsonSerializationSettings settings, Page<T> singleObject, Set<String> supportedResponseParameters);
}
