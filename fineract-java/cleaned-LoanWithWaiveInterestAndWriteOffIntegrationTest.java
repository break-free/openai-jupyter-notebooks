
package org.apache.fineract.integrationtests;
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
import org.apache.fineract.integrationtests.common.ClientHelper;
import org.apache.fineract.integrationtests.common.CollateralManagementHelper;
import org.apache.fineract.integrationtests.common.Utils;
import org.apache.fineract.integrationtests.common.loans.LoanApplicationTestBuilder;
import org.apache.fineract.integrationtests.common.loans.LoanProductTestBuilder;
import org.apache.fineract.integrationtests.common.loans.LoanStatusChecker;
import org.apache.fineract.integrationtests.common.loans.LoanTransactionHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@SuppressWarnings({ "rawtypes" })
public class LoanWithWaiveInterestAndWriteOffIntegrationTest {
    private static final Logger LOG = LoggerFactory.getLogger(LoanWithWaiveInterestAndWriteOffIntegrationTest.class);
    private ResponseSpecification responseSpec;
    private RequestSpecification requestSpec;
    private static final String LP_PRINCIPAL = "12,000.00";
    private static final String LP_REPAYMENTS = "2";
    private static final String LP_REPAYMENT_PERIOD = "6";
    private static final String LP_INTEREST_RATE = "1";
    private static final String PRINCIPAL = "4,500.00";
    private static final String LOAN_TERM_FREQUENCY = "18";
    private static final String NUMBER_OF_REPAYMENTS = "9";
    private static final String REPAYMENT_PERIOD = "2";
    private static final String DISBURSEMENT_DATE = "30 October 2010";
    private static final String LOAN_APPLICATION_SUBMISSION_DATE = "23 September 2010";
    private static final String EXPECTED_DISBURSAL_DATE = "28 October 2010";
    private static final String RATE_OF_INTEREST_PER_PERIOD = "2";
    private static final String DATE_OF_JOINING = "04 March 2009";
    private static final String INTEREST_VALUE_AMOUNT = "40.00";
    private LoanTransactionHelper loanTransactionHelper;
    @BeforeEach
    public void setup() {
        Utils.initializeRESTAssured();
        this.requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        this.requestSpec.header("Authorization", "Basic " + Utils.loginIntoServerAndGetBase64EncodedAuthenticationKey());
        this.responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
        this.loanTransactionHelper = new LoanTransactionHelper(this.requestSpec, this.responseSpec);
    }
    @Test
    public void checkClientLoanCreateAndDisburseFlow() {
        final Integer clientID = ClientHelper.createClient(this.requestSpec, this.responseSpec, DATE_OF_JOINING);
        ClientHelper.verifyClientCreatedOnServer(this.requestSpec, this.responseSpec, clientID);
        final Integer loanProductID = createLoanProduct();
        final Integer loanID = applyForLoanApplication(clientID, loanProductID);
        HashMap loanStatusHashMap = LoanStatusChecker.getStatusOfLoan(this.requestSpec, this.responseSpec, loanID);
        LoanStatusChecker.verifyLoanIsPending(loanStatusHashMap);
        LOG.info("-----------------------------------APPROVE LOAN-----------------------------------------");
        loanStatusHashMap = this.loanTransactionHelper.approveLoan("28 September 2010", loanID);
        LoanStatusChecker.verifyLoanIsApproved(loanStatusHashMap);
        LoanStatusChecker.verifyLoanIsWaitingForDisbursal(loanStatusHashMap);
        loanStatusHashMap = this.loanTransactionHelper.undoApproval(loanID);
        LoanStatusChecker.verifyLoanIsPending(loanStatusHashMap);
        LOG.info("-----------------------------------RE-APPROVE LOAN-----------------------------------------");
        loanStatusHashMap = this.loanTransactionHelper.approveLoan("01 October 2010", loanID);
        LoanStatusChecker.verifyLoanIsApproved(loanStatusHashMap);
        LoanStatusChecker.verifyLoanIsWaitingForDisbursal(loanStatusHashMap);
        String loanDetails = this.loanTransactionHelper.getLoanDetails(this.requestSpec, this.responseSpec, loanID);
        loanStatusHashMap = this.loanTransactionHelper.disburseLoan(DISBURSEMENT_DATE, loanID,
                JsonPath.from(loanDetails).get("netDisbursalAmount").toString());
        LOG.info("DISBURSE {}", loanStatusHashMap.toString());
        LoanStatusChecker.verifyLoanIsActive(loanStatusHashMap);
        this.loanTransactionHelper.verifyRepaymentScheduleEntryFor(1, 4000.0F, loanID);
        this.loanTransactionHelper.makeRepayment("01 January 2011", 540.0f, loanID);
        loanStatusHashMap = this.loanTransactionHelper.undoDisbursal(loanID);
        LoanStatusChecker.verifyLoanIsWaitingForDisbursal(loanStatusHashMap);
        loanStatusHashMap = this.loanTransactionHelper.disburseLoan(DISBURSEMENT_DATE, loanID,
                JsonPath.from(loanDetails).get("netDisbursalAmount").toString());
        LOG.info("DISBURSE {}", loanStatusHashMap);
        LoanStatusChecker.verifyLoanIsActive(loanStatusHashMap);
        final float repayment_with_interest = 540.0f;
        final float repayment_without_interest = 500.0f;
        this.loanTransactionHelper.verifyRepaymentScheduleEntryFor(1, 4000.0F, loanID);
        this.loanTransactionHelper.makeRepayment("01 January 2011", repayment_with_interest, loanID);
        this.loanTransactionHelper.makeRepayment("01 March 2011", repayment_with_interest, loanID);
        this.loanTransactionHelper.waiveInterest("01 May 2011", INTEREST_VALUE_AMOUNT, loanID);
        this.loanTransactionHelper.makeRepayment("01 May 2011", repayment_without_interest, loanID);
        this.loanTransactionHelper.makeRepayment("01 July 2011", repayment_with_interest, loanID);
        this.loanTransactionHelper.waiveInterest("01 September 2011", INTEREST_VALUE_AMOUNT, loanID);
        this.loanTransactionHelper.makeRepayment("01 September 2011", repayment_without_interest, loanID);
        this.loanTransactionHelper.makeRepayment("01 November 2011", repayment_with_interest, loanID);
        this.loanTransactionHelper.waiveInterest("01 January 2012", INTEREST_VALUE_AMOUNT, loanID);
        this.loanTransactionHelper.makeRepayment("01 January 2012", repayment_without_interest, loanID);
        this.loanTransactionHelper.verifyRepaymentScheduleEntryFor(7, 1000.0f, loanID);
        LoanStatusChecker.verifyLoanAccountIsClosed(this.loanTransactionHelper.writeOffLoan("01 March 2012", loanID));
    }
    @Test
    public void checkClientLoan_WRITTEN_OFF() {
        final Integer clientID = ClientHelper.createClient(this.requestSpec, this.responseSpec, DATE_OF_JOINING);
        ClientHelper.verifyClientCreatedOnServer(this.requestSpec, this.responseSpec, clientID);
        final Integer loanProductID = createLoanProduct();
        final Integer loanID = applyForLoanApplication(clientID, loanProductID);
        HashMap loanStatusHashMap = LoanStatusChecker.getStatusOfLoan(this.requestSpec, this.responseSpec, loanID);
        LoanStatusChecker.verifyLoanIsPending(loanStatusHashMap);
        LOG.info("-----------------------------------APPROVE LOAN-----------------------------------------");
        loanStatusHashMap = this.loanTransactionHelper.approveLoan("28 September 2010", loanID);
        LoanStatusChecker.verifyLoanIsApproved(loanStatusHashMap);
        LoanStatusChecker.verifyLoanIsWaitingForDisbursal(loanStatusHashMap);
        String loanDetails = this.loanTransactionHelper.getLoanDetails(this.requestSpec, this.responseSpec, loanID);
        loanStatusHashMap = this.loanTransactionHelper.disburseLoan(DISBURSEMENT_DATE, loanID,
                JsonPath.from(loanDetails).get("netDisbursalAmount").toString());
        LOG.info("DISBURSE {}", loanStatusHashMap);
        LoanStatusChecker.verifyLoanIsActive(loanStatusHashMap);
        final float repayment_with_interest = 680.0f;
        this.loanTransactionHelper.verifyRepaymentScheduleEntryFor(1, 4000.0F, loanID);
        this.loanTransactionHelper.makeRepayment("01 January 2011", repayment_with_interest, loanID);
        HashMap toLoanSummaryAfter = this.loanTransactionHelper.getLoanSummary(requestSpec, responseSpec, loanID);
        Assertions.assertTrue(Float.valueOf("500.0").compareTo(Float.valueOf(String.valueOf(toLoanSummaryAfter.get("principalPaid")))) == 0,
                "Checking for Principal paid ");
        Assertions.assertTrue(Float.valueOf("180.0").compareTo(Float.valueOf(String.valueOf(toLoanSummaryAfter.get("interestPaid")))) == 0,
                "Checking for interestPaid paid ");
        Assertions.assertTrue(
                Float.valueOf("680.0").compareTo(Float.valueOf(String.valueOf(toLoanSummaryAfter.get("totalRepayment")))) == 0,
                "Checking for total paid ");
        LoanStatusChecker.verifyLoanAccountIsClosed(this.loanTransactionHelper.writeOffLoan("01 January 2011", loanID));
        toLoanSummaryAfter = this.loanTransactionHelper.getLoanSummary(requestSpec, responseSpec, loanID);
        Assertions.assertTrue(
                Float.valueOf("4000.0").compareTo(Float.valueOf(String.valueOf(toLoanSummaryAfter.get("principalWrittenOff")))) == 0,
                "Checking for Principal written off ");
        Assertions.assertTrue(
                Float.valueOf("1440.0").compareTo(Float.valueOf(String.valueOf(toLoanSummaryAfter.get("interestWrittenOff")))) == 0,
                "Checking for interestPaid written off ");
        Assertions.assertTrue(
                Float.valueOf("5440.0").compareTo(Float.valueOf(String.valueOf(toLoanSummaryAfter.get("totalWrittenOff")))) == 0,
                "Checking for total written off ");
    }
    private Integer createLoanProduct() {
        LOG.info("------------------------------CREATING NEW LOAN PRODUCT ---------------------------------------");
        final String loanProductJSON = new LoanProductTestBuilder().withPrincipal(LP_PRINCIPAL).withRepaymentTypeAsMonth()
                .withRepaymentAfterEvery(LP_REPAYMENT_PERIOD).withNumberOfRepayments(LP_REPAYMENTS).withRepaymentTypeAsMonth()
                .withinterestRatePerPeriod(LP_INTEREST_RATE).withInterestRateFrequencyTypeAsMonths()
                .withAmortizationTypeAsEqualPrincipalPayment().withInterestTypeAsFlat().build(null);
        return this.loanTransactionHelper.getLoanProductId(loanProductJSON);
    }
    private Integer applyForLoanApplication(final Integer clientID, final Integer loanProductID) {
        LOG.info("--------------------------------APPLYING FOR LOAN APPLICATION--------------------------------");
        List<HashMap> collaterals = new ArrayList<>();
        final Integer collateralId = CollateralManagementHelper.createCollateralProduct(this.requestSpec, this.responseSpec);
        Assertions.assertNotNull(collateralId);
        final Integer clientCollateralId = CollateralManagementHelper.createClientCollateral(this.requestSpec, this.responseSpec,
                clientID.toString(), collateralId);
        Assertions.assertNotNull(clientCollateralId);
        addCollaterals(collaterals, clientCollateralId, BigDecimal.valueOf(1));
        final String loanApplicationJSON = new LoanApplicationTestBuilder().withPrincipal(PRINCIPAL)
                .withLoanTermFrequency(LOAN_TERM_FREQUENCY).withLoanTermFrequencyAsMonths().withNumberOfRepayments(NUMBER_OF_REPAYMENTS)
                .withRepaymentEveryAfter(REPAYMENT_PERIOD).withRepaymentFrequencyTypeAsMonths()
                .withInterestRatePerPeriod(RATE_OF_INTEREST_PER_PERIOD).withInterestTypeAsFlatBalance()
                .withAmortizationTypeAsEqualInstallments().withInterestCalculationPeriodTypeSameAsRepaymentPeriod()
                .withExpectedDisbursementDate(EXPECTED_DISBURSAL_DATE).withSubmittedOnDate(LOAN_APPLICATION_SUBMISSION_DATE)
                .withCollaterals(collaterals).build(clientID.toString(), loanProductID.toString(), null);
        return this.loanTransactionHelper.getLoanId(loanApplicationJSON);
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
}
