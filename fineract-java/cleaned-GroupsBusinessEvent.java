
package org.apache.fineract.portfolio.businessevent.domain.group;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.businessevent.domain.AbstractBusinessEvent;
public abstract class GroupsBusinessEvent extends AbstractBusinessEvent<CommandProcessingResult> {
    public GroupsBusinessEvent(CommandProcessingResult value) {
        super(value);
    }
}
