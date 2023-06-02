
package org.apache.fineract.module.example;
import org.apache.fineract.infrastructure.core.config.FineractProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
@EnableConfigurationProperties({ FineractProperties.class })
@ComponentScan("com.acmecorp.fineract.foo")
public class TestFooConfiguration {}
