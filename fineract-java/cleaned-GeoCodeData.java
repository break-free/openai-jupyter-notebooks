
package org.apache.fineract.interoperation.data;
import static org.apache.fineract.interoperation.util.InteropUtil.PARAM_LATITUDE;
import static org.apache.fineract.interoperation.util.InteropUtil.PARAM_LONGITUDE;
import com.google.gson.JsonObject;
import java.util.Arrays;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
public class GeoCodeData {
    public static final List<String> PARAMS = List.copyOf(Arrays.asList(PARAM_LATITUDE, PARAM_LONGITUDE));
    @NotNull
    private final String latitude;
    @NotNull
    private final String longitude;
    public GeoCodeData(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public String getLatitude() {
        return latitude;
    }
    public String getLongitude() {
        return longitude;
    }
    public static GeoCodeData validateAndParse(DataValidatorBuilder dataValidator, JsonObject element, FromJsonHelper jsonHelper) {
        if (element == null) {
            return null;
        }
        jsonHelper.checkForUnsupportedParameters(element, PARAMS);
        String latitude = jsonHelper.extractStringNamed(PARAM_LATITUDE, element);
        DataValidatorBuilder dataValidatorCopy = dataValidator.reset().parameter(PARAM_LATITUDE).value(latitude).notBlank();
        String longitude = jsonHelper.extractStringNamed(PARAM_LONGITUDE, element);
        dataValidatorCopy = dataValidatorCopy.reset().parameter(PARAM_LONGITUDE).value(longitude).notBlank();
        dataValidator.merge(dataValidatorCopy);
        return dataValidator.hasError() ? null : new GeoCodeData(latitude, longitude);
    }
}
