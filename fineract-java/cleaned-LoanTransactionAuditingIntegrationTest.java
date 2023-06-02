
package org.apache.fineract.integrationtests;
import static org.apache.fineract.infrastructure.core.domain.AuditableFieldsConstants.CREATED_BY;
import static org.apache.fineract.infrastructure.core.domain.AuditableFieldsConstants.CREATED_DATE;
import static org.apache.fineract.infrastructure.core.domain.AuditableFieldsConstants.LAST_MODIFIED_BY;
import static org.apache.fineract.infrastructure.core.domain.AuditableFieldsConstants.LAST_MODIFIED_DATE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.apache.fineract.integrationtests.common.ClientHelper;
import org.apache.fineract.integrationtests.common.Utils;
import org.apache.fineract.integrationtests.common.accounting.Account;
import org.apache.fineract.integrationtests.common.accounting.AccountHelper;
import org.apache.fineract.integrationtests.common.loans.LoanApplicationTestBuilder;
import org.apache.fineract.integrationtests.common.loans.LoanProductTestBuilder;
import org.apache.fineract.integrationtests.common.loans.LoanStatusChecker;
import org.apache.fineract.integrationtests.common.loans.LoanTransactionHelper;
import org.apache.fineract.integrationtests.common.organisation.StaffHelper;
import org.apache.fineract.integrationtests.useradministration.users.UserHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class LoanTransactionAuditingIntegrationTest {
    private static final Logger LOG = LoggerFactory.getLogger(LoanTransactionAuditingIntegrationTest.class);
    private ResponseSpecification responseSpec;
    private RequestSpecification requestSpec;
    private LoanTransactionHelper loanTransactionHelper;
    private AccountHelper accountHelper;
    @BeforeEach
    public void setup() {
        Utils.initializeRESTAssured();
        this.requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        this.requestSpec.header("Authorization", "Basic " + Utils.loginIntoServerAndGetBase64EncodedAuthenticationKey());
        this.responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
        this.loanTransactionHelper = new LoanTransactionHelper(this.requestSpec, this.responseSpec);
        this.accountHelper = new AccountHelper(this.requestSpec, this.responseSpec);
    }
    @Test
    public void checkAuditDates() throws InterruptedException {
        final Integer staffId = StaffHelper.createStaff(this.requestSpec, this.responseSpec);
        String username = Utils.randomNameGenerator("user", 8);
        final Integer userId = (Integer) UserHelper.createUser(this.requestSpec, this.responseSpec, 1, staffId, username, "password",
                "resourceId");
        LOG.info("-------------------------Creating Client---------------------------");
        final Integer clientID = ClientHelper.createClient(requestSpec, responseSpec);
        ClientHelper.verifyClientCreatedOnServer(requestSpec, responseSpec, clientID);
        LOG.info("-------------------------Creating Loan---------------------------");
        final Account assetAccount = this.accountHelper.createAssetAccount();
        final Account incomeAccount = this.accountHelper.createIncomeAccount();
        final Account expenseAccount = this.accountHelper.createExpenseAccount();
        final Account overpaymentAccount = this.accountHelper.createLiabilityAccount();
        final Integer loanProductID = createLoanProduct("0", "0", LoanProductTestBuilder.DEFAULT_STRATEGY, "2", assetAccount, incomeAccount,
                expenseAccount, overpaymentAccount);
        final Integer loanID = applyForLoanApplicationWithPaymentStrategyAndPastMonth(clientID, loanProductID, Collections.emptyList(),
                null, "10000", LoanApplicationTestBuilder.DEFAULT_STRATEGY, "10 July 2022");
        Assertions.assertNotNull(loanID);
        HashMap loanStatusHashMap = LoanStatusChecker.getStatusOfLoan(this.requestSpec, this.responseSpec, loanID);
        LoanStatusChecker.verifyLoanIsPending(loanStatusHashMap);
        LOG.info("-----------------------------------APPROVE LOAN-----------------------------------------");
        loanStatusHashMap = this.loanTransactionHelper.approveLoan("11 July 2022", loanID);
        LoanStatusChecker.verifyLoanIsApproved(loanStatusHashMap);
        loanStatusHashMap = this.loanTransactionHelper.disburseLoan("11 July 2022", loanID, "10000");
        LoanStatusChecker.verifyLoanIsActive(loanStatusHashMap);
        OffsetDateTime now = OffsetDateTime.now(ZoneId.of("Asia/Kolkata"));
        if (now.getSecond() > 56) {
            Thread.sleep(5000);
            now = OffsetDateTime.now(ZoneId.of("Asia/Kolkata"));
        }
        HashMap repaymentDetails = this.loanTransactionHelper.makeRepayment("11 July 2022", 100.0f, loanID);
        Integer transactionId = (Integer) repaymentDetails.get("resourceId");
        HashMap auditFieldsResponse = LoanTransactionHelper.getLoanTransactionAuditFields(requestSpec, responseSpec, loanID, transactionId,
                "");
        OffsetDateTime createdDate = OffsetDateTime.parse((String) auditFieldsResponse.get(CREATED_DATE),
                DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        OffsetDateTime lastModifiedDate = OffsetDateTime.parse((String) auditFieldsResponse.get(LAST_MODIFIED_DATE),
                DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        LOG.info("-------------------------Check Audit dates---------------------------");
        assertEquals(1, auditFieldsResponse.get(CREATED_BY));
        assertEquals(now.getYear(), createdDate.getYear());
        assertEquals(now.getMonth(), createdDate.getMonth());
        assertEquals(now.getDayOfMonth(), createdDate.getDayOfMonth());
        assertEquals(now.getHour(), createdDate.getHour());
        assertEquals(now.getMinute(), createdDate.getMinute());
        assertEquals(1, auditFieldsResponse.get(LAST_MODIFIED_BY));
        assertEquals(now.getYear(), lastModifiedDate.getYear());
        assertEquals(now.getMonth(), lastModifiedDate.getMonth());
        assertEquals(now.getDayOfMonth(), lastModifiedDate.getDayOfMonth());
        assertEquals(now.getHour(), lastModifiedDate.getHour());
        assertEquals(now.getMinute(), lastModifiedDate.getMinute());
        Thread.sleep(2000);
        this.requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        this.requestSpec.header("Authorization",
                "Basic " + Utils.loginIntoServerAndGetBase64EncodedAuthenticationKey(username, "password"));
        this.loanTransactionHelper = new LoanTransactionHelper(this.requestSpec, this.responseSpec);
        this.loanTransactionHelper.reverseRepayment(loanID, transactionId, "11 July 2022");
        now = OffsetDateTime.now(ZoneId.of("Asia/Kolkata"));
        auditFieldsResponse = LoanTransactionHelper.getLoanTransactionAuditFields(requestSpec, responseSpec, loanID, transactionId, "");
        createdDate = OffsetDateTime.parse((String) auditFieldsResponse.get(CREATED_DATE), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        lastModifiedDate = OffsetDateTime.parse((String) auditFieldsResponse.get(LAST_MODIFIED_DATE),
                DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        LOG.info("-------------------------Check Audit dates---------------------------");
        assertEquals(1, auditFieldsResponse.get(CREATED_BY));
        assertEquals(now.getYear(), createdDate.getYear());
        assertEquals(now.getMonth(), createdDate.getMonth());
        assertEquals(now.getDayOfMonth(), createdDate.getDayOfMonth());
        assertEquals(now.getHour(), createdDate.getHour());
        assertEquals(now.getMinute(), createdDate.getMinute());
        now = OffsetDateTime.now(ZoneId.of("Asia/Kolkata"));
        assertEquals(userId, auditFieldsResponse.get(LAST_MODIFIED_BY));
        assertEquals(now.getYear(), lastModifiedDate.getYear());
        assertEquals(now.getMonth(), lastModifiedDate.getMonth());
        assertEquals(now.getDayOfMonth(), lastModifiedDate.getDayOfMonth());
        assertEquals(now.getHour(), lastModifiedDate.getHour());
        assertEquals(now.getMinute(), lastModifiedDate.getMinute());
    }
    private Integer applyForLoanApplicationWithPaymentStrategyAndPastMonth(final Integer clientID, final Integer loanProductID,
            List<HashMap> charges, final String savingsId, String principal, final String repaymentStrategy, final String submittedOnDate) {
        LOG.info("--------------------------------APPLYING FOR LOAN APPLICATION--------------------------------");
        final String loanApplicationJSON = new LoanApplicationTestBuilder() 
                .withPrincipal(principal) 
                .withLoanTermFrequency("6") 
                .withLoanTermFrequencyAsMonths() 
                .withNumberOfRepayments("6") 
                .withRepaymentEveryAfter("1") 
                .withRepaymentFrequencyTypeAsMonths() 
                .withInterestRatePerPeriod("2") 
                .withAmortizationTypeAsEqualInstallments() 
                .withInterestTypeAsFlatBalance() 
                .withInterestCalculationPeriodTypeSameAsRepaymentPeriod() 
                .withExpectedDisbursementDate(submittedOnDate) 
                .withSubmittedOnDate(submittedOnDate) 
                .withwithRepaymentStrategy(repaymentStrategy) 
                .withCharges(charges).build(clientID.toString(), loanProductID.toString(), savingsId);
        return this.loanTransactionHelper.getLoanId(loanApplicationJSON);
    }
    private Integer createLoanProduct(final String inMultiplesOf, final String digitsAfterDecimal, final String repaymentStrategy,
            final String accountingRule, final Account... accounts) {
        LOG.info("------------------------------CREATING NEW LOAN PRODUCT ---------------------------------------");
        final String loanProductJSON = new LoanProductTestBuilder() 
                .withPrincipal("10000000.00") 
                .withNumberOfRepayments("24") 
                .withRepaymentAfterEvery("1") 
                .withRepaymentTypeAsMonth() 
                .withinterestRatePerPeriod("2") 
                .withInterestRateFrequencyTypeAsMonths() 
                .withRepaymentStrategy(repaymentStrategy) 
                .withAmortizationTypeAsEqualPrincipalPayment() 
                .withInterestTypeAsDecliningBalance() 
                .currencyDetails(digitsAfterDecimal, inMultiplesOf).withAccounting(accountingRule, accounts).build(null);
        return this.loanTransactionHelper.getLoanProductId(loanProductJSON);
    }
}
