
package org.apache.fineract.interoperation.data;
import static org.apache.fineract.interoperation.util.InteropUtil.PARAM_KEY;
import static org.apache.fineract.interoperation.util.InteropUtil.PARAM_VALUE;
import com.google.gson.JsonObject;
import java.util.Arrays;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
public class ExtensionData {
    public static final List<String> PARAMS = List.copyOf(Arrays.asList(PARAM_KEY, PARAM_VALUE));
    @NotNull
    private final String key;
    private String value;
    public ExtensionData(String key, String value) {
        this.key = key;
        this.value = value;
    }
    public String getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public static ExtensionData validateAndParse(DataValidatorBuilder dataValidator, JsonObject element, FromJsonHelper jsonHelper) {
        if (element == null) {
            return null;
        }
        jsonHelper.checkForUnsupportedParameters(element, PARAMS);
        String key = jsonHelper.extractStringNamed(PARAM_KEY, element);
        DataValidatorBuilder dataValidatorCopy = dataValidator.reset().parameter(PARAM_KEY).value(key).notBlank();
        String value = jsonHelper.extractStringNamed(PARAM_VALUE, element);
        dataValidator.merge(dataValidatorCopy);
        return dataValidator.hasError() ? null : new ExtensionData(key, value);
    }
}
