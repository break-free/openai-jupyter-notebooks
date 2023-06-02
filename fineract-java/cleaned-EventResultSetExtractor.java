
package org.apache.fineract.infrastructure.hooks.data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
public class EventResultSetExtractor implements ResultSetExtractor<List<Grouping>> {
    @Override
    public List<Grouping> extractData(final ResultSet rs) throws SQLException, DataAccessException {
        final List<Grouping> groupings = new ArrayList<>();
        final Map<String, Map<String, List<String>>> groupToEntityMapping = new HashMap<>();
        Map<String, List<String>> entityToActionMapping = new HashMap<>();
        while (rs.next()) {
            final String groupingName = rs.getString("grouping");
            final String entityName = rs.getString("entity_name");
            final String actionName = rs.getString("action_name");
            Map<String, List<String>> entities = groupToEntityMapping.get(groupingName);
            List<String> actions = entityToActionMapping.get(entityName);
            if (entities == null) {
                entityToActionMapping = new HashMap<>();
            }
            if (actions == null) {
                actions = new ArrayList<>();
            }
            actions.add(actionName);
            entityToActionMapping.put(entityName, actions);
            if (entities == null) {
                entities = new HashMap<>();
            }
            entities.putAll(entityToActionMapping);
            groupToEntityMapping.put(groupingName, entities);
        }
        for (final Map.Entry<String, Map<String, List<String>>> groupingEntry : groupToEntityMapping.entrySet()) {
            final List<Entity> entities = new ArrayList<>();
            final Grouping group = new Grouping();
            group.setName(groupingEntry.getKey());
            for (final Map.Entry<String, List<String>> entityEntry : groupingEntry.getValue().entrySet()) {
                final Entity entity = new Entity();
                entity.setName(entityEntry.getKey());
                final List<String> actions = new ArrayList<>(entityEntry.getValue());
                Collections.sort(actions);
                entity.setActions(actions);
                entities.add(entity);
            }
            entities.sort(Comparator.comparing(Entity::getName));
            group.setEntities(entities);
            groupings.add(group);
        }
        groupings.sort(Comparator.comparing(Grouping::getName));
        return groupings;
    }
}
