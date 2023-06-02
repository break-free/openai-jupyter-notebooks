
package org.apache.fineract.infrastructure.core.serialization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class CommandSerializerDefaultToJson implements CommandSerializer {
    private final ExcludeNothingWithPrettyPrintingOffJsonSerializerGoogleGson excludeNothingWithPrettyPrintingOff;
    @Autowired
    public CommandSerializerDefaultToJson(
            final ExcludeNothingWithPrettyPrintingOffJsonSerializerGoogleGson excludeNothingWithPrettyPrintingOff) {
        this.excludeNothingWithPrettyPrintingOff = excludeNothingWithPrettyPrintingOff;
    }
    @Override
    public String serializeCommandToJson(final Object command) {
        return this.excludeNothingWithPrettyPrintingOff.serialize(command);
    }
}
