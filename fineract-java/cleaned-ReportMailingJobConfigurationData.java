
package org.apache.fineract.infrastructure.reportmailingjob.data;
public final class ReportMailingJobConfigurationData {
    private final int id;
    private final String name;
    private final String value;
    private ReportMailingJobConfigurationData(final int id, final String name, final String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }
    public static ReportMailingJobConfigurationData newInstance(final int id, final String name, final String value) {
        return new ReportMailingJobConfigurationData(id, name, value);
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getValue() {
        return value;
    }
}
