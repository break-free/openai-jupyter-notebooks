
package org.apache.fineract.organisation.monetary.domain;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.math.MathContext;
import java.math.RoundingMode;
import javax.annotation.PostConstruct;
import org.apache.fineract.infrastructure.configuration.domain.ConfigurationDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class MoneyHelper {
    private static RoundingMode roundingMode = null;
    private static MathContext mathContext;
    private static final int PRECISION = 12;
    private static ConfigurationDomainService staticConfigurationDomainService;
    @Autowired
    private ConfigurationDomainService configurationDomainService;
    @PostConstruct
    @SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
    public void someFunction() {
        staticConfigurationDomainService = configurationDomainService;
    }
    public static RoundingMode getRoundingMode() {
        if (roundingMode == null) {
            roundingMode = RoundingMode.valueOf(staticConfigurationDomainService.getRoundingMode());
        }
        return roundingMode;
    }
    public static MathContext getMathContext() {
        if (mathContext == null) {
            mathContext = new MathContext(PRECISION, getRoundingMode());
        }
        return mathContext;
    }
}
