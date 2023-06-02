
package org.apache.fineract.infrastructure.campaigns.email.data;
public final class EmailConfigurationData {
    @SuppressWarnings("unused")
    private final Long id;
    private final String name;
    private final String value;
    public static EmailConfigurationData instance(Long id, String name, String value) {
        return new EmailConfigurationData(id, name, value);
    }
    private EmailConfigurationData(Long id, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getValue() {
        return value;
    }
}
