
package org.apache.fineract.portfolio.businessevent.domain.group;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public class GroupsCreateBusinessEvent extends GroupsBusinessEvent {
    public GroupsCreateBusinessEvent(CommandProcessingResult value) {
        super(value);
    }
}
