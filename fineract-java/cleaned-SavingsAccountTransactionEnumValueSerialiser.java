
package org.apache.fineract.infrastructure.bulkimport.importhandler.helper;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import org.apache.fineract.portfolio.savings.data.SavingsAccountTransactionEnumData;
public class SavingsAccountTransactionEnumValueSerialiser implements JsonSerializer<SavingsAccountTransactionEnumData> {
    @Override
    public JsonElement serialize(SavingsAccountTransactionEnumData src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getValue());
    }
}
