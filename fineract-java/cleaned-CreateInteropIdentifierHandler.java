
package org.apache.fineract.interoperation.handler;
import static org.apache.fineract.interoperation.util.InteropUtil.ENTITY_NAME_IDENTIFIER;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import java.util.List;
import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.interoperation.domain.InteropIdentifierType;
import org.apache.fineract.interoperation.service.InteropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
@Service
@CommandType(entity = ENTITY_NAME_IDENTIFIER, action = "CREATE")
public class CreateInteropIdentifierHandler implements NewCommandSourceHandler {
    private final InteropService interopService;
    @Autowired
    public CreateInteropIdentifierHandler(InteropService interopService) {
        this.interopService = interopService;
    }
    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        List<String> split = Splitter.on('/').splitToList(command.getUrl());
        int length = split.size();
        String subIdOrType = Strings.emptyToNull(StringUtils.trimWhitespace(split.get(length - 1)));
        String idValue = split.get(length - 2);
        InteropIdentifierType idType = InteropIdentifierType.valueOf(split.get(length - 3).toUpperCase());
        return this.interopService.registerAccountIdentifier(idType, idValue, subIdOrType, command);
    }
}
