
package org.apache.fineract.portfolio.fund.data;
import java.io.Serializable;
public final class FundData implements Serializable {
    @SuppressWarnings("unused")
    private final Long id;
    @SuppressWarnings("unused")
    private final String name;
    @SuppressWarnings("unused")
    private final String externalId;
    public static FundData instance(final Long id, final String name, final String externalId) {
        return new FundData(id, name, externalId);
    }
    private FundData(final Long id, final String name, final String externalId) {
        this.id = id;
        this.name = name;
        this.externalId = externalId;
    }
    public String getName() {
        return name;
    }
    public Long getId() {
        return id;
    }
}
