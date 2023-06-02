
package org.apache.fineract.infrastructure.core.api;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
public class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime> {
    @Override
    @SuppressWarnings("unused")
    public JsonElement serialize(final LocalDateTime src, final Type typeOfSrc, final JsonSerializationContext context) {
        JsonArray array = null;
        if (src != null) {
            array = new JsonArray();
            array.add(new JsonPrimitive(src.get(ChronoField.YEAR_OF_ERA)));
            array.add(new JsonPrimitive(src.getMonthValue()));
            array.add(new JsonPrimitive(src.getDayOfMonth()));
            array.add(new JsonPrimitive(src.getHour()));
            array.add(new JsonPrimitive(src.getMinute()));
            array.add(new JsonPrimitive(src.getSecond()));
        }
        return array;
    }
}
