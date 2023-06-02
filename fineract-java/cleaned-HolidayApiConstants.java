
package org.apache.fineract.organisation.holiday.api;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
public final class HolidayApiConstants {
    private HolidayApiConstants() {
    }
    public static final String HOLIDAY_RESOURCE_NAME = "holiday";
    public static final String localeParamName = "locale";
    public static final String dateFormatParamName = "dateFormat";
    public static final String idParamName = "id";
    public static final String nameParamName = "name";
    public static final String fromDateParamName = "fromDate";
    public static final String toDateParamName = "toDate";
    public static final String descriptionParamName = "description";
    public static final String officesParamName = "offices";
    public static final String officeIdParamName = "officeId";
    public static final String repaymentsRescheduledToParamName = "repaymentsRescheduledTo";
    public static final String processed = "processed";
    public static final String status = "status";
    public static final String reschedulingType = "reschedulingType";
    static final Set<String> HOLIDAY_RESPONSE_DATA_PARAMETERS = new HashSet<>(Arrays.asList(idParamName, nameParamName, fromDateParamName,
            descriptionParamName, toDateParamName, repaymentsRescheduledToParamName, localeParamName, dateFormatParamName, status));
}
