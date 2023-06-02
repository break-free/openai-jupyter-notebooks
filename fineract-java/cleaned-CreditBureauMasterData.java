
package org.apache.fineract.infrastructure.creditbureau.data;
public final class CreditBureauMasterData {
    private final long creditBureauId;
    private final String creditBureauName;
    private final String country;
    public static CreditBureauMasterData instance(final Long creditBureauId, final String creditBureauName, final String country) {
        return new CreditBureauMasterData(creditBureauId, creditBureauName, country);
    }
    private CreditBureauMasterData(final Long creditBureauId, final String creditBureauName, final String country) {
        this.creditBureauId = creditBureauId;
        this.creditBureauName = creditBureauName;
        this.country = country;
    }
    public String getCreditBureauName() {
        return this.creditBureauName;
    }
    public String getCountry() {
        return this.country;
    }
    public Long getCreditBureauId() {
        return this.creditBureauId;
    }
}
