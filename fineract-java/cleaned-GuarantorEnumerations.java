
package org.apache.fineract.portfolio.loanaccount.guarantor.service;
import java.util.ArrayList;
import java.util.List;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.portfolio.loanaccount.guarantor.domain.GuarantorFundStatusType;
import org.apache.fineract.portfolio.loanaccount.guarantor.domain.GuarantorType;
public final class GuarantorEnumerations {
    private GuarantorEnumerations() {
    }
    public static EnumOptionData guarantorType(final int id) {
        return guarantorType(GuarantorType.fromInt(id));
    }
    public static EnumOptionData guarantorType(final GuarantorType guarantorType) {
        final EnumOptionData optionData = new EnumOptionData(guarantorType.getValue().longValue(), guarantorType.getCode(),
                guarantorType.toString());
        return optionData;
    }
    public static List<EnumOptionData> guarantorType(final GuarantorType[] guarantorTypes) {
        final List<EnumOptionData> optionDatas = new ArrayList<>();
        for (final GuarantorType guarantorType : guarantorTypes) {
            optionDatas.add(guarantorType(guarantorType));
        }
        return optionDatas;
    }
    public static EnumOptionData guarantorFundStatusType(final int id) {
        return guarantorFundStatusType(GuarantorFundStatusType.fromInt(id));
    }
    public static EnumOptionData guarantorFundStatusType(final GuarantorFundStatusType guarantorFundType) {
        final EnumOptionData optionData = new EnumOptionData(guarantorFundType.getValue().longValue(), guarantorFundType.getCode(),
                guarantorFundType.toString());
        return optionData;
    }
}
