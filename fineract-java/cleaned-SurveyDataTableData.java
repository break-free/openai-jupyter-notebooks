
package org.apache.fineract.infrastructure.survey.data;
import org.apache.fineract.infrastructure.dataqueries.data.DatatableData;
public final class SurveyDataTableData {
    @SuppressWarnings("unused")
    private final DatatableData datatableData;
    @SuppressWarnings("unused")
    private final boolean enabled;
    public static SurveyDataTableData create(final DatatableData datatableData, final boolean enabled) {
        return new SurveyDataTableData(datatableData, enabled);
    }
    private SurveyDataTableData(final DatatableData datatableData, final boolean enabled) {
        this.datatableData = datatableData;
        this.enabled = enabled;
    }
}
