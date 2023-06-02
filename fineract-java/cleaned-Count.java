
package org.apache.fineract.infrastructure.bulkimport.data;
public final class Count {
    private Integer successCount;
    private Integer errorCount;
    public static Count instance(final Integer successCount, final Integer errorCount) {
        return new Count(successCount, errorCount);
    }
    private Count(final Integer successCount, final Integer errorCount) {
        this.successCount = successCount;
        this.errorCount = errorCount;
    }
    public Integer getSuccessCount() {
        return successCount;
    }
    public Integer getErrorCount() {
        return errorCount;
    }
}
