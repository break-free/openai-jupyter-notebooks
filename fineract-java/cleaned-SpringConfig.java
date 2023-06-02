
package org.apache.fineract.infrastructure.core.config;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.security.core.context.SecurityContextHolder;
@Configuration
public class SpringConfig {
    @Bean
    public SimpleApplicationEventMulticaster applicationEventMulticaster() {
        SimpleApplicationEventMulticaster saem = new SimpleApplicationEventMulticaster();
        saem.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return saem;
    }
    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean() {
        MethodInvokingFactoryBean mifb = new MethodInvokingFactoryBean();
        mifb.setTargetClass(SecurityContextHolder.class);
        mifb.setTargetMethod("setStrategyName");
        mifb.setArguments("MODE_INHERITABLETHREADLOCAL");
        return mifb;
    }
}
