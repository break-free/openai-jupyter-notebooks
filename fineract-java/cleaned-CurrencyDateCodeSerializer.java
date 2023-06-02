
package org.apache.fineract.infrastructure.bulkimport.importhandler.helper;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import org.apache.fineract.organisation.monetary.data.CurrencyData;
public class CurrencyDateCodeSerializer implements JsonSerializer<CurrencyData> {
    @Override
    public JsonElement serialize(CurrencyData src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.code());
    }
}
