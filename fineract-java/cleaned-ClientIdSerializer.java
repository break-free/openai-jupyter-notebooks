
package org.apache.fineract.infrastructure.bulkimport.importhandler.helper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Collection;
import org.apache.fineract.portfolio.client.data.ClientData;
public class ClientIdSerializer implements JsonSerializer<Collection<ClientData>> {
    @Override
    public JsonElement serialize(Collection<ClientData> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray clientIdJsonArray = new JsonArray();
        for (ClientData client : src) {
            JsonElement clientIdElment = new JsonPrimitive(client.id().toString());
            clientIdJsonArray.add(clientIdElment);
        }
        return clientIdJsonArray;
    }
}
