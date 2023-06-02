
package org.apache.fineract.infrastructure.creditbureau.data;
public final class CreditBureauConfigurationData {
    private final long creditBureauConfigurationId;
    private final String configurationKey;
    private final String value;
    private final long organizationCreditBureauId;
    private final String description;
    private CreditBureauConfigurationData(final long creditBureauConfigurationId, final String configurationKey, final String value,
            final long organizationCreditBureauId, final String description) {
        this.creditBureauConfigurationId = creditBureauConfigurationId;
        this.configurationKey = configurationKey;
        this.value = value;
        this.organizationCreditBureauId = organizationCreditBureauId;
        this.description = description;
    }
    public static CreditBureauConfigurationData instance(final long creditBureauConfigurationId, final String configurationKey,
            final String value, final long organizationCreditBureauId, final String description) {
        return new CreditBureauConfigurationData(creditBureauConfigurationId, configurationKey, value, organizationCreditBureauId,
                description);
    }
    public long getCreditBureauConfigurationId() {
        return this.creditBureauConfigurationId;
    }
    public String getConfigurationKey() {
        return this.configurationKey;
    }
    public String getValue() {
        return this.value;
    }
    public long getOrganizationCreditBureauId() {
        return this.organizationCreditBureauId;
    }
    public String getDescription() {
        return this.description;
    }
}
