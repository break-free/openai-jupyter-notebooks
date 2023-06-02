
package org.apache.fineract.organisation.teller.domain;
import java.io.Serializable;
public final class CashierTxnType implements Serializable {
    private Integer id;
    private String value;
    public static final CashierTxnType ALLOCATE = new CashierTxnType(101, "Allocate Cash");
    public static final CashierTxnType SETTLE = new CashierTxnType(102, "Settle Cash");
    public static final CashierTxnType INWARD_CASH_TXN = new CashierTxnType(103, "Cash In");
    public static final CashierTxnType OUTWARD_CASH_TXN = new CashierTxnType(104, "Cash Out");
    private CashierTxnType() {}
    private CashierTxnType(Integer id, String value) {
        this.id = id;
        this.value = value;
    }
    public Integer getId() {
        return id;
    }
    public String getValue() {
        return value;
    }
    public static CashierTxnType getCashierTxnType(Integer id) {
        CashierTxnType retVal = null;
        switch (id) {
            case 101:
                retVal = ALLOCATE;
            break;
            case 102:
                retVal = SETTLE;
            break;
            case 103:
                retVal = INWARD_CASH_TXN;
            break;
            case 104:
                retVal = OUTWARD_CASH_TXN;
            break;
            default:
            break;
        }
        return retVal;
    }
}
