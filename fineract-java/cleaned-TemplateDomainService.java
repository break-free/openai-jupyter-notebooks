
package org.apache.fineract.template.service;
import java.util.List;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.template.domain.Template;
import org.apache.fineract.template.domain.TemplateEntity;
import org.apache.fineract.template.domain.TemplateType;
public interface TemplateDomainService {
    List<Template> getAll();
    List<Template> getAllByEntityAndType(TemplateEntity entity, TemplateType type);
    Template findOneById(Long id);
    Template updateTemplate(Template template);
    CommandProcessingResult createTemplate(JsonCommand command);
    CommandProcessingResult updateTemplate(Long templateId, JsonCommand command);
    CommandProcessingResult removeTemplate(Long templateId);
}
