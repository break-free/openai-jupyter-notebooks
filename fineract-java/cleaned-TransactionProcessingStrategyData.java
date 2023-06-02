
package org.apache.fineract.portfolio.loanproduct.data;
import java.io.Serializable;
public final class TransactionProcessingStrategyData implements Serializable {
    private final Long id;
    @SuppressWarnings("unused")
    private final String code;
    private final String name;
    public TransactionProcessingStrategyData(final Long id, final String code, final String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }
    public Long id() {
        return this.id;
    }
    public String name() {
        return this.name;
    }
}
