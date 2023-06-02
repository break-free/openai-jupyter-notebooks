
package org.apache.fineract.commands.provider;
import com.google.common.base.Preconditions;
import java.util.HashMap;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.exception.UnsupportedCommandException;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
@Component
@NoArgsConstructor
@Slf4j
public class CommandHandlerProvider implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    private HashMap<String, String> registeredHandlers;
    public NewCommandSourceHandler getHandler(final String entity, final String action) {
        Preconditions.checkArgument(StringUtils.isNoneEmpty(entity), "An entity must be given!");
        Preconditions.checkArgument(StringUtils.isNoneEmpty(action), "An action must be given!");
        final String key = entity + "|" + action;
        if (!this.registeredHandlers.containsKey(key)) {
            throw new UnsupportedCommandException(key);
        }
        return (NewCommandSourceHandler) this.applicationContext.getBean(this.registeredHandlers.get(key));
    }
    private void initializeHandlerRegistry() {
        if (this.registeredHandlers == null) {
            this.registeredHandlers = new HashMap<>();
            final String[] commandHandlerBeans = this.applicationContext.getBeanNamesForAnnotation(CommandType.class);
            if (ArrayUtils.isNotEmpty(commandHandlerBeans)) {
                for (final String commandHandlerName : commandHandlerBeans) {
                    log.debug("Register command handler '{}' ...", commandHandlerName);
                    final CommandType commandType = this.applicationContext.findAnnotationOnBean(commandHandlerName, CommandType.class);
                    try {
                        this.registeredHandlers.put(commandType.entity() + "|" + commandType.action(), commandHandlerName);
                    } catch (final Throwable th) {
                        log.error("Unable to register command handler '{}'!", commandHandlerName, th);
                    }
                }
            }
        }
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        this.initializeHandlerRegistry();
    }
}
