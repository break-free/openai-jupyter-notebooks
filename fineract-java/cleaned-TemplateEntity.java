
package org.apache.fineract.template.domain;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.SerializedName;
@JsonSerialize(using = TemplateEntitySerializer.class)
public enum TemplateEntity {
    @SerializedName("client")
    CLIENT(0, "client"), @SerializedName("loan")
    LOAN(1, "loan");
    private final int id;
    private final String name;
    TemplateEntity(final int id, final String name) {
        this.id = id;
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public int getId() {
        return this.id;
    }
}
