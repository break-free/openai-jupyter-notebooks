
package org.apache.fineract.portfolio.group.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class GroupMemberCountNotInPermissibleRangeException extends AbstractPlatformDomainRuleException {
    public GroupMemberCountNotInPermissibleRangeException(final Long groupId, final Integer minClients, final Integer maxClients) {
        super("error.msg.group.members.count.must.be.in.permissible.range",
                "Number of members in the group with Id " + groupId + " should be between " + minClients + " and " + maxClients, groupId,
                minClients, maxClients);
    }
}
