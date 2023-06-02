
package org.apache.fineract.integrationtests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import java.util.Map;
import org.apache.fineract.integrationtests.common.ClientHelper;
import org.apache.fineract.integrationtests.common.CollateralManagementHelper;
import org.apache.fineract.integrationtests.common.GlobalConfigurationHelper;
import org.apache.fineract.integrationtests.common.LoanRescheduleRequestHelper;
import org.apache.fineract.integrationtests.common.Utils;
import org.apache.fineract.integrationtests.common.loans.LoanApplicationTestBuilder;
import org.apache.fineract.integrationtests.common.loans.LoanProductTestBuilder;
import org.apache.fineract.integrationtests.common.loans.LoanRescheduleRequestTestBuilder;
import org.apache.fineract.integrationtests.common.loans.LoanTransactionHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class LoanRescheduleOnDecliningBalanceLoanTest {
    private static final Logger LOG = LoggerFactory.getLogger(LoanRescheduleOnDecliningBalanceLoanTest.class);
    private ResponseSpecification responseSpec;
    private ResponseSpecification generalResponseSpec;
    private RequestSpecification requestSpec;
    private LoanTransactionHelper loanTransactionHelper;
    private LoanRescheduleRequestHelper loanRescheduleRequestHelper;
    private Integer clientId;
    private Integer loanProductId;
    private Integer loanId;
    private Integer loanRescheduleRequestId;
    private final String loanPrincipalAmount = "100000.00";
    private final String numberOfRepayments = "12";
    private final String interestRatePerPeriod = "18";
    private final String dateString = "4 September 2014";
    @BeforeEach
    public void initialize() {
        Utils.initializeRESTAssured();
        this.requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        this.requestSpec.header("Authorization", "Basic " + Utils.loginIntoServerAndGetBase64EncodedAuthenticationKey());
        this.responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
        this.loanTransactionHelper = new LoanTransactionHelper(this.requestSpec, this.responseSpec);
        this.loanRescheduleRequestHelper = new LoanRescheduleRequestHelper(this.requestSpec, this.responseSpec);
        this.generalResponseSpec = new ResponseSpecBuilder().build();
    }
    @AfterEach
    public void tearDown() {
        disableConfig();
    }
    private void createRequiredEntities() {
        this.createClientEntity();
        this.createLoanProductEntity();
        this.createLoanEntity();
        this.enableConfig();
    }
    private void createRequiredEntitiesWithRecalculationEnabled() {
        this.createClientEntity();
        this.createLoanProductWithInterestRecalculation();
        this.createLoanEntity();
        this.enableConfig();
    }
    private void createClientEntity() {
        this.clientId = ClientHelper.createClient(this.requestSpec, this.responseSpec);
        ClientHelper.verifyClientCreatedOnServer(this.requestSpec, this.responseSpec, this.clientId);
    }
    private void createLoanProductEntity() {
        LOG.info("---------------------------------CREATING LOAN PRODUCT------------------------------------------");
        final String loanProductJSON = new LoanProductTestBuilder().withPrincipal(loanPrincipalAmount)
                .withNumberOfRepayments(numberOfRepayments).withinterestRatePerPeriod(interestRatePerPeriod)
                .withInterestRateFrequencyTypeAsYear().withInterestTypeAsDecliningBalance().withInterestCalculationPeriodTypeAsDays()
                .build(null);
        this.loanProductId = this.loanTransactionHelper.getLoanProductId(loanProductJSON);
        LOG.info("Successfully created loan product  (ID:{}) ", this.loanProductId);
    }
    private void createLoanProductWithInterestRecalculation() {
        LOG.info(
                "---------------------------------CREATING LOAN PRODUCT WITH RECALULATION ENABLED ------------------------------------------");
        final String interestRecalculationCompoundingMethod = LoanProductTestBuilder.RECALCULATION_COMPOUNDING_METHOD_NONE;
        final String rescheduleStrategyMethod = LoanProductTestBuilder.RECALCULATION_STRATEGY_REDUCE_NUMBER_OF_INSTALLMENTS;
        final String recalculationRestFrequencyType = LoanProductTestBuilder.RECALCULATION_FREQUENCY_TYPE_DAILY;
        final String recalculationRestFrequencyInterval = "0";
        final String preCloseInterestCalculationStrategy = LoanProductTestBuilder.INTEREST_APPLICABLE_STRATEGY_ON_PRE_CLOSE_DATE;
        final String recalculationCompoundingFrequencyType = null;
        final String recalculationCompoundingFrequencyInterval = null;
        final Integer recalculationCompoundingFrequencyOnDayType = null;
        final Integer recalculationCompoundingFrequencyDayOfWeekType = null;
        final Integer recalculationRestFrequencyOnDayType = null;
        final Integer recalculationRestFrequencyDayOfWeekType = null;
        final String loanProductJSON = new LoanProductTestBuilder().withPrincipal(loanPrincipalAmount)
                .withNumberOfRepayments(numberOfRepayments).withinterestRatePerPeriod(interestRatePerPeriod)
                .withInterestRateFrequencyTypeAsYear().withInterestTypeAsDecliningBalance().withInterestCalculationPeriodTypeAsDays()
                .withInterestRecalculationDetails(interestRecalculationCompoundingMethod, rescheduleStrategyMethod,
                        preCloseInterestCalculationStrategy)
                .withInterestRecalculationRestFrequencyDetails(recalculationRestFrequencyType, recalculationRestFrequencyInterval,
                        recalculationRestFrequencyOnDayType, recalculationRestFrequencyDayOfWeekType)
                .withInterestRecalculationCompoundingFrequencyDetails(recalculationCompoundingFrequencyType,
                        recalculationCompoundingFrequencyInterval, recalculationCompoundingFrequencyOnDayType,
                        recalculationCompoundingFrequencyDayOfWeekType)
                .build(null);
        this.loanProductId = this.loanTransactionHelper.getLoanProductId(loanProductJSON);
        LOG.info("Successfully created loan product  (ID:{}) ", this.loanProductId);
    }
    private void createLoanEntity() {
        LOG.info("---------------------------------NEW LOAN APPLICATION------------------------------------------");
        List<HashMap> collaterals = new ArrayList<>();
        final Integer collateralId = CollateralManagementHelper.createCollateralProduct(this.requestSpec, this.responseSpec);
        Assertions.assertNotNull(collateralId);
        final Integer clientCollateralId = CollateralManagementHelper.createClientCollateral(this.requestSpec, this.responseSpec,
                this.clientId.toString(), collateralId);
        Assertions.assertNotNull(clientCollateralId);
        addCollaterals(collaterals, clientCollateralId, BigDecimal.valueOf(1));
        final String loanApplicationJSON = new LoanApplicationTestBuilder().withPrincipal(loanPrincipalAmount)
                .withLoanTermFrequency(numberOfRepayments).withLoanTermFrequencyAsMonths().withNumberOfRepayments(numberOfRepayments)
                .withRepaymentEveryAfter("1").withRepaymentFrequencyTypeAsMonths().withAmortizationTypeAsEqualInstallments()
                .withInterestCalculationPeriodTypeAsDays().withInterestRatePerPeriod(interestRatePerPeriod)
                .withInterestTypeAsDecliningBalance().withSubmittedOnDate(dateString).withExpectedDisbursementDate(dateString)
                .withCollaterals(collaterals).withPrincipalGrace("2").withInterestGrace("2")
                .build(this.clientId.toString(), this.loanProductId.toString(), null);
        this.loanId = this.loanTransactionHelper.getLoanId(loanApplicationJSON);
        LOG.info("Sucessfully created loan (ID: {} )", this.loanId);
        this.approveLoanApplication(this.dateString);
        this.disburseLoan(this.dateString);
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
    private void approveLoanApplication(String approveDate) {
        if (this.loanId != null) {
            this.loanTransactionHelper.approveLoan(approveDate, this.loanId);
            LOG.info("Successfully approved loan (ID: {} )", this.loanId);
        }
    }
    private void disburseLoan(String disburseDate) {
        if (this.loanId != null) {
            String loanDetails = this.loanTransactionHelper.getLoanDetails(this.requestSpec, this.responseSpec, this.loanId);
            this.loanTransactionHelper.disburseLoan(disburseDate, this.loanId,
                    JsonPath.from(loanDetails).get("netDisbursalAmount").toString());
            LOG.info("Successfully disbursed loan (ID: {} )", this.loanId);
        }
    }
    private void enableConfig() {
        GlobalConfigurationHelper.updateEnabledFlagForGlobalConfiguration(this.requestSpec, this.responseSpec, "42", true);
    }
    private void disableConfig() {
        GlobalConfigurationHelper.updateEnabledFlagForGlobalConfiguration(this.requestSpec, this.responseSpec, "42", false);
    }
    @Test
    public void testCreateLoanRescheduleRequestWithInterestAppropriation() {
        this.createRequiredEntities();
        this.createAndApproveLoanRescheduleRequestForRecoverInterestInterestFirst();
    }
    private void createAndApproveLoanRescheduleRequestForRecoverInterestInterestFirst() {
        LOG.info(
                "---------------------------------CREATING LOAN RESCHEDULE REQUEST FOR INTEREST APPROPRIATTION-------------------------------------");
        final String requestJSON = new LoanRescheduleRequestTestBuilder().updateGraceOnPrincipal(null).updateGraceOnInterest(null)
                .updateExtraTerms(null).updateRescheduleFromDate("04 January 2015").updateAdjustedDueDate("04 October 2015")
                .updateRecalculateInterest(true).build(this.loanId.toString());
        this.loanRescheduleRequestId = this.loanRescheduleRequestHelper.createLoanRescheduleRequest(requestJSON);
        this.loanRescheduleRequestHelper.verifyCreationOfLoanRescheduleRequest(this.loanRescheduleRequestId);
        LOG.info("Successfully created loan reschedule request (ID: {} )", this.loanRescheduleRequestId);
        final String aproveRequestJSON = new LoanRescheduleRequestTestBuilder().getApproveLoanRescheduleRequestJSON();
        this.loanRescheduleRequestHelper.approveLoanRescheduleRequest(this.loanRescheduleRequestId, aproveRequestJSON);
        final HashMap response = (HashMap) this.loanRescheduleRequestHelper.getLoanRescheduleRequest(loanRescheduleRequestId, "statusEnum");
        assertTrue((Boolean) response.get("approved"));
        LOG.info("Successfully approved loan reschedule request (ID: {})", this.loanRescheduleRequestId);
        final Map repaymentSchedule = (Map) this.loanTransactionHelper.getLoanDetail(requestSpec, generalResponseSpec, loanId,
                "repaymentSchedule");
        final ArrayList periods = (ArrayList) repaymentSchedule.get("periods");
        HashMap period = (HashMap) periods.get(5);
        Float totalDueForPeriod = (Float) period.get("totalDueForPeriod");
        final HashMap loanSummary = this.loanTransactionHelper.getLoanSummary(requestSpec, generalResponseSpec, loanId);
        final Float totalExpectedRepayment = (Float) loanSummary.get("totalExpectedRepayment");
        assertEquals(10831, totalDueForPeriod.intValue(), "EXPECTED REPAYMENT is NOK");
        assertEquals(125184, totalExpectedRepayment.intValue(), "TOTAL EXPECTED REPAYMENT is NOK");
        LOG.info("Successfully approved loan reschedule request (ID: {})", this.loanRescheduleRequestId);
    }
    @Test
    public void testCreateLoanRescheduleRequestWithRecalculationEnabled() {
        this.createRequiredEntitiesWithRecalculationEnabled();
        this.createAndApproveLoanRescheduleRequestWithRecalculationEnabled();
    }
    private void createAndApproveLoanRescheduleRequestWithRecalculationEnabled() {
        LOG.info(
                "---------------------------------CREATING LOAN RESCHEDULE REQUEST FOR LOAN WITH RECALCULATION------------------------------------");
        final String requestJSON = new LoanRescheduleRequestTestBuilder().updateGraceOnPrincipal(null).updateGraceOnInterest(null)
                .updateExtraTerms(null).updateRescheduleFromDate("04 January 2015").updateAdjustedDueDate("04 October 2015")
                .updateRecalculateInterest(true).build(this.loanId.toString());
        this.loanRescheduleRequestId = this.loanRescheduleRequestHelper.createLoanRescheduleRequest(requestJSON);
        this.loanRescheduleRequestHelper.verifyCreationOfLoanRescheduleRequest(this.loanRescheduleRequestId);
        LOG.info("Successfully created loan reschedule request (ID: {} )", this.loanRescheduleRequestId);
        final String aproveRequestJSON = new LoanRescheduleRequestTestBuilder().getApproveLoanRescheduleRequestJSON();
        this.loanRescheduleRequestHelper.approveLoanRescheduleRequest(this.loanRescheduleRequestId, aproveRequestJSON);
        final HashMap response = (HashMap) this.loanRescheduleRequestHelper.getLoanRescheduleRequest(loanRescheduleRequestId, "statusEnum");
        assertTrue((Boolean) response.get("approved"));
        LOG.info("Successfully approved loan reschedule request (ID: {})", this.loanRescheduleRequestId);
        final Map repaymentSchedule = (Map) this.loanTransactionHelper.getLoanDetail(requestSpec, generalResponseSpec, loanId,
                "repaymentSchedule");
        final ArrayList periods = (ArrayList) repaymentSchedule.get("periods");
        HashMap period = (HashMap) periods.get(5);
        Float totalDueForPeriod = (Float) period.get("totalDueForPeriod");
        final HashMap loanSummary = this.loanTransactionHelper.getLoanSummary(requestSpec, generalResponseSpec, loanId);
        final Float totalExpectedRepayment = (Float) loanSummary.get("totalExpectedRepayment");
        assertEquals(10831, totalDueForPeriod.intValue(), "EXPECTED REPAYMENT is NOK");
        assertEquals(131512, totalExpectedRepayment.intValue(), "TOTAL EXPECTED REPAYMENT is NOK");
        LOG.info("Successfully approved loan reschedule request (ID: {})", this.loanRescheduleRequestId);
    }
    @Test
    public void testCreateLoanRescheduleRequestForInterestAppropriationAndFixedEMI() {
        this.createRequiredEntities();
        this.createAndApproveLoanRescheduleRequestForRecoverInterestFirstAndFixedEMI();
    }
    private void createAndApproveLoanRescheduleRequestForRecoverInterestFirstAndFixedEMI() {
        LOG.info(
                "---------------------------------CREATING LOAN RESCHEDULE REQUEST FOR INTEREST APPROPRIATTION-------------------------------------");
        final String requestJSON = new LoanRescheduleRequestTestBuilder().updateGraceOnPrincipal(null).updateGraceOnInterest(null)
                .updateExtraTerms(null).updateRescheduleFromDate("04 January 2015").updateAdjustedDueDate("04 July 2015").updateEMI("5000")
                .updateEmiChangeEndDate("4 September 2015").updateRecalculateInterest(true).build(this.loanId.toString());
        this.loanRescheduleRequestId = this.loanRescheduleRequestHelper.createLoanRescheduleRequest(requestJSON);
        this.loanRescheduleRequestHelper.verifyCreationOfLoanRescheduleRequest(this.loanRescheduleRequestId);
        LOG.info("Successfully created loan reschedule request (ID: {} )", this.loanRescheduleRequestId);
        final String aproveRequestJSON = new LoanRescheduleRequestTestBuilder().getApproveLoanRescheduleRequestJSON();
        this.loanRescheduleRequestHelper.approveLoanRescheduleRequest(this.loanRescheduleRequestId, aproveRequestJSON);
        final HashMap response = (HashMap) this.loanRescheduleRequestHelper.getLoanRescheduleRequest(loanRescheduleRequestId, "statusEnum");
        assertTrue((Boolean) response.get("approved"));
        LOG.info("Successfully approved loan reschedule request (ID: {})", this.loanRescheduleRequestId);
        final Map repaymentSchedule = (Map) this.loanTransactionHelper.getLoanDetail(requestSpec, generalResponseSpec, loanId,
                "repaymentSchedule");
        final ArrayList periods = (ArrayList) repaymentSchedule.get("periods");
        HashMap period = (HashMap) periods.get(5);
        Float totalFixedDueForPeriod = (Float) period.get("totalDueForPeriod");
        HashMap period2 = (HashMap) periods.get(8);
        Float totalDueForPeriod = (Float) period2.get("totalDueForPeriod");
        final HashMap loanSummary = this.loanTransactionHelper.getLoanSummary(requestSpec, generalResponseSpec, loanId);
        final Float totalExpectedRepayment = (Float) loanSummary.get("totalExpectedRepayment");
        assertEquals(5000, totalFixedDueForPeriod.intValue(), "EXPECTED FIXED REPAYMENT is NOK");
        assertEquals(15417, totalDueForPeriod.intValue(), "EXPECTED REPAYMENT is NOK");
        assertEquals(121412, totalExpectedRepayment.intValue(), "TOTAL EXPECTED REPAYMENT is NOK");
        LOG.info("Successfully approved loan reschedule request (ID: {})", this.loanRescheduleRequestId);
    }
    @Test
    public void testCreateLoanRescheduleRequestWithMultpleInterestAppropriation() {
        this.createRequiredEntities();
        this.createAndApproveLoanRescheduleRequestForRecoverInterestInterestFirst();
        this.createAndApproveLoanRescheduleRequestForSecondInterestAppropriation();
    }
    private void createAndApproveLoanRescheduleRequestForSecondInterestAppropriation() {
        LOG.info(
                "---------------------------------CREATING LOAN RESCHEDULE REQUEST FOR INTEREST APPROPRIATTION-------------------------------------");
        final String requestJSON = new LoanRescheduleRequestTestBuilder().updateGraceOnPrincipal(null).updateGraceOnInterest(null)
                .updateExtraTerms(null).updateRescheduleFromDate("04 December 2015").updateAdjustedDueDate("04 June 2016")
                .updateRecalculateInterest(true).build(this.loanId.toString());
        this.loanRescheduleRequestId = this.loanRescheduleRequestHelper.createLoanRescheduleRequest(requestJSON);
        this.loanRescheduleRequestHelper.verifyCreationOfLoanRescheduleRequest(this.loanRescheduleRequestId);
        LOG.info("Successfully created loan reschedule request (ID: {} )", this.loanRescheduleRequestId);
        final String aproveRequestJSON = new LoanRescheduleRequestTestBuilder().getApproveLoanRescheduleRequestJSON();
        this.loanRescheduleRequestHelper.approveLoanRescheduleRequest(this.loanRescheduleRequestId, aproveRequestJSON);
        final HashMap response = (HashMap) this.loanRescheduleRequestHelper.getLoanRescheduleRequest(loanRescheduleRequestId, "statusEnum");
        assertTrue((Boolean) response.get("approved"));
        LOG.info("Successfully approved loan reschedule request (ID: {})", this.loanRescheduleRequestId);
        final Map repaymentSchedule = (Map) this.loanTransactionHelper.getLoanDetail(requestSpec, generalResponseSpec, loanId,
                "repaymentSchedule");
        final ArrayList periods = (ArrayList) repaymentSchedule.get("periods");
        HashMap period = (HashMap) periods.get(7);
        Float totalDueForPeriod = (Float) period.get("totalDueForPeriod");
        final HashMap loanSummary = this.loanTransactionHelper.getLoanSummary(requestSpec, generalResponseSpec, loanId);
        final Float totalExpectedRepayment = (Float) loanSummary.get("totalExpectedRepayment");
        assertEquals(10831, totalDueForPeriod.intValue(), "EXPECTED REPAYMENT in Second Reschedule is NOK");
        assertEquals(133470, totalExpectedRepayment.intValue(), "TOTAL EXPECTED in Second Reschedule REPAYMENT is NOK");
        LOG.info("Successfully approved loan reschedule request (ID: {})", this.loanRescheduleRequestId);
    }
    @Test
    public void testCreateLoanInterestGreaterThanEMIFromGapWithRecalculationEnabledAndPrincipalCompoundingOff() {
        this.enableConfig();
        this.enablePrincipalCompoundingConfig();
        this.createRequiredEntitiesWithRecalculationEnabledWithPrincipalCompoundingOff();
        this.createApproveLoanRescheduleRequestWithRecalculationEnabledWithPrincipalCompoundingOff();
        this.disablePrincipalCompoundingConfig();
        this.disableConfig();
    }
    private void createRequiredEntitiesWithRecalculationEnabledWithPrincipalCompoundingOff() {
        this.createClientEntity();
        this.createLoanProductWithInterestRecalculation();
        this.createLoanEntityWithScheduleGapWithInterestGreaterThanEMIAndPrincipalCompoundingOff();
    }
    private void enablePrincipalCompoundingConfig() {
        GlobalConfigurationHelper.updateEnabledFlagForGlobalConfiguration(this.requestSpec, this.responseSpec, "43", true);
    }
    private void disablePrincipalCompoundingConfig() {
        GlobalConfigurationHelper.updateEnabledFlagForGlobalConfiguration(this.requestSpec, this.responseSpec, "43", false);
    }
    private void createLoanEntityWithScheduleGapWithInterestGreaterThanEMIAndPrincipalCompoundingOff() {
        String firstRepaymentDate = "01 January 2015";
        LOG.info("---------------------------------NEW LOAN APPLICATION------------------------------------------");
        final String loanApplicationJSON = new LoanApplicationTestBuilder().withPrincipal("15000").withLoanTermFrequency("24")
                .withLoanTermFrequencyAsMonths().withNumberOfRepayments("24").withRepaymentEveryAfter("1")
                .withRepaymentFrequencyTypeAsMonths().withAmortizationTypeAsEqualInstallments().withInterestCalculationPeriodTypeAsDays()
                .withInterestRatePerPeriod("25").withInterestTypeAsDecliningBalance().withSubmittedOnDate(this.dateString)
                .withExpectedDisbursementDate(this.dateString).withFirstRepaymentDate(firstRepaymentDate)
                .withinterestChargedFromDate(this.dateString).build(this.clientId.toString(), this.loanProductId.toString(), null);
        this.loanId = this.loanTransactionHelper.getLoanId(loanApplicationJSON);
        LOG.info("Sucessfully created loan (ID: {} )", this.loanId);
        this.approveLoanApplication(this.dateString);
        this.disburseLoan(this.dateString);
    }
    private void createApproveLoanRescheduleRequestWithRecalculationEnabledWithPrincipalCompoundingOff() {
        LOG.info(
                "---------------------------------CREATING LOAN RESCHEDULE REQUEST FOR LOAN WITH RECALCULATION------------------------------------");
        final String requestJSON = new LoanRescheduleRequestTestBuilder().updateGraceOnPrincipal(null).updateGraceOnInterest(null)
                .updateExtraTerms(null).updateRescheduleFromDate("01 March 2015").updateAdjustedDueDate("01 July 2015")
                .updateRecalculateInterest(true).build(this.loanId.toString());
        this.loanRescheduleRequestId = this.loanRescheduleRequestHelper.createLoanRescheduleRequest(requestJSON);
        this.loanRescheduleRequestHelper.verifyCreationOfLoanRescheduleRequest(this.loanRescheduleRequestId);
        LOG.info("Successfully created loan reschedule request (ID: {} )", this.loanRescheduleRequestId);
        final String aproveRequestJSON = new LoanRescheduleRequestTestBuilder().getApproveLoanRescheduleRequestJSON();
        this.loanRescheduleRequestHelper.approveLoanRescheduleRequest(this.loanRescheduleRequestId, aproveRequestJSON);
        final HashMap response = (HashMap) this.loanRescheduleRequestHelper.getLoanRescheduleRequest(loanRescheduleRequestId, "statusEnum");
        assertTrue((Boolean) response.get("approved"));
        LOG.info("Successfully approved loan reschedule request (ID: {})", this.loanRescheduleRequestId);
        final Map repaymentSchedule = (Map) this.loanTransactionHelper.getLoanDetail(requestSpec, generalResponseSpec, loanId,
                "repaymentSchedule");
        final ArrayList periods = (ArrayList) repaymentSchedule.get("periods");
        HashMap period = (HashMap) periods.get(5);
        Float totalDueForPeriod = (Float) period.get("totalDueForPeriod");
        final HashMap loanSummary = this.loanTransactionHelper.getLoanSummary(requestSpec, generalResponseSpec, loanId);
        final Float totalExpectedRepayment = (Float) loanSummary.get("totalExpectedRepayment");
        assertEquals(798, totalDueForPeriod.intValue(), "EXPECTED REPAYMENT is NOK");
        assertEquals(22567, totalExpectedRepayment.intValue(), "TOTAL EXPECTED REPAYMENT is NOK");
        LOG.info("Successfully approved loan reschedule request (ID: {})", this.loanRescheduleRequestId);
    }
}
