
package org.apache.fineract.infrastructure.bulkimport.importhandler.helper;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
public class EnumOptionDataIdSerializer implements JsonSerializer<EnumOptionData> {
    @Override
    public JsonElement serialize(EnumOptionData src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getId());
    }
}
