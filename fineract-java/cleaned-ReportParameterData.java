
package org.apache.fineract.infrastructure.dataqueries.data;
public final class ReportParameterData {
    @SuppressWarnings("unused")
    private final Long id;
    @SuppressWarnings("unused")
    private final Long parameterId;
    @SuppressWarnings("unused")
    private final String parameterName;
    @SuppressWarnings("unused")
    private final String reportParameterName;
    public ReportParameterData(final Long id, final Long parameterId, final String reportParameterName, final String parameterName) {
        this.id = id;
        this.parameterId = parameterId;
        this.parameterName = parameterName;
        this.reportParameterName = reportParameterName;
    }
}
