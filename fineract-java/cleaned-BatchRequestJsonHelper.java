
package org.apache.fineract.batch.serialization;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import org.apache.fineract.batch.domain.BatchRequest;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.springframework.stereotype.Component;
@Component
public class BatchRequestJsonHelper extends FromJsonHelper {
    public List<BatchRequest> extractList(final String json) {
        final Type listType = new TypeToken<List<BatchRequest>>() {}.getType();
        final List<BatchRequest> requests = super.getGsonConverter().fromJson(json, listType);
        return requests;
    }
}
