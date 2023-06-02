
package org.apache.fineract.infrastructure.reportmailingjob.util;
import org.apache.fineract.infrastructure.core.service.DateUtils;
import org.apache.fineract.infrastructure.reportmailingjob.data.ReportMailingJobStretchyReportParamDateOption;
public final class ReportMailingJobDateUtil {
    private ReportMailingJobDateUtil() {
    }
    public static final String MYSQL_DATE_FORMAT = "yyyy-MM-dd";
    public static String getTodayDateAsString() {
        return DateUtils.getLocalDateOfTenant().format(DateUtils.DEFAULT_DATE_FORMATER);
    }
    public static String getYesterdayDateAsString() {
        return DateUtils.getLocalDateOfTenant().minusDays(1).format(DateUtils.DEFAULT_DATE_FORMATER);
    }
    public static String getTomorrowDateAsString() {
        return DateUtils.getLocalDateOfTenant().plusDays(1).format(DateUtils.DEFAULT_DATE_FORMATER);
    }
    public static String getDateAsString(
            final ReportMailingJobStretchyReportParamDateOption reportMailingJobStretchyReportParamDateOption) {
        String dateAsString = null;
        switch (reportMailingJobStretchyReportParamDateOption) {
            case TODAY:
                dateAsString = getTodayDateAsString();
            break;
            case YESTERDAY:
                dateAsString = getYesterdayDateAsString();
            break;
            case TOMORROW:
                dateAsString = getTomorrowDateAsString();
            break;
            default:
            break;
        }
        return dateAsString;
    }
}
