
package org.apache.fineract.integrationtests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import org.apache.fineract.integrationtests.common.CalendarHelper;
import org.apache.fineract.integrationtests.common.ClientHelper;
import org.apache.fineract.integrationtests.common.GroupHelper;
import org.apache.fineract.integrationtests.common.LoanRescheduleRequestHelper;
import org.apache.fineract.integrationtests.common.Utils;
import org.apache.fineract.integrationtests.common.loans.LoanApplicationTestBuilder;
import org.apache.fineract.integrationtests.common.loans.LoanProductTestBuilder;
import org.apache.fineract.integrationtests.common.loans.LoanRescheduleRequestTestBuilder;
import org.apache.fineract.integrationtests.common.loans.LoanStatusChecker;
import org.apache.fineract.integrationtests.common.loans.LoanTransactionHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
@SuppressWarnings({ "rawtypes" })
@Disabled
public class DisbursalAndRepaymentScheduleTest {
    private ResponseSpecification responseSpec;
    private ResponseSpecification responseSpecForStatusCode403;
    private ResponseSpecification generalResponseSpec;
    private RequestSpecification requestSpec;
    private LoanTransactionHelper loanTransactionHelper;
    private LoanRescheduleRequestHelper loanRescheduleRequestHelper;
    private Integer loanRescheduleRequestId;
    private Integer clientId;
    private Integer groupId;
    private Integer groupCalendarId;
    private Integer loanProductId;
    private Integer loanId;
    private final String loanPrincipalAmount = "100000.00";
    private final String numberOfRepayments = "12";
    private final String interestRatePerPeriod = "18";
    private final SimpleDateFormat dateFormatterStandard = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
    @BeforeEach
    public void setup() {
        Utils.initializeRESTAssured();
    }
    @Test
    public void testRescheduleJLGLoanSynk() {
        Calendar meetingCalendar = Calendar.getInstance();
        meetingCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        meetingCalendar.setTime(new java.util.Date());
        int today = meetingCalendar.get(Calendar.DAY_OF_WEEK);
        if (today >= Calendar.MONDAY) {
            meetingCalendar.add(Calendar.DAY_OF_YEAR, +(Calendar.MONDAY - today + 7));
        } else {
            meetingCalendar.add(Calendar.DAY_OF_YEAR, +(Calendar.MONDAY - today));
        }
        Calendar groupMeetingChangeCalendar = (Calendar) meetingCalendar.clone();
        meetingCalendar.add(Calendar.WEEK_OF_YEAR, -3);
        final String groupMeetingDate = this.dateFormatterStandard.format(meetingCalendar.getTime());
        final String disbursalDate = groupMeetingDate; 
        final String rescheduleSubmittedDate = this.dateFormatterStandard.format(new java.util.Date());
        final String loanType = "jlg";
        final String rescheduleInterestRate = "28.0";
        groupMeetingChangeCalendar.add(Calendar.DAY_OF_YEAR, 1);
        final String groupMeetingNewStartDate = this.dateFormatterStandard.format(groupMeetingChangeCalendar.getTime());
        groupMeetingChangeCalendar.add(Calendar.WEEK_OF_YEAR, 2);
        final String rescheduleDate = this.dateFormatterStandard.format(groupMeetingChangeCalendar.getTime());
        this.requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        this.requestSpec.header("Authorization", "Basic " + Utils.loginIntoServerAndGetBase64EncodedAuthenticationKey());
        this.requestSpec.header("Fineract-Platform-TenantId", "default");
        this.responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
        this.generalResponseSpec = new ResponseSpecBuilder().build();
        this.loanTransactionHelper = new LoanTransactionHelper(this.requestSpec, this.responseSpec);
        this.loanRescheduleRequestHelper = new LoanRescheduleRequestHelper(this.requestSpec, this.responseSpec);
        this.createRequiredEntitiesForJLGLoanSync(groupMeetingDate);
        final String loanApplicationJSON = new LoanApplicationTestBuilder().withPrincipal(loanPrincipalAmount).withLoanTermFrequency("24")
                .withLoanTermFrequencyAsWeeks().withNumberOfRepayments("12").withRepaymentEveryAfter("2")
                .withRepaymentFrequencyTypeAsMonths().withAmortizationTypeAsEqualInstallments().withInterestCalculationPeriodTypeAsDays()
                .withInterestRatePerPeriod(interestRatePerPeriod).withRepaymentFrequencyTypeAsWeeks().withSubmittedOnDate(disbursalDate)
                .withExpectedDisbursementDate(disbursalDate).withLoanType(loanType).withSyncDisbursementWithMeetin()
                .withCalendarID(this.groupCalendarId.toString())
                .build(this.clientId.toString(), this.groupId.toString(), this.loanProductId.toString(), null);
        this.loanId = this.loanTransactionHelper.getLoanId(loanApplicationJSON);
        Assertions.assertNotNull(this.loanId);
        HashMap loanStatusHashMap = LoanStatusChecker.getStatusOfLoan(this.requestSpec, this.responseSpec, this.loanId);
        LoanStatusChecker.verifyLoanIsPending(loanStatusHashMap);
        this.loanTransactionHelper.approveLoan(disbursalDate, this.loanId);
        loanStatusHashMap = LoanStatusChecker.getStatusOfLoan(this.requestSpec, this.responseSpec, this.loanId);
        LoanStatusChecker.verifyLoanIsApproved(loanStatusHashMap);
        String loanDetails = this.loanTransactionHelper.getLoanDetails(this.requestSpec, this.responseSpec, this.loanId);
        this.loanTransactionHelper.disburseLoan(disbursalDate, this.loanId,
                JsonPath.from(loanDetails).get("netDisbursalAmount").toString());
        loanStatusHashMap = LoanStatusChecker.getStatusOfLoan(this.requestSpec, this.responseSpec, this.loanId);
        LoanStatusChecker.verifyLoanIsActive(loanStatusHashMap);
        CalendarHelper.updateMeetingCalendarForGroup(this.requestSpec, this.responseSpec, this.groupId, this.groupCalendarId.toString(),
                groupMeetingNewStartDate, "2", "2", "2"); 
        ArrayList loanRepaymnetSchedule = this.loanTransactionHelper.getLoanRepaymentSchedule(requestSpec, generalResponseSpec,
                this.loanId);
        ArrayList dueDateLoanSchedule = (ArrayList) ((HashMap) loanRepaymnetSchedule.get(2)).get("dueDate");
        Calendar dueDateCalendar = Calendar.getInstance();
        dueDateCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        dueDateCalendar.set((Integer) dueDateLoanSchedule.get(0), (Integer) dueDateLoanSchedule.get(1) - 1,
                (Integer) dueDateLoanSchedule.get(2));
        assertEquals(3, dueDateCalendar.get(Calendar.DAY_OF_WEEK), "AFTER MEETING CHANGE DATE THE NEXT REPAYMENT SHOULD BE ON TUESDAY");
        String requestJSON = new LoanRescheduleRequestTestBuilder().updateGraceOnInterest("2").updateGraceOnPrincipal("2")
                .updateNewInterestRate(rescheduleInterestRate).updateRescheduleFromDate(rescheduleDate)
                .updateSubmittedOnDate(rescheduleSubmittedDate).build(this.loanId.toString());
        this.loanRescheduleRequestId = this.loanRescheduleRequestHelper.createLoanRescheduleRequest(requestJSON);
        this.loanRescheduleRequestHelper.verifyCreationOfLoanRescheduleRequest(this.loanRescheduleRequestId);
        loanRepaymnetSchedule = this.loanTransactionHelper.getLoanRepaymentSchedule(requestSpec, generalResponseSpec, this.loanId);
        dueDateLoanSchedule = (ArrayList) ((HashMap) loanRepaymnetSchedule.get(2)).get("dueDate");
        dueDateCalendar.set((Integer) dueDateLoanSchedule.get(0), (Integer) dueDateLoanSchedule.get(1) - 1,
                (Integer) dueDateLoanSchedule.get(2));
        assertEquals(3, dueDateCalendar.get(Calendar.DAY_OF_WEEK),
                "AFTER MEETING CHANGE DATE THE NEXT REPAYMENT SHOULD BE ON TUESDAY, EVEN AFTER LOAN RESCHEDULE REQUEST WAS SENT");
        requestJSON = new LoanRescheduleRequestTestBuilder().updateSubmittedOnDate(rescheduleSubmittedDate)
                .getApproveLoanRescheduleRequestJSON();
        this.loanRescheduleRequestHelper.approveLoanRescheduleRequest(this.loanRescheduleRequestId, requestJSON);
        final HashMap response = (HashMap) this.loanRescheduleRequestHelper.getLoanRescheduleRequest(loanRescheduleRequestId, "statusEnum");
        assertTrue((Boolean) response.get("approved"));
        loanRepaymnetSchedule = this.loanTransactionHelper.getLoanRepaymentSchedule(requestSpec, generalResponseSpec, this.loanId);
        dueDateLoanSchedule = (ArrayList) ((HashMap) loanRepaymnetSchedule.get(2)).get("dueDate");
        dueDateCalendar.set((Integer) dueDateLoanSchedule.get(0), (Integer) dueDateLoanSchedule.get(1) - 1,
                (Integer) dueDateLoanSchedule.get(2));
        assertEquals(3, dueDateCalendar.get(Calendar.DAY_OF_WEEK),
                "AFTER MEETING CHANGE DATE THE NEXT REPAYMENT SHOULD BE ON TUESDAY, EVEN AFTER RESCHEDULE");
        this.loanTransactionHelper = new LoanTransactionHelper(this.requestSpec, this.responseSpecForStatusCode403);
    }
    @Test
    public void testChangeGroupMeetingMaturedOnDate() {
        Calendar meetingCalendar = Calendar.getInstance();
        meetingCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        meetingCalendar.setTime(new java.util.Date());
        int today = meetingCalendar.get(Calendar.DAY_OF_WEEK);
        if (today >= Calendar.MONDAY) {
            meetingCalendar.add(Calendar.DAY_OF_YEAR, +(Calendar.MONDAY - today + 7));
        } else {
            meetingCalendar.add(Calendar.DAY_OF_YEAR, +(Calendar.MONDAY - today));
        }
        Calendar groupMeetingChangeCalendar = (Calendar) meetingCalendar.clone();
        meetingCalendar.add(Calendar.WEEK_OF_YEAR, -3);
        final String groupMeetingDate = this.dateFormatterStandard.format(meetingCalendar.getTime());
        final String disbursalDate = groupMeetingDate; 
        final String loanType = "jlg";
        groupMeetingChangeCalendar.add(Calendar.DAY_OF_YEAR, 1);
        final String groupMeetingNewStartDate = this.dateFormatterStandard.format(groupMeetingChangeCalendar.getTime());
        this.requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        this.requestSpec.header("Authorization", "Basic " + Utils.loginIntoServerAndGetBase64EncodedAuthenticationKey());
        this.requestSpec.header("Fineract-Platform-TenantId", "default");
        this.responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
        this.generalResponseSpec = new ResponseSpecBuilder().build();
        this.loanTransactionHelper = new LoanTransactionHelper(this.requestSpec, this.responseSpec);
        this.loanRescheduleRequestHelper = new LoanRescheduleRequestHelper(this.requestSpec, this.responseSpec);
        this.createRequiredEntitiesForJLGLoanSync(groupMeetingDate);
        final String loanApplicationJSON = new LoanApplicationTestBuilder().withPrincipal(loanPrincipalAmount).withLoanTermFrequency("24")
                .withLoanTermFrequencyAsWeeks().withNumberOfRepayments("12").withRepaymentEveryAfter("2")
                .withRepaymentFrequencyTypeAsMonths().withAmortizationTypeAsEqualInstallments().withInterestCalculationPeriodTypeAsDays()
                .withInterestRatePerPeriod(interestRatePerPeriod).withRepaymentFrequencyTypeAsWeeks().withSubmittedOnDate(disbursalDate)
                .withExpectedDisbursementDate(disbursalDate).withLoanType(loanType).withSyncDisbursementWithMeetin()
                .withCalendarID(this.groupCalendarId.toString())
                .build(this.clientId.toString(), this.groupId.toString(), this.loanProductId.toString(), null);
        this.loanId = this.loanTransactionHelper.getLoanId(loanApplicationJSON);
        Assertions.assertNotNull(this.loanId);
        HashMap loanStatusHashMap = LoanStatusChecker.getStatusOfLoan(this.requestSpec, this.responseSpec, this.loanId);
        LoanStatusChecker.verifyLoanIsPending(loanStatusHashMap);
        this.loanTransactionHelper.approveLoan(disbursalDate, this.loanId);
        loanStatusHashMap = LoanStatusChecker.getStatusOfLoan(this.requestSpec, this.responseSpec, this.loanId);
        LoanStatusChecker.verifyLoanIsApproved(loanStatusHashMap);
        String loanDetails = this.loanTransactionHelper.getLoanDetails(this.requestSpec, this.responseSpec, this.loanId);
        this.loanTransactionHelper.disburseLoan(disbursalDate, this.loanId,
                JsonPath.from(loanDetails).get("netDisbursalAmount").toString());
        loanStatusHashMap = LoanStatusChecker.getStatusOfLoan(this.requestSpec, this.responseSpec, this.loanId);
        LoanStatusChecker.verifyLoanIsActive(loanStatusHashMap);
        CalendarHelper.updateMeetingCalendarForGroup(this.requestSpec, this.responseSpec, this.groupId, this.groupCalendarId.toString(),
                groupMeetingNewStartDate, "2", "2", "2"); 
        Calendar expectedMaturityCalendar = Calendar.getInstance();
        expectedMaturityCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        ArrayList expectedMaturityDate = ((ArrayList) ((HashMap) this.loanTransactionHelper.getLoanDetail(requestSpec, generalResponseSpec,
                this.loanId, "timeline")).get("expectedMaturityDate"));
        expectedMaturityCalendar.set((Integer) expectedMaturityDate.get(0), (Integer) expectedMaturityDate.get(1) - 1,
                (Integer) expectedMaturityDate.get(2));
        assertEquals(3, expectedMaturityCalendar.get(Calendar.DAY_OF_WEEK),
                "AFTER MEETING CHANGE DATE THE EXPECTED MATURITY SHOULD BE ON TUESDAY");
        this.loanTransactionHelper = new LoanTransactionHelper(this.requestSpec, this.responseSpecForStatusCode403);
    }
    private void createRequiredEntitiesForJLGLoanSync(final String groupActivationDate) {
        this.createGroupEntityWithCalendar("2", "2", "1", groupActivationDate);
        this.createClientEntity();
        this.associateClientToGroup(this.groupId, this.clientId);
        this.createLoanProductEntity();
    }
    private void associateClientToGroup(final Integer groupId, final Integer clientId) {
        GroupHelper.associateClient(this.requestSpec, this.responseSpec, groupId.toString(), clientId.toString());
        GroupHelper.verifyGroupMembers(this.requestSpec, this.responseSpec, groupId, clientId);
    }
    private void createGroupEntityWithCalendar(final String frequency, final String interval, final String repeatsOnDay,
            final String groupActivationDate) {
        this.groupId = GroupHelper.createGroup(this.requestSpec, this.responseSpec, groupActivationDate);
        GroupHelper.verifyGroupCreatedOnServer(this.requestSpec, this.responseSpec, this.groupId);
        final String startDate = groupActivationDate;
        this.setGroupCalendarId(CalendarHelper.createMeetingCalendarForGroup(this.requestSpec, this.responseSpec, this.groupId, startDate,
                frequency, interval, repeatsOnDay));
    }
    private void createClientEntity() {
        this.clientId = ClientHelper.createClient(this.requestSpec, this.responseSpec);
        ClientHelper.verifyClientCreatedOnServer(this.requestSpec, this.responseSpec, this.clientId);
    }
    private void createLoanProductEntity() {
        final String loanProductJSON = new LoanProductTestBuilder().withPrincipal(loanPrincipalAmount)
                .withNumberOfRepayments(numberOfRepayments).withinterestRatePerPeriod(interestRatePerPeriod)
                .withInterestRateFrequencyTypeAsYear().build(null);
        this.loanProductId = this.loanTransactionHelper.getLoanProductId(loanProductJSON);
    }
    public void setGroupCalendarId(Integer groupCalendarId) {
        this.groupCalendarId = groupCalendarId;
    }
}
