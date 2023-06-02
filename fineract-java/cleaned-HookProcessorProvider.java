
package org.apache.fineract.infrastructure.hooks.processor;
import static org.apache.fineract.infrastructure.hooks.api.HookApiConstants.elasticSearchTemplateName;
import static org.apache.fineract.infrastructure.hooks.api.HookApiConstants.httpSMSTemplateName;
import static org.apache.fineract.infrastructure.hooks.api.HookApiConstants.smsTemplateName;
import static org.apache.fineract.infrastructure.hooks.api.HookApiConstants.webTemplateName;
import org.apache.fineract.infrastructure.hooks.domain.Hook;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
@Service
public class HookProcessorProvider implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    public HookProcessor getProcessor(final Hook hook) {
        HookProcessor processor;
        final String templateName = hook.getHookTemplate().getName();
        if (templateName.equalsIgnoreCase(smsTemplateName)) {
            processor = this.applicationContext.getBean("twilioHookProcessor", TwilioHookProcessor.class);
        } else if (templateName.equals(webTemplateName)) {
            processor = this.applicationContext.getBean("webHookProcessor", WebHookProcessor.class);
        } else if (templateName.equals(elasticSearchTemplateName)) {
            processor = this.applicationContext.getBean("elasticSearchHookProcessor", ElasticSearchHookProcessor.class);
        } else if (templateName.equals(httpSMSTemplateName)) {
            processor = this.applicationContext.getBean("messageGatewayHookProcessor", MessageGatewayHookProcessor.class);
        } else {
            processor = null;
        }
        return processor;
    }
}
