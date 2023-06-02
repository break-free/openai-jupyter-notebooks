
package org.apache.fineract.infrastructure.core.data;
public class ApiErrorMessageArg {
    private Object value;
    public static ApiErrorMessageArg from(final Object object) {
        return new ApiErrorMessageArg(object);
    }
    public ApiErrorMessageArg(final Object object) {
        this.value = object;
    }
    public Object getValue() {
        return this.value;
    }
    public void setValue(final Object value) {
        this.value = value;
    }
}
