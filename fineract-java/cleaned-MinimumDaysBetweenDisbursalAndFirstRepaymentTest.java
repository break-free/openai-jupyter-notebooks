
package org.apache.fineract.integrationtests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.fineract.integrationtests.common.CalendarHelper;
import org.apache.fineract.integrationtests.common.ClientHelper;
import org.apache.fineract.integrationtests.common.CollateralManagementHelper;
import org.apache.fineract.integrationtests.common.CommonConstants;
import org.apache.fineract.integrationtests.common.GroupHelper;
import org.apache.fineract.integrationtests.common.Utils;
import org.apache.fineract.integrationtests.common.loans.LoanApplicationTestBuilder;
import org.apache.fineract.integrationtests.common.loans.LoanProductTestBuilder;
import org.apache.fineract.integrationtests.common.loans.LoanStatusChecker;
import org.apache.fineract.integrationtests.common.loans.LoanTransactionHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
@SuppressWarnings({ "rawtypes" })
public class MinimumDaysBetweenDisbursalAndFirstRepaymentTest {
    private ResponseSpecification responseSpec;
    private ResponseSpecification responseSpecForStatusCode403;
    private RequestSpecification requestSpec;
    private LoanTransactionHelper loanTransactionHelper;
    private Integer clientId;
    private Integer groupId;
    private Integer groupCalendarId;
    private Integer loanProductId;
    private Integer loanId;
    private final String loanPrincipalAmount = "100000.00";
    private final String numberOfRepayments = "12";
    private final String interestRatePerPeriod = "18";
    private final String groupActivationDate = "01 August 2014";
    @BeforeEach
    public void setup() {
        Utils.initializeRESTAssured();
    }
    @Test
    public void createLoanEntity_WITH_DAY_BETWEEN_DISB_DATE_AND_REPAY_START_DATE_GREATER_THAN_MIN_DAY_CRITERIA() {
        this.requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        this.requestSpec.header("Authorization", "Basic " + Utils.loginIntoServerAndGetBase64EncodedAuthenticationKey());
        this.responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
        this.loanTransactionHelper = new LoanTransactionHelper(this.requestSpec, this.responseSpec);
        this.createRequiredEntities();
        final String disbursalDate = "04 September 2014";
        final String firstRepaymentDate = "11 September 2014";
        List<HashMap> collaterals = new ArrayList<>();
        final Integer collateralId = CollateralManagementHelper.createCollateralProduct(this.requestSpec, this.responseSpec);
        Assertions.assertNotNull(collateralId);
        final Integer clientCollateralId = CollateralManagementHelper.createClientCollateral(this.requestSpec, this.responseSpec,
                this.clientId.toString(), collateralId);
        Assertions.assertNotNull(clientCollateralId);
        addCollaterals(collaterals, clientCollateralId, BigDecimal.valueOf(1));
        final String loanApplicationJSON = new LoanApplicationTestBuilder().withPrincipal(loanPrincipalAmount)
                .withLoanTermFrequency(numberOfRepayments).withLoanTermFrequencyAsWeeks().withNumberOfRepayments(numberOfRepayments)
                .withRepaymentEveryAfter("1").withRepaymentFrequencyTypeAsMonths().withAmortizationTypeAsEqualInstallments()
                .withInterestCalculationPeriodTypeAsDays().withInterestRatePerPeriod(interestRatePerPeriod)
                .withRepaymentFrequencyTypeAsWeeks().withSubmittedOnDate(disbursalDate).withExpectedDisbursementDate(disbursalDate)
                .withPrincipalGrace("2").withInterestGrace("2").withFirstRepaymentDate(firstRepaymentDate).withCollaterals(collaterals)
                .build(this.clientId.toString(), this.loanProductId.toString(), null);
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
    }
    private void addCollaterals(List<HashMap> collaterals, Integer collateralId, BigDecimal quantity) {
        collaterals.add(collaterals(collateralId, quantity));
    }
    private HashMap<String, String> collaterals(Integer collateralId, BigDecimal quantity) {
        HashMap<String, String> collateral = new HashMap<String, String>(2);
        collateral.put("clientCollateralId", collateralId.toString());
        collateral.put("quantity", quantity.toString());
        return collateral;
    }
    @SuppressWarnings("unchecked")
    @Test
    public void createLoanEntity_WITH_DAY_BETWEEN_DISB_DATE_AND_REPAY_START_DATE_LESS_THAN_MIN_DAY_CRITERIA() {
        this.requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        this.requestSpec.header("Authorization", "Basic " + Utils.loginIntoServerAndGetBase64EncodedAuthenticationKey());
        this.responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
        this.responseSpecForStatusCode403 = new ResponseSpecBuilder().expectStatusCode(403).build();
        this.loanTransactionHelper = new LoanTransactionHelper(this.requestSpec, this.responseSpec);
        this.createRequiredEntities();
        this.loanTransactionHelper = new LoanTransactionHelper(this.requestSpec, this.responseSpecForStatusCode403);
        final String disbursalDate = "04 September 2014";
        final String firstRepaymentDate = "05 September 2014";
        List<HashMap> collaterals = new ArrayList<>();
        final Integer collateralId = CollateralManagementHelper.createCollateralProduct(this.requestSpec, this.responseSpec);
        Assertions.assertNotNull(collateralId);
        final Integer clientCollateralId = CollateralManagementHelper.createClientCollateral(this.requestSpec, this.responseSpec,
                this.clientId.toString(), collateralId);
        Assertions.assertNotNull(clientCollateralId);
        addCollaterals(collaterals, clientCollateralId, BigDecimal.valueOf(1));
        final String loanApplicationJSON = new LoanApplicationTestBuilder().withPrincipal(loanPrincipalAmount)
                .withLoanTermFrequency(numberOfRepayments).withLoanTermFrequencyAsWeeks().withNumberOfRepayments(numberOfRepayments)
                .withRepaymentEveryAfter("1").withRepaymentFrequencyTypeAsMonths().withAmortizationTypeAsEqualInstallments()
                .withInterestCalculationPeriodTypeAsDays().withInterestRatePerPeriod(interestRatePerPeriod)
                .withRepaymentFrequencyTypeAsWeeks().withSubmittedOnDate(disbursalDate).withExpectedDisbursementDate(disbursalDate)
                .withPrincipalGrace("2").withInterestGrace("2").withFirstRepaymentDate(firstRepaymentDate).withCollaterals(collaterals)
                .build(this.clientId.toString(), this.loanProductId.toString(), null);
        List<HashMap> error = (List<HashMap>) this.loanTransactionHelper.createLoanAccount(loanApplicationJSON,
                CommonConstants.RESPONSE_ERROR);
        assertEquals("error.msg.loan.days.between.first.repayment.and.disbursal.are.less.than.minimum.allowed",
                error.get(0).get(CommonConstants.RESPONSE_ERROR_MESSAGE_CODE));
    }
    private void createRequiredEntities() {
        final String minimumDaysBetweenDisbursalAndFirstRepayment = "7"; 
        this.createGroupEntityWithCalendar();
        this.createClientEntity();
        this.associateClientToGroup(this.groupId, this.clientId);
        this.createLoanProductEntity(minimumDaysBetweenDisbursalAndFirstRepayment);
    }
    private void associateClientToGroup(final Integer groupId, final Integer clientId) {
        GroupHelper.associateClient(this.requestSpec, this.responseSpec, groupId.toString(), clientId.toString());
        GroupHelper.verifyGroupMembers(this.requestSpec, this.responseSpec, groupId, clientId);
    }
    private void createGroupEntityWithCalendar() {
        this.groupId = GroupHelper.createGroup(this.requestSpec, this.responseSpec, this.groupActivationDate);
        GroupHelper.verifyGroupCreatedOnServer(this.requestSpec, this.responseSpec, this.groupId);
        final String startDate = this.groupActivationDate;
        final String frequency = "2"; 
        final String interval = "1"; 
        final String repeatsOnDay = "1"; 
        this.setGroupCalendarId(CalendarHelper.createMeetingCalendarForGroup(this.requestSpec, this.responseSpec, this.groupId, startDate,
                frequency, interval, repeatsOnDay));
    }
    private void createClientEntity() {
        this.clientId = ClientHelper.createClient(this.requestSpec, this.responseSpec);
        ClientHelper.verifyClientCreatedOnServer(this.requestSpec, this.responseSpec, this.clientId);
    }
    private void createLoanProductEntity(final String minimumDaysBetweenDisbursalAndFirstRepayment) {
        final String loanProductJSON = new LoanProductTestBuilder().withPrincipal(loanPrincipalAmount)
                .withNumberOfRepayments(numberOfRepayments).withinterestRatePerPeriod(interestRatePerPeriod)
                .withInterestRateFrequencyTypeAsYear()
                .withMinimumDaysBetweenDisbursalAndFirstRepayment(minimumDaysBetweenDisbursalAndFirstRepayment).build(null);
        this.loanProductId = this.loanTransactionHelper.getLoanProductId(loanProductJSON);
    }
    public Integer getGroupCalendarId() {
        return groupCalendarId;
    }
    public void setGroupCalendarId(Integer groupCalendarId) {
        this.groupCalendarId = groupCalendarId;
    }
}
