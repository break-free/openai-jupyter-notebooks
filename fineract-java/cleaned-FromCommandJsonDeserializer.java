
package org.apache.fineract.infrastructure.core.serialization;
public interface FromCommandJsonDeserializer<T> {
    T commandFromCommandJson(String json);
    T commandFromCommandJson(Long resourceId, String json);
    T commandFromCommandJson(Long resourceId, String json, boolean makerCheckerApproval);
}
