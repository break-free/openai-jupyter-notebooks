
package org.apache.fineract.portfolio.group.service;
import java.util.Collection;
import org.apache.fineract.portfolio.group.data.GroupLevelData;
public interface GroupLevelReadPlatformService {
    Collection<GroupLevelData> retrieveAllLevels();
}
