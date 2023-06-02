
package org.apache.fineract.infrastructure.creditbureau.data;
public final class CreditBureauProduct {
    private final long creditBureauProductId;
    private final String creditBureauProductName;
    private final long creditBureauMasterId;
    private CreditBureauProduct(final long creditBureauProductId, final String creditBureauProductName, final long creditBureauMasterId) {
        this.creditBureauProductId = creditBureauProductId;
        this.creditBureauProductName = creditBureauProductName;
        this.creditBureauMasterId = creditBureauMasterId;
    }
    public static CreditBureauProduct instance(final long creditBureauProductId, final String creditBureauProductName,
            final long creditBureauMasterId) {
        return new CreditBureauProduct(creditBureauProductId, creditBureauProductName, creditBureauMasterId);
    }
    public long getCreditBureauProductId() {
        return this.creditBureauProductId;
    }
    public String getCreditBureauProductName() {
        return this.creditBureauProductName;
    }
    public long getCreditBureauMasterId() {
        return this.creditBureauMasterId;
    }
}
