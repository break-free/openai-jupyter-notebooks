
package org.apache.fineract.infrastructure.core.config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;
@Component
@ConditionalOnClass(name = "org.apache.coyote.http11.Http11Nio2Protocol")
public class TomcatConnectorCustomizer implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        ((TomcatServletWebServerFactory) factory).setProtocol("org.apache.coyote.http11.Http11Nio2Protocol");
    }
}
