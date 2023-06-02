
package org.apache.fineract.portfolio.client.serialization;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.infrastructure.core.exception.InvalidJsonException;
import org.apache.fineract.infrastructure.core.serialization.AbstractFromApiJsonDeserializer;
import org.apache.fineract.infrastructure.core.serialization.FromApiJsonDeserializer;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.apache.fineract.portfolio.client.command.ClientIdentifierCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public final class ClientIdentifierCommandFromApiJsonDeserializer extends AbstractFromApiJsonDeserializer<ClientIdentifierCommand> {
    private final Set<String> supportedParameters = new HashSet<>(Arrays.asList("documentTypeId", "documentKey", "status", "description"));
    private final FromJsonHelper fromApiJsonHelper;
    @Autowired
    public ClientIdentifierCommandFromApiJsonDeserializer(final FromJsonHelper fromApiJsonHelper) {
        this.fromApiJsonHelper = fromApiJsonHelper;
    }
    @Override
    public ClientIdentifierCommand commandFromApiJson(final String json) {
        if (StringUtils.isBlank(json)) {
            throw new InvalidJsonException();
        }
        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, this.supportedParameters);
        final JsonElement element = this.fromApiJsonHelper.parse(json);
        final Long documentTypeId = this.fromApiJsonHelper.extractLongNamed("documentTypeId", element);
        final String documentKey = this.fromApiJsonHelper.extractStringNamed("documentKey", element);
        final String documentDescription = this.fromApiJsonHelper.extractStringNamed("documentDescription", element);
        final String statusString = this.fromApiJsonHelper.extractStringNamed("status", element);
        return new ClientIdentifierCommand(documentTypeId, documentKey, statusString, documentDescription);
    }
}
