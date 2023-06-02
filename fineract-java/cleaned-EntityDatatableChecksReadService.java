
package org.apache.fineract.infrastructure.dataqueries.service;
import java.util.List;
import org.apache.fineract.infrastructure.core.service.Page;
import org.apache.fineract.infrastructure.core.service.SearchParameters;
import org.apache.fineract.infrastructure.dataqueries.data.DatatableData;
import org.apache.fineract.infrastructure.dataqueries.data.EntityDataTableChecksData;
import org.apache.fineract.infrastructure.dataqueries.data.EntityDataTableChecksTemplateData;
public interface EntityDatatableChecksReadService {
    EntityDataTableChecksTemplateData retrieveTemplate();
    List<DatatableData> retrieveTemplates(Long status, String entity, Long productId);
    Page<EntityDataTableChecksData> retrieveAll(SearchParameters searchParameters, Long status, String entity, Long productLoanId);
}
