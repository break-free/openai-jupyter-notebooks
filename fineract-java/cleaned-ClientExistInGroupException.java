
package org.apache.fineract.portfolio.group.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class ClientExistInGroupException extends AbstractPlatformDomainRuleException {
    public ClientExistInGroupException(final Long clientId, final Long groupId) {
        super("error.msg.group.client.exist.in.group",
                "Client with identifier " + clientId + " is already exists in Group with identifier " + groupId, clientId, groupId);
    }
}
