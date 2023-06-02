
package org.apache.fineract.interoperation.api;
import static org.apache.fineract.interoperation.util.InteropUtil.ENTITY_NAME_IDENTIFIER;
import static org.apache.fineract.interoperation.util.InteropUtil.ENTITY_NAME_QUOTE;
import static org.apache.fineract.interoperation.util.InteropUtil.ENTITY_NAME_REQUEST;
import static org.apache.fineract.interoperation.util.InteropUtil.ENTITY_NAME_TRANSFER;
import static org.apache.fineract.interoperation.util.InteropUtil.ROOT_PATH;
import org.apache.fineract.commands.domain.CommandWrapper;
import org.apache.fineract.interoperation.domain.InteropIdentifierType;
import org.apache.fineract.interoperation.domain.InteropTransferActionType;
public class InteropWrapperBuilder {
    private String actionName;
    private String entityName;
    private String href;
    private String json = "{}";
    public CommandWrapper build() {
        return new CommandWrapper(null, null, null, null, null, actionName, entityName, null, null, href, json, null, null, null, null,
                null);
    }
    public InteropWrapperBuilder withJson(final String json) {
        this.json = json;
        return this;
    }
    public InteropWrapperBuilder registerAccountIdentifier(InteropIdentifierType idType, String idValue, String subIdOrType) {
        this.actionName = "CREATE";
        this.entityName = ENTITY_NAME_IDENTIFIER;
        this.href = "/" + ROOT_PATH + "/parties/" + idType + "/" + idValue + "/" + (subIdOrType == null ? " " : subIdOrType);
        return this;
    }
    public InteropWrapperBuilder deleteAccountIdentifier(InteropIdentifierType idType, String idValue, String subIdOrType) {
        this.actionName = "DELETE";
        this.entityName = ENTITY_NAME_IDENTIFIER;
        this.href = "/" + ROOT_PATH + "/parties/" + idType + "/" + idValue + "/" + (subIdOrType == null ? " " : subIdOrType);
        return this;
    }
    public InteropWrapperBuilder createTransactionRequest() {
        this.actionName = "CREATE";
        this.entityName = ENTITY_NAME_REQUEST;
        this.href = "/" + ROOT_PATH + "/requests";
        return this;
    }
    public InteropWrapperBuilder createQuotes() {
        this.actionName = "CREATE";
        this.entityName = ENTITY_NAME_QUOTE;
        this.href = "/" + ROOT_PATH + "/quotes";
        return this;
    }
    public InteropWrapperBuilder performTransfer(InteropTransferActionType action) {
        this.actionName = action.name();
        this.entityName = ENTITY_NAME_TRANSFER;
        this.href = "/" + ROOT_PATH + "/transfers";
        return this;
    }
}
