
package org.apache.fineract.portfolio.group.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class ClientNotInGroupException extends AbstractPlatformDomainRuleException {
    public ClientNotInGroupException(final Long clientId, final Long groupId) {
        this("group.client.not.in.group", "Client with identifier " + clientId + " is not in Group with identifier " + groupId, clientId,
                groupId);
    }
    public ClientNotInGroupException(final String postFix, final String defaultUserMessage, final Object... defaultUserMessageArgs) {
        super("error.msg." + postFix, defaultUserMessage, defaultUserMessageArgs);
    }
}
