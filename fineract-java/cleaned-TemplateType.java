
package org.apache.fineract.template.domain;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.SerializedName;
@JsonSerialize(using = TemplateTypeSerializer.class)
public enum TemplateType {
    @SerializedName("Document")
    DOCUMENT(0, "Document"), @SerializedName("SMS")
    SMS(2, "SMS");
    private final int id;
    private final String name;
    TemplateType(final int id, final String name) {
        this.id = id;
        this.name = name;
    }
    public int getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
}
