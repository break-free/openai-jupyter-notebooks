
package org.apache.fineract.template.domain;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
public class TemplateTypeSerializer extends JsonSerializer<TemplateType> {
    @Override
    public void serialize(final TemplateType value, final JsonGenerator generator,
            @SuppressWarnings("unused") final SerializerProvider provider) throws IOException, JsonProcessingException {
        generator.writeStartObject();
        generator.writeFieldName("id");
        generator.writeNumber(value.getId());
        generator.writeFieldName("name");
        generator.writeString(value.getName());
        generator.writeEndObject();
    }
}
