
package org.apache.fineract.infrastructure.configuration.data;
import java.util.List;
public class GlobalConfigurationData {
    @SuppressWarnings("unused")
    private final List<GlobalConfigurationPropertyData> globalConfiguration;
    public GlobalConfigurationData(final List<GlobalConfigurationPropertyData> globalConfiguration) {
        this.globalConfiguration = globalConfiguration;
    }
}
