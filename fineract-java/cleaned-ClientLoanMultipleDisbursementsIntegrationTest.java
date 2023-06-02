
package org.apache.fineract.integrationtests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.fineract.integrationtests.common.ClientHelper;
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
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ClientLoanMultipleDisbursementsIntegrationTest {
    private static final Logger LOG = LoggerFactory.getLogger(ClientLoanMultipleDisbursementsIntegrationTest.class);
    private ResponseSpecification responseSpec;
    private RequestSpecification requestSpec;
    private LoanTransactionHelper loanTransactionHelper;
    @BeforeEach
    public void setup() {
        Utils.initializeRESTAssured();
        this.requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        this.requestSpec.header("Authorization", "Basic " + Utils.loginIntoServerAndGetBase64EncodedAuthenticationKey());
        this.responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
        this.loanTransactionHelper = new LoanTransactionHelper(this.requestSpec, this.responseSpec);
    }
    private Integer createLoanProduct(final boolean multiDisburseLoan) {
        LOG.info("------------------------------CREATING NEW LOAN PRODUCT ---------------------------------------");
        LoanProductTestBuilder builder = new LoanProductTestBuilder() 
                .withPrincipal("12,000.00") 
                .withNumberOfRepayments("4") 
                .withRepaymentAfterEvery("1") 
                .withRepaymentTypeAsMonth() 
                .withinterestRatePerPeriod("1") 
                .withInterestRateFrequencyTypeAsMonths() 
                .withAmortizationTypeAsEqualInstallments() 
                .withInterestTypeAsDecliningBalance() 
                .withTranches(multiDisburseLoan);
        if (multiDisburseLoan) {
            builder = builder.withInterestCalculationPeriodTypeAsRepaymentPeriod(true);
            builder = builder.withMaxTrancheCount("30");
        }
        final String loanProductJSON = builder.build(null);
        return this.loanTransactionHelper.getLoanProductId(loanProductJSON);
    }
    private Integer applyForLoanApplicationWithTranches(final Integer clientID, final Integer loanProductID, final String savingsId,
            String principal, List<HashMap> tranches, String submitDate) {
        LOG.info("--------------------------------APPLYING FOR LOAN APPLICATION--------------------------------");
        final String loanApplicationJSON = new LoanApplicationTestBuilder() 
                .withPrincipal(principal) 
                .withLoanTermFrequency("4") 
                .withLoanTermFrequencyAsMonths() 
                .withNumberOfRepayments("4") 
                .withRepaymentEveryAfter("1") 
                .withRepaymentFrequencyTypeAsMonths() 
                .withInterestRatePerPeriod("2") 
                .withAmortizationTypeAsEqualInstallments() 
                .withInterestTypeAsDecliningBalance() 
                .withInterestCalculationPeriodTypeSameAsRepaymentPeriod() 
                .withExpectedDisbursementDate(submitDate) 
                .withTranches(tranches) 
                .withSubmittedOnDate(submitDate) 
                .build(clientID.toString(), loanProductID.toString(), savingsId);
        return this.loanTransactionHelper.getLoanId(loanApplicationJSON);
    }
    private HashMap createTrancheDetail(final String date, final String amount) {
        HashMap detail = new HashMap();
        detail.put("expectedDisbursementDate", date);
        detail.put("principal", amount);
        return detail;
    }
    @Test
    public void checkThatAllMultiDisbursalsAppearOnLoanScheduleAndOutStandingBalanceIsZeroTest() {
        this.loanTransactionHelper = new LoanTransactionHelper(this.requestSpec, this.responseSpec);
        final Integer clientID = ClientHelper.createClient(this.requestSpec, this.responseSpec);
        ClientHelper.verifyClientCreatedOnServer(this.requestSpec, this.responseSpec, clientID);
        boolean allowMultipleDisbursals = true;
        final Integer loanProductID = createLoanProduct(allowMultipleDisbursals);
        Assertions.assertNotNull(loanProductID);
        final String savingsId = null;
        final String principal = "12,000.00";
        LOG.info("-----------------------------------10 Tranches--------------------------------------");
        List<HashMap> tranches = new ArrayList<>();
        tranches.add(createTrancheDetail("01 January 2021", "1"));
        tranches.add(createTrancheDetail("02 January 2021", "2"));
        tranches.add(createTrancheDetail("03 January 2021", "4"));
        tranches.add(createTrancheDetail("04 January 2021", "8"));
        tranches.add(createTrancheDetail("05 January 2021", "16"));
        tranches.add(createTrancheDetail("06 January 2021", "32"));
        tranches.add(createTrancheDetail("07 January 2021", "64"));
        tranches.add(createTrancheDetail("08 January 2021", "128"));
        tranches.add(createTrancheDetail("09 January 2021", "256"));
        tranches.add(createTrancheDetail("10 January 2021", "512"));
        String submitDate = "01 January 2021";
        final Integer loanID = applyForLoanApplicationWithTranches(clientID, loanProductID, savingsId, principal, tranches, submitDate);
        Assertions.assertNotNull(loanID);
        HashMap loanStatusHashMap = LoanStatusChecker.getStatusOfLoan(this.requestSpec, this.responseSpec, loanID);
        LoanStatusChecker.verifyLoanIsPending(loanStatusHashMap);
        LOG.info("-----------------------------------APPROVE LOAN-----------------------------------------");
        loanStatusHashMap = this.loanTransactionHelper.approveLoan("01 January 2021", loanID);
        LoanStatusChecker.verifyLoanIsApproved(loanStatusHashMap);
        LoanStatusChecker.verifyLoanIsWaitingForDisbursal(loanStatusHashMap);
        LOG.info("-------------------------------DISBURSE 8 LOANS -------------------------------------------");
        loanStatusHashMap = this.loanTransactionHelper.disburseLoan("12 January 2021", loanID, "1");
        LoanStatusChecker.verifyLoanIsActive(loanStatusHashMap);
        loanStatusHashMap = this.loanTransactionHelper.disburseLoan("12 January 2021", loanID, "2");
        LoanStatusChecker.verifyLoanIsActive(loanStatusHashMap);
        loanStatusHashMap = this.loanTransactionHelper.disburseLoan("12 January 2021", loanID, "4");
        LoanStatusChecker.verifyLoanIsActive(loanStatusHashMap);
        loanStatusHashMap = this.loanTransactionHelper.disburseLoan("13 January 2021", loanID, "8");
        LoanStatusChecker.verifyLoanIsActive(loanStatusHashMap);
        loanStatusHashMap = this.loanTransactionHelper.disburseLoan("14 January 2021", loanID, "16");
        LoanStatusChecker.verifyLoanIsActive(loanStatusHashMap);
        loanStatusHashMap = this.loanTransactionHelper.disburseLoan("14 January 2021", loanID, "32");
        LoanStatusChecker.verifyLoanIsActive(loanStatusHashMap);
        loanStatusHashMap = this.loanTransactionHelper.disburseLoan("15 January 2021", loanID, "64");
        LoanStatusChecker.verifyLoanIsActive(loanStatusHashMap);
        loanStatusHashMap = this.loanTransactionHelper.disburseLoan("15 January 2021", loanID, "128");
        LoanStatusChecker.verifyLoanIsActive(loanStatusHashMap);
        ArrayList<HashMap> loanSchedule = this.loanTransactionHelper.getLoanRepaymentSchedule(this.requestSpec, this.responseSpec, loanID);
        final int loanScheduleLineCount = loanSchedule.size();
        final int expectedLoanScheduleLineCount = 9;
        final int expectedDisbursals = 8;
        final BigDecimal val255 = BigDecimal.valueOf(255.0);
        final BigDecimal expectedTotalPrincipalDisbursed = val255;
        final BigDecimal expectedPrincipalDue = val255;
        final BigDecimal expectedPrincipalLoanBalanceOutstanding = BigDecimal.valueOf(0.0);
        assertEquals(expectedLoanScheduleLineCount, loanScheduleLineCount, "Checking nine lines in schedule");
        int disbursalCount = 0;
        BigDecimal totalPrincipalDisbursed = BigDecimal.ZERO;
        for (int i = 0; i < loanScheduleLineCount - 1; i++) {
            final Integer period = (Integer) loanSchedule.get(i).get("period");
            final BigDecimal principalDisbursed = BigDecimal
                    .valueOf(Double.parseDouble(loanSchedule.get(i).get("principalDisbursed").toString()));
            if (period == null) {
                disbursalCount += 1;
                totalPrincipalDisbursed = totalPrincipalDisbursed.add(principalDisbursed);
            }
        }
        assertEquals(expectedDisbursals, disbursalCount, "Checking for eight disbursals");
        assertEquals(expectedTotalPrincipalDisbursed, totalPrincipalDisbursed, "Checking Principal Disburse is 255");
        final BigDecimal principalDue = BigDecimal.valueOf(Double.parseDouble(loanSchedule.get(8).get("principalDue").toString()));
        assertEquals(expectedPrincipalDue, principalDue, "Checking Principal Due is 255");
        final BigDecimal principalLoanBalanceOutstanding = BigDecimal
                .valueOf(Double.parseDouble(loanSchedule.get(8).get("principalLoanBalanceOutstanding").toString()));
        assertEquals(expectedPrincipalLoanBalanceOutstanding, principalLoanBalanceOutstanding,
                "Checking Principal Loan Balance Outstanding is zero");
    }
}
