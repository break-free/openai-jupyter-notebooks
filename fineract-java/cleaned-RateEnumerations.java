
package org.apache.fineract.portfolio.rate.service;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.portfolio.rate.domain.RateAppliesTo;
public final class RateEnumerations {
    private RateEnumerations() {
    }
    public static EnumOptionData rateAppliesTo(final int id) {
        return rateAppliesTo(RateAppliesTo.fromInt(id));
    }
    public static EnumOptionData rateAppliesTo(final RateAppliesTo type) {
        EnumOptionData optionData = null;
        switch (type) {
            case LOAN:
                optionData = new EnumOptionData(RateAppliesTo.LOAN.getValue().longValue(), RateAppliesTo.LOAN.getCode(), "Loan");
            break;
            default:
                optionData = new EnumOptionData(RateAppliesTo.INVALID.getValue().longValue(), RateAppliesTo.INVALID.getCode(), "Invalid");
            break;
        }
        return optionData;
    }
}
