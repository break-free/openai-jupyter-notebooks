
package org.apache.fineract.infrastructure.instancemode;
import org.apache.fineract.infrastructure.core.config.FineractProperties;
public final class InstanceModeMock {
    private InstanceModeMock() {
    }
    public static FineractProperties.FineractModeProperties createModeProps(boolean readEnabled, boolean writeEnabled,
            boolean batchWorkerEnabled, boolean batchManagerEnabled) {
        FineractProperties.FineractModeProperties modeProperties = new FineractProperties.FineractModeProperties();
        modeProperties.setReadEnabled(readEnabled);
        modeProperties.setWriteEnabled(writeEnabled);
        modeProperties.setBatchWorkerEnabled(batchWorkerEnabled);
        modeProperties.setBatchManagerEnabled(batchManagerEnabled);
        return modeProperties;
    }
}
