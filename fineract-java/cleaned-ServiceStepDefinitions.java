
package org.apache.fineract.module.service;
import static org.assertj.core.api.Assertions.assertThat;
import io.cucumber.java8.En;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
public class ServiceStepDefinitions implements En {
    private Class interfaceClass;
    private Class implementationClass;
    private ApplicationContextRunner contextRunner;
    public ServiceStepDefinitions() {
        Given("/^An auto configuration (.*) and a service configuration (.*)$/",
                (String autoConfigurationClassName, String configurationClassName) -> {
                    contextRunner = new ApplicationContextRunner()
                            .withConfiguration(AutoConfigurations.of(Class.forName(autoConfigurationClassName)))
                            .withUserConfiguration(Class.forName(configurationClassName.trim()));
                });
        When("/^The user retrieves the service of interface class (.*)$/", (String interfaceClassName) -> {
            contextRunner.run((ctx) -> {
                this.interfaceClass = Class.forName(interfaceClassName.trim());
                assertThat(this.interfaceClass.isInterface()).isTrue();
                assertThat(ctx).hasSingleBean(this.interfaceClass);
                this.implementationClass = ctx.getBean(interfaceClass).getClass();
            });
        });
        Then("/^The service class should match (.*)$/", (String serviceClassName) -> {
            assertThat(Class.forName(serviceClassName.trim())).isEqualTo(this.implementationClass);
        });
    }
}
