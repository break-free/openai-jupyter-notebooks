
package org.apache.fineract.infrastructure.dataqueries.service;
import java.util.List;
import org.apache.fineract.infrastructure.dataqueries.data.GenericResultsetData;
import org.apache.fineract.infrastructure.dataqueries.data.ResultsetColumnHeaderData;
public interface GenericDataService {
    GenericResultsetData fillGenericResultSet(String sql);
    String generateJsonFromGenericResultsetData(GenericResultsetData grs);
    String replace(String str, String pattern, String replace);
    String wrapSQL(String sql);
    List<ResultsetColumnHeaderData> fillResultsetColumnHeaders(String datatable);
}
