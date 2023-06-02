
package org.apache.fineract.spm.util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.fineract.spm.data.LookupTableData;
import org.apache.fineract.spm.data.LookupTableEntry;
import org.apache.fineract.spm.domain.LookupTable;
import org.apache.fineract.spm.domain.Survey;
public final class LookupTableMapper {
    private LookupTableMapper() {
    }
    public static List<LookupTableData> map(final List<LookupTable> lookupTables) {
        final Map<String, LookupTableData> lookupTableDataMap = new HashMap<>();
        LookupTableData lookupTableData = null;
        if (lookupTables != null && !lookupTables.isEmpty()) {
            for (LookupTable lookupTable : lookupTables) {
                if ((lookupTableData = lookupTableDataMap.get(lookupTable.getKey())) == null) {
                    lookupTableData = new LookupTableData();
                    lookupTableDataMap.put(lookupTable.getKey(), lookupTableData);
                    lookupTableData.setKey(lookupTable.getKey());
                    lookupTableData.setDescription(lookupTable.getDescription());
                    lookupTableData.setEntries(new ArrayList<LookupTableEntry>());
                }
                lookupTableData.getEntries()
                        .add(new LookupTableEntry(lookupTable.getValueFrom(), lookupTable.getValueTo(), lookupTable.getScore()));
            }
            return List.copyOf(lookupTableDataMap.values());
        }
        return List.of();
    }
    public static List<LookupTable> map(final LookupTableData lookupTableData, final Survey survey) {
        final List<LookupTable> lookupTables = new ArrayList<>();
        final List<LookupTableEntry> entries = lookupTableData.getEntries();
        if (entries != null) {
            for (LookupTableEntry entry : entries) {
                final LookupTable lookupTable = new LookupTable();
                lookupTables.add(lookupTable);
                lookupTable.setSurvey(survey);
                lookupTable.setKey(lookupTableData.getKey());
                lookupTable.setDescription(lookupTableData.getDescription());
                lookupTable.setValueFrom(entry.getValueFrom());
                lookupTable.setValueTo(entry.getValueTo());
                lookupTable.setScore(entry.getScore());
            }
        }
        return lookupTables;
    }
}
