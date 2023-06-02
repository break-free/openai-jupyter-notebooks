
package org.apache.fineract;
import java.io.IOException;
import org.apache.fineract.infrastructure.core.boot.AbstractApplicationConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
public class ServerApplication extends SpringBootServletInitializer {
    private static class Configuration extends AbstractApplicationConfiguration {}
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Configuration.class);
    }
    private static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder) {
        return builder.sources(Configuration.class);
    }
    public static void main(String[] args) throws IOException {
        configureApplication(new SpringApplicationBuilder(ServerApplication.class)).run(args);
    }
}
