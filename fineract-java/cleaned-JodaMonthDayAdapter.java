
package org.apache.fineract.infrastructure.core.api;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.time.MonthDay;
public class JodaMonthDayAdapter implements JsonSerializer<MonthDay> {
    @Override
    @SuppressWarnings("unused")
    public JsonElement serialize(final MonthDay src, final Type typeOfSrc, final JsonSerializationContext context) {
        JsonArray array = null;
        if (src != null) {
            array = new JsonArray();
            array.add(new JsonPrimitive(src.get(MONTH_OF_YEAR)));
            array.add(new JsonPrimitive(src.getDayOfMonth()));
        }
        return array;
    }
}
