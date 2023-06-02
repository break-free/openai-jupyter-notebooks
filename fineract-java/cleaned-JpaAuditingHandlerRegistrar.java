
package org.apache.fineract.infrastructure.core.auditing;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.auditing.CustomAuditingHandler;
public class JpaAuditingHandlerRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        registry.registerBeanDefinition("jpaAuditingHandler", BeanDefinitionBuilder.rootBeanDefinition(CustomAuditingHandler.class)
                .addConstructorArgReference("jpaMappingContext").addConstructorArgReference("auditorAware").getBeanDefinition());
    }
}
