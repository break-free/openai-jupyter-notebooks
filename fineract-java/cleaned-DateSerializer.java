
package org.apache.fineract.infrastructure.bulkimport.importhandler.helper;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class DateSerializer implements JsonSerializer<LocalDate> {
    private final String dateFormat;
    public DateSerializer(String dateFormat) {
        this.dateFormat = dateFormat;
    }
    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern(dateFormat)));
    }
}
