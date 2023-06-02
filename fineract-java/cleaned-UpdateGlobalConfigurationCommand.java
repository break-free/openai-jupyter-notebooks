
package org.apache.fineract.infrastructure.configuration.command;
import java.util.Map;
public class UpdateGlobalConfigurationCommand {
    private final Map<String, Boolean> globalConfiguration;
    public UpdateGlobalConfigurationCommand(final Map<String, Boolean> globalConfigurationMap) {
        this.globalConfiguration = globalConfigurationMap;
    }
    public Map<String, Boolean> getGlobalConfiguration() {
        return this.globalConfiguration;
    }
}
