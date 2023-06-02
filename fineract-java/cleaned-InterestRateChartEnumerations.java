
package org.apache.fineract.portfolio.interestratechart.service;
import java.util.ArrayList;
import java.util.List;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.portfolio.common.domain.PeriodFrequencyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public final class InterestRateChartEnumerations {
    private InterestRateChartEnumerations() {
    }
    private static final Logger LOG = LoggerFactory.getLogger(InterestRateChartEnumerations.class);
    public static EnumOptionData periodType(final Integer type) {
        return periodType(PeriodFrequencyType.fromInt(type));
    }
    public static EnumOptionData periodType(final PeriodFrequencyType type) {
        EnumOptionData optionData = new EnumOptionData(PeriodFrequencyType.INVALID.getValue().longValue(),
                PeriodFrequencyType.INVALID.getCode(), "Invalid");
        switch (type) {
            case INVALID:
            break;
            case DAYS:
                optionData = new EnumOptionData(PeriodFrequencyType.DAYS.getValue().longValue(), PeriodFrequencyType.DAYS.getCode(),
                        "Days");
            break;
            case WEEKS:
                optionData = new EnumOptionData(PeriodFrequencyType.WEEKS.getValue().longValue(), PeriodFrequencyType.WEEKS.getCode(),
                        "Weeks");
            break;
            case MONTHS:
                optionData = new EnumOptionData(PeriodFrequencyType.MONTHS.getValue().longValue(), PeriodFrequencyType.MONTHS.getCode(),
                        "Months");
            break;
            case YEARS:
                optionData = new EnumOptionData(PeriodFrequencyType.YEARS.getValue().longValue(), PeriodFrequencyType.YEARS.getCode(),
                        "Years");
            break;
            case WHOLE_TERM:
                optionData = new EnumOptionData(PeriodFrequencyType.WHOLE_TERM.getValue().longValue(),
                        PeriodFrequencyType.WHOLE_TERM.getCode(), "Whole Term");
            break;
        }
        return optionData;
    }
    public static List<EnumOptionData> periodType(final PeriodFrequencyType[] periodTypes) {
        final List<EnumOptionData> optionDatas = new ArrayList<>();
        for (final PeriodFrequencyType periodType : periodTypes) {
            if (!periodType.isInvalid()) {
                optionDatas.add(periodType(periodType));
            }
        }
        return optionDatas;
    }
}
