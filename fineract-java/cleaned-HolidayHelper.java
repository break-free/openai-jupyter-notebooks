
package org.apache.fineract.integrationtests.common;
import com.google.gson.Gson;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@SuppressWarnings({ "unused", "rawtypes" })
public class HolidayHelper {
    private static final Logger LOG = LoggerFactory.getLogger(HolidayHelper.class);
    private static final String HOLIDAYS_URL = "/fineract-provider/api/v1/holidays";
    private static final String CREATE_HOLIDAY_URL = HOLIDAYS_URL + "?" + Utils.TENANT_IDENTIFIER;
    private static final String OFFICE_ID = "1";
    private final RequestSpecification requestSpec;
    private final ResponseSpecification responseSpec;
    public HolidayHelper(final RequestSpecification requestSpec, final ResponseSpecification responseSpec) {
        this.requestSpec = requestSpec;
        this.responseSpec = responseSpec;
    }
    public static String getCreateHolidayDataAsJSON() {
        final HashMap<String, Object> map = new HashMap<>();
        List<HashMap<String, String>> offices = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> officeMap = new HashMap<>();
        officeMap.put("officeId", OFFICE_ID);
        offices.add(officeMap);
        map.put("offices", offices);
        map.put("locale", "en");
        map.put("dateFormat", "dd MMMM yyyy");
        map.put("name", Utils.randomNameGenerator("HOLIDAY_", 5));
        map.put("fromDate", "01 April 2013");
        map.put("toDate", "01 April 2013");
        map.put("repaymentsRescheduledTo", "08 April 2013");
        map.put("reschedulingType", 2);
        String HolidayCreateJson = new Gson().toJson(map);
        LOG.info("{}", HolidayCreateJson);
        return HolidayCreateJson;
    }
    public static String getActivateHolidayDataAsJSON() {
        final HashMap<String, String> map = new HashMap<>();
        String activateHoliday = new Gson().toJson(map);
        LOG.info("{}", activateHoliday);
        return activateHoliday;
    }
    public static Integer createHolidays(final RequestSpecification requestSpec, final ResponseSpecification responseSpec) {
        return Utils.performServerPost(requestSpec, responseSpec, CREATE_HOLIDAY_URL, getCreateHolidayDataAsJSON(), "resourceId");
    }
    public static Integer activateHolidays(final RequestSpecification requestSpec, final ResponseSpecification responseSpec,
            final String holidayID) {
        final String ACTIVATE_HOLIDAY_URL = HOLIDAYS_URL + "/" + holidayID + "?command=activate&" + Utils.TENANT_IDENTIFIER;
        return Utils.performServerPost(requestSpec, responseSpec, ACTIVATE_HOLIDAY_URL, getActivateHolidayDataAsJSON(), "resourceId");
    }
    public static HashMap getHolidayById(final RequestSpecification requestSpec, final ResponseSpecification responseSpec,
            final String holidayID) {
        final String GET_HOLIDAY_BY_ID_URL = HOLIDAYS_URL + "/" + holidayID + "?" + Utils.TENANT_IDENTIFIER;
        LOG.info("------------------------ RETRIEVING HOLIDAY BY ID -------------------------");
        final HashMap response = Utils.performServerGet(requestSpec, responseSpec, GET_HOLIDAY_BY_ID_URL, "");
        return response;
    }
}
