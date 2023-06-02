
package org.apache.fineract.template.handler;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.template.service.TemplateDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@CommandType(entity = "TEMPLATE", action = "CREATE")
public class CreateTemplateCommandHandler implements NewCommandSourceHandler {
    private final TemplateDomainService templateService;
    @Autowired
    public CreateTemplateCommandHandler(final TemplateDomainService templateService) {
        this.templateService = templateService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.templateService.createTemplate(command);
    }
}
