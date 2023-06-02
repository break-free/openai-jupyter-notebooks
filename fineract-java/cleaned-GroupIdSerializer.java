
package org.apache.fineract.infrastructure.bulkimport.importhandler.helper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Collection;
import org.apache.fineract.portfolio.group.data.GroupGeneralData;
public class GroupIdSerializer implements JsonSerializer<Collection<GroupGeneralData>> {
    @Override
    public JsonElement serialize(Collection<GroupGeneralData> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray groupIdJsonArray = new JsonArray();
        for (GroupGeneralData group : src) {
            JsonElement groupIdElement = new JsonPrimitive(group.getId().toString());
            groupIdJsonArray.add(groupIdElement);
        }
        return groupIdJsonArray;
    }
}
