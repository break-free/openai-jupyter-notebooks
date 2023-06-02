
package org.apache.fineract.adhocquery.api;
import java.util.HashSet;
import java.util.Set;
public enum AdHocJsonInputParams {
    ID("id"), NAME("name"), QUERY("query"), TABLENAME("tableName"), TABLEFIELDS("tableFields"), ISACTIVE("isActive"), REPORT_RUN_FREQUENCY(
            "reportRunFrequency"), REPORT_RUN_EVERY("reportRunEvery"), EMAIL("email");
    private final String value;
    AdHocJsonInputParams(final String value) {
        this.value = value;
    }
    private static final Set<String> values = new HashSet<>();
    static {
        for (final AdHocJsonInputParams type : AdHocJsonInputParams.values()) {
            values.add(type.value);
        }
    }
    public static Set<String> getAllValues() {
        return values;
    }
    @Override
    public String toString() {
        return name().toString().replaceAll("_", " ");
    }
    public String getValue() {
        return this.value;
    }
}
