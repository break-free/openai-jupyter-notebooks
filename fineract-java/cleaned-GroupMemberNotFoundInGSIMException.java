
package org.apache.fineract.portfolio.group.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class GroupMemberNotFoundInGSIMException extends AbstractPlatformDomainRuleException {
    public GroupMemberNotFoundInGSIMException(final Long clientId) {
        super("error.msg.Group.member.not.found.in.linked.gsim", "The client with id `" + clientId + "` is not present in GSIM", clientId);
    }
}
