
package org.apache.fineract.infrastructure.core;
import io.cucumber.java8.En;
import org.apache.fineract.infrastructure.core.config.FineractProperties;
import org.springframework.beans.factory.annotation.Autowired;
public class InstanceTypeStepDefinitions implements En {
    @Autowired
    private FineractProperties fineractProperties;
    public InstanceTypeStepDefinitions() {
        Given("Set every Fineract instance type to false", () -> {
            fineractProperties.getMode().setWriteEnabled(false);
            fineractProperties.getMode().setReadEnabled(false);
            fineractProperties.getMode().setBatchWorkerEnabled(false);
            fineractProperties.getMode().setBatchManagerEnabled(false);
        });
        Given("Fineract instance is a write instance", () -> {
            fineractProperties.getMode().setWriteEnabled(true);
        });
        Given("Fineract instance is a read instance", () -> {
            fineractProperties.getMode().setReadEnabled(true);
        });
        Given("Fineract instance is a batch manager instance", () -> {
            fineractProperties.getMode().setBatchManagerEnabled(true);
        });
    }
}
