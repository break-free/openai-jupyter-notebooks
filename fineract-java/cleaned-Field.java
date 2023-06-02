
package org.apache.fineract.infrastructure.hooks.data;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
@SuppressWarnings("unused")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public final class Field implements Serializable {
    private final String fieldName;
    private final String fieldValue;
    private final String fieldType;
    private final Boolean optional;
    private final String placeholder;
    public static Field fromConfig(final String fieldName, final String fieldValue) {
        return new Field(fieldName, fieldValue, null, null, null);
    }
    public static Field fromSchema(final String fieldType, final String fieldName, final Boolean optional, final String placeholder) {
        return new Field(fieldName, null, fieldType, optional, placeholder);
    }
}
