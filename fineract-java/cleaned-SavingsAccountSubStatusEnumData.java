
package org.apache.fineract.portfolio.savings.data;
import java.io.Serializable;
public class SavingsAccountSubStatusEnumData implements Serializable {
    private final Long id;
    @SuppressWarnings("unused")
    private final String code;
    @SuppressWarnings("unused")
    private final String value;
    @SuppressWarnings("unused")
    private final boolean none;
    @SuppressWarnings("unused")
    private final boolean inactive;
    @SuppressWarnings("unused")
    private final boolean dormant;
    @SuppressWarnings("unused")
    private final boolean escheat;
    @SuppressWarnings("unused")
    private final boolean block;
    @SuppressWarnings("unused")
    private final boolean blockCredit;
    @SuppressWarnings("unused")
    private final boolean blockDebit;
    public SavingsAccountSubStatusEnumData(final Long id, final String code, final String value, final boolean none, final boolean inactive,
            final boolean dormant, final boolean escheat, final boolean block, final boolean blockCredit, final boolean blockDebit) {
        this.id = id;
        this.code = code;
        this.value = value;
        this.none = none;
        this.inactive = inactive;
        this.dormant = dormant;
        this.escheat = escheat;
        this.block = block;
        this.blockCredit = blockCredit;
        this.blockDebit = blockDebit;
    }
    public Long id() {
        return this.id;
    }
}
