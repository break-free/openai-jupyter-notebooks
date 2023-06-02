
package org.apache.fineract.integrationtests.variableinstallments;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.fineract.integrationtests.common.ClientHelper;
import org.apache.fineract.integrationtests.common.CollateralManagementHelper;
import org.apache.fineract.integrationtests.common.Utils;
import org.apache.fineract.integrationtests.common.loans.LoanStatusChecker;
import org.apache.fineract.integrationtests.common.loans.LoanTransactionHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@SuppressWarnings({ "rawtypes", "unchecked" })
public class VariableInstallmentsIntegrationTest {
    private static final Logger LOG = LoggerFactory.getLogger(VariableInstallmentsIntegrationTest.class);
    private static final String NONE = "1";
    private RequestSpecification requestSpec;
    private ResponseSpecification responseSpec;
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
    public void testVariableLoanProductCreation() {
        final String json = VariableInstallmentsDecliningBalanceHelper.createLoanProductWithVaribleConfig(false, NONE);
        final Integer loanProductID = this.loanTransactionHelper.getLoanProductId(json);
        LOG.info("------------------------------RETRIEVING CREATED LOAN PRODUCT DETAILS ---------------------------------------");
        Map loanProduct = (Map) loanTransactionHelper.getLoanProductDetail(requestSpec, responseSpec, loanProductID, "");
        Assertions.assertTrue((Boolean) loanProduct.get("allowVariableInstallments"));
        Assertions.assertEquals(Integer.valueOf(5), loanProduct.get("minimumGap"));
        Assertions.assertEquals(Integer.valueOf(90), loanProduct.get("maximumGap"));
    }
    @Test
    public void testLoanProductCreation() {
        final String josn = VariableInstallmentsDecliningBalanceHelper.createLoanProductWithoutVaribleConfig(false, NONE);
        Integer loanProductID = this.loanTransactionHelper.getLoanProductId(josn);
        LOG.info("------------------------------RETRIEVING CREATED LOAN PRODUCT DETAILS ---------------------------------------");
        Map loanProduct = (Map) loanTransactionHelper.getLoanProductDetail(requestSpec, responseSpec, loanProductID, "");
        Assertions.assertTrue(!(Boolean) loanProduct.get("allowVariableInstallments"));
    }
    @Test
    public void testDeleteInstallmentsWithDecliningBalanceEqualInstallments() {
        VariableIntallmentsTransactionHelper transactionHelper = new VariableIntallmentsTransactionHelper(requestSpec, responseSpec);
        final String loanProductJson = VariableInstallmentsDecliningBalanceHelper.createLoanProductWithVaribleConfig(false, NONE);
        Integer loanProductID = this.loanTransactionHelper.getLoanProductId(loanProductJson);
        final Integer clientID = ClientHelper.createClient(this.requestSpec, this.responseSpec);
        ClientHelper.verifyClientCreatedOnServer(this.requestSpec, this.responseSpec, clientID);
        List<HashMap> collaterals = new ArrayList<>();
        final Integer collateralId = CollateralManagementHelper.createCollateralProduct(this.requestSpec, this.responseSpec);
        Assertions.assertNotNull(collateralId);
        final Integer clientCollateralId = CollateralManagementHelper.createClientCollateral(this.requestSpec, this.responseSpec,
                clientID.toString(), collateralId);
        Assertions.assertNotNull(clientCollateralId);
        addCollaterals(collaterals, clientCollateralId, BigDecimal.valueOf(1));
        final String json = VariableInstallmentsDecliningBalanceHelper.applyForLoanApplication(clientID, loanProductID, null, null,
                "1,00,000.00", collaterals);
        final Integer loanID = this.loanTransactionHelper.getLoanId(json);
        HashMap loanStatusHashMap = LoanStatusChecker.getStatusOfLoan(this.requestSpec, this.responseSpec, loanID);
        LoanStatusChecker.verifyLoanIsPending(loanStatusHashMap);
        Map list = transactionHelper.retrieveSchedule(loanID);
        Map repaymentSchedule = (Map) list.get("repaymentSchedule");
        ArrayList periods = (ArrayList) repaymentSchedule.get("periods");
        ArrayList toDelete = new ArrayList<>();
        toDelete.add(periods.get(1));
        String toDeletedata = VariableInstallmentsDecliningBalanceHelper.createDeleteVariations(toDelete);
        HashMap modifiedReschdule = transactionHelper.validateVariations(toDeletedata, loanID);
        ArrayList newperiods = (ArrayList) modifiedReschdule.get("periods");
        ArrayList toVerifyData = VariableInstallmentsDecliningBalanceHelper.constructVerifyData(
                new String[] { "20 November 2011", "20 December 2011", "20 January 2012" },
                new String[] { "34675.47", "34675.47", "36756.26" });
        assertAfterSubmit(newperiods, toVerifyData);
        transactionHelper.submitVariations(toDeletedata, loanID);
        list = transactionHelper.retrieveSchedule(loanID);
        repaymentSchedule = (Map) list.get("repaymentSchedule");
        periods = (ArrayList) repaymentSchedule.get("periods");
        periods.remove(0); 
        assertAfterSubmit(periods, toVerifyData);
    }
    private HashMap<String, String> collaterals(Integer collateralId, BigDecimal quantity) {
        HashMap<String, String> collateral = new HashMap<String, String>(2);
        collateral.put("clientCollateralId", collateralId.toString());
        collateral.put("quantity", quantity.toString());
        return collateral;
    }
    private void addCollaterals(List<HashMap> collaterals, Integer collateralId, BigDecimal quantity) {
        collaterals.add(collaterals(collateralId, quantity));
    }
    private void assertAfterSubmit(ArrayList<Map> serverData, ArrayList<Map> expectedData) {
        Assertions.assertTrue(serverData.size() == expectedData.size());
        for (int i = 0; i < serverData.size(); i++) {
            Map<String, Object> serverMap = serverData.get(i);
            Map<String, Object> expectedMap = expectedData.get(i);
            Assertions.assertTrue(VariableInstallmentsDecliningBalanceHelper.formatDate((ArrayList) serverMap.get("dueDate"))
                    .equals(expectedMap.get("dueDate")));
            Assertions.assertTrue(serverMap.get("totalOutstandingForPeriod").toString().equals(expectedMap.get("installmentAmount")));
        }
    }
    @Test
    public void testAddInstallmentsWithDecliningBalanceEqualInstallments() {
        VariableIntallmentsTransactionHelper transactionHelper = new VariableIntallmentsTransactionHelper(requestSpec, responseSpec);
        final String loanProductJson = VariableInstallmentsDecliningBalanceHelper.createLoanProductWithVaribleConfig(false, NONE);
        Integer loanProductID = this.loanTransactionHelper.getLoanProductId(loanProductJson);
        final Integer clientID = ClientHelper.createClient(this.requestSpec, this.responseSpec);
        ClientHelper.verifyClientCreatedOnServer(this.requestSpec, this.responseSpec, clientID);
        List<HashMap> collaterals = new ArrayList<>();
        final Integer collateralId = CollateralManagementHelper.createCollateralProduct(this.requestSpec, this.responseSpec);
        Assertions.assertNotNull(collateralId);
        final Integer clientCollateralId = CollateralManagementHelper.createClientCollateral(this.requestSpec, this.responseSpec,
                String.valueOf(clientID), collateralId);
        Assertions.assertNotNull(clientCollateralId);
        addCollaterals(collaterals, clientCollateralId, BigDecimal.valueOf(1));
        final String json = VariableInstallmentsDecliningBalanceHelper.applyForLoanApplication(clientID, loanProductID, null, null,
                "1,00,000.00", collaterals);
        final Integer loanID = this.loanTransactionHelper.getLoanId(json);
        HashMap loanStatusHashMap = LoanStatusChecker.getStatusOfLoan(this.requestSpec, this.responseSpec, loanID);
        LoanStatusChecker.verifyLoanIsPending(loanStatusHashMap);
        Map list = transactionHelper.retrieveSchedule(loanID);
        Map repaymentSchedule = (Map) list.get("repaymentSchedule");
        ArrayList periods = (ArrayList) repaymentSchedule.get("periods");
        String addVariationsjsondata = VariableInstallmentsDecliningBalanceHelper.createAddVariations();
        HashMap modifiedReschdule = transactionHelper.validateVariations(addVariationsjsondata, loanID);
        ArrayList newperiods = (ArrayList) modifiedReschdule.get("periods");
        ArrayList toVerifyData = VariableInstallmentsDecliningBalanceHelper.constructVerifyData(
                new String[] { "20 October 2011", "31 October 2011", "20 November 2011", "20 December 2011", "20 January 2012" },
                new String[] { "21215.84", "5000.0", "26477.31", "26477.31", "25947.7" });
        assertAfterSubmit(newperiods, toVerifyData);
        transactionHelper.submitVariations(addVariationsjsondata, loanID);
        list = transactionHelper.retrieveSchedule(loanID);
        repaymentSchedule = (Map) list.get("repaymentSchedule");
        periods = (ArrayList) repaymentSchedule.get("periods");
        periods.remove(0); 
        assertAfterSubmit(periods, toVerifyData);
    }
    @Test
    public void testModifyInstallmentWithDecliningBalanceEqualInstallments() {
        VariableIntallmentsTransactionHelper transactionHelper = new VariableIntallmentsTransactionHelper(requestSpec, responseSpec);
        final String loanProductJson = VariableInstallmentsDecliningBalanceHelper.createLoanProductWithVaribleConfig(false, NONE);
        Integer loanProductID = this.loanTransactionHelper.getLoanProductId(loanProductJson);
        final Integer clientID = ClientHelper.createClient(this.requestSpec, this.responseSpec);
        ClientHelper.verifyClientCreatedOnServer(this.requestSpec, this.responseSpec, clientID);
        List<HashMap> collaterals = new ArrayList<>();
        final Integer collateralId = CollateralManagementHelper.createCollateralProduct(this.requestSpec, this.responseSpec);
        Assertions.assertNotNull(collateralId);
        final Integer clientCollateralId = CollateralManagementHelper.createClientCollateral(this.requestSpec, this.responseSpec,
                String.valueOf(clientID), collateralId);
        Assertions.assertNotNull(clientCollateralId);
        addCollaterals(collaterals, clientCollateralId, BigDecimal.valueOf(1));
        final String json = VariableInstallmentsDecliningBalanceHelper.applyForLoanApplication(clientID, loanProductID, null, null,
                "1,00,000.00", collaterals);
        final Integer loanID = this.loanTransactionHelper.getLoanId(json);
        HashMap loanStatusHashMap = LoanStatusChecker.getStatusOfLoan(this.requestSpec, this.responseSpec, loanID);
        LoanStatusChecker.verifyLoanIsPending(loanStatusHashMap);
        Map list = transactionHelper.retrieveSchedule(loanID);
        Map repaymentSchedule = (Map) list.get("repaymentSchedule");
        ArrayList periods = (ArrayList) repaymentSchedule.get("periods");
        String addVariationsjsondata = VariableInstallmentsDecliningBalanceHelper.createModifiyVariations((Map) periods.get(1)); 
        HashMap modifiedReschdule = transactionHelper.validateVariations(addVariationsjsondata, loanID);
        ArrayList newperiods = (ArrayList) modifiedReschdule.get("periods");
        ArrayList toVerifyData = VariableInstallmentsDecliningBalanceHelper.constructVerifyData(
                new String[] { "20 October 2011", "20 November 2011", "20 December 2011", "20 January 2012" },
                new String[] { "30000.0", "24966.34", "24966.34", "24966.33" });
        assertAfterSubmit(newperiods, toVerifyData);
        transactionHelper.submitVariations(addVariationsjsondata, loanID);
        list = transactionHelper.retrieveSchedule(loanID);
        repaymentSchedule = (Map) list.get("repaymentSchedule");
        periods = (ArrayList) repaymentSchedule.get("periods");
        periods.remove(0); 
        assertAfterSubmit(periods, toVerifyData);
    }
    @Test
    public void testAllVariationsDecliningBalancewithEqualInstallments() {
        VariableIntallmentsTransactionHelper transactionHelper = new VariableIntallmentsTransactionHelper(requestSpec, responseSpec);
        final String loanProductJson = VariableInstallmentsDecliningBalanceHelper.createLoanProductWithVaribleConfig(false, NONE);
        Integer loanProductID = this.loanTransactionHelper.getLoanProductId(loanProductJson);
        final Integer clientID = ClientHelper.createClient(this.requestSpec, this.responseSpec);
        ClientHelper.verifyClientCreatedOnServer(this.requestSpec, this.responseSpec, clientID);
        List<HashMap> collaterals = new ArrayList<>();
        final Integer collateralId = CollateralManagementHelper.createCollateralProduct(this.requestSpec, this.responseSpec);
        Assertions.assertNotNull(collateralId);
        final Integer clientCollateralId = CollateralManagementHelper.createClientCollateral(this.requestSpec, this.responseSpec,
                String.valueOf(clientID), collateralId);
        Assertions.assertNotNull(clientCollateralId);
        addCollaterals(collaterals, clientCollateralId, BigDecimal.valueOf(1));
        final String json = VariableInstallmentsDecliningBalanceHelper.applyForLoanApplication(clientID, loanProductID, null, null,
                "1,00,000.00", collaterals);
        final Integer loanID = this.loanTransactionHelper.getLoanId(json);
        HashMap loanStatusHashMap = LoanStatusChecker.getStatusOfLoan(this.requestSpec, this.responseSpec, loanID);
        LoanStatusChecker.verifyLoanIsPending(loanStatusHashMap);
        Map list = transactionHelper.retrieveSchedule(loanID);
        Map repaymentSchedule = (Map) list.get("repaymentSchedule");
        ArrayList periods = (ArrayList) repaymentSchedule.get("periods");
        String addVariationsjsondata = VariableInstallmentsDecliningBalanceHelper.createAllVariations(); 
        HashMap modifiedReschdule = transactionHelper.validateVariations(addVariationsjsondata, loanID);
        ArrayList newperiods = (ArrayList) modifiedReschdule.get("periods");
        ArrayList toVerifyData = VariableInstallmentsDecliningBalanceHelper.constructVerifyData(
                new String[] { "20 October 2011", "20 November 2011", "25 December 2011", "20 January 2012" },
                new String[] { "26262.38", "30000.0", "5000.0", "44077.0" });
        assertAfterSubmit(newperiods, toVerifyData);
        transactionHelper.submitVariations(addVariationsjsondata, loanID);
        list = transactionHelper.retrieveSchedule(loanID);
        repaymentSchedule = (Map) list.get("repaymentSchedule");
        periods = (ArrayList) repaymentSchedule.get("periods");
        periods.remove(0); 
        assertAfterSubmit(periods, toVerifyData);
    }
    @Test
    public void testAllVariationsDecliningBalancewithEqualPrincipal() {
        VariableIntallmentsTransactionHelper transactionHelper = new VariableIntallmentsTransactionHelper(requestSpec, responseSpec);
        final String loanProductJson = VariableInstallmentsDecliningBalanceHelper
                .createLoanProductWithVaribleConfigwithEqualPrincipal(false, NONE);
        Integer loanProductID = this.loanTransactionHelper.getLoanProductId(loanProductJson);
        final Integer clientID = ClientHelper.createClient(this.requestSpec, this.responseSpec);
        ClientHelper.verifyClientCreatedOnServer(this.requestSpec, this.responseSpec, clientID);
        List<HashMap> collaterals = new ArrayList<>();
        final Integer collateralId = CollateralManagementHelper.createCollateralProduct(this.requestSpec, this.responseSpec);
        Assertions.assertNotNull(collateralId);
        final Integer clientCollateralId = CollateralManagementHelper.createClientCollateral(this.requestSpec, this.responseSpec,
                clientID.toString(), collateralId);
        Assertions.assertNotNull(clientCollateralId);
        addCollaterals(collaterals, clientCollateralId, BigDecimal.valueOf(1));
        final String json = VariableInstallmentsDecliningBalanceHelper.applyForLoanApplicationWithEqualPrincipal(clientID, loanProductID,
                null, null, "1,00,000.00", collaterals);
        final Integer loanID = this.loanTransactionHelper.getLoanId(json);
        HashMap loanStatusHashMap = LoanStatusChecker.getStatusOfLoan(this.requestSpec, this.responseSpec, loanID);
        LoanStatusChecker.verifyLoanIsPending(loanStatusHashMap);
        Map list = transactionHelper.retrieveSchedule(loanID);
        Map repaymentSchedule = (Map) list.get("repaymentSchedule");
        ArrayList periods = (ArrayList) repaymentSchedule.get("periods");
        String addVariationsjsondata = VariableInstallmentsDecliningBalanceHelper.createAllVariationsWithEqualPrincipal(); 
        HashMap modifiedReschdule = transactionHelper.validateVariations(addVariationsjsondata, loanID);
        ArrayList newperiods = (ArrayList) modifiedReschdule.get("periods");
        ArrayList toVerifyData = VariableInstallmentsDecliningBalanceHelper.constructVerifyData(
                new String[] { "20 October 2011", "20 November 2011", "25 December 2011", "20 January 2012" },
                new String[] { "27000.0", "31500.0", "6045.16", "40670.97" });
        assertAfterSubmit(newperiods, toVerifyData);
        transactionHelper.submitVariations(addVariationsjsondata, loanID);
        list = transactionHelper.retrieveSchedule(loanID);
        repaymentSchedule = (Map) list.get("repaymentSchedule");
        periods = (ArrayList) repaymentSchedule.get("periods");
        periods.remove(0); 
        assertAfterSubmit(periods, toVerifyData);
    }
    @Test
    public void testModifyDatesWithDecliningBalanceEqualInstallments() {
        VariableIntallmentsTransactionHelper transactionHelper = new VariableIntallmentsTransactionHelper(requestSpec, responseSpec);
        final String loanProductJson = VariableInstallmentsDecliningBalanceHelper.createLoanProductWithVaribleConfig(false, NONE);
        Integer loanProductID = this.loanTransactionHelper.getLoanProductId(loanProductJson);
        final Integer clientID = ClientHelper.createClient(this.requestSpec, this.responseSpec);
        ClientHelper.verifyClientCreatedOnServer(this.requestSpec, this.responseSpec, clientID);
        List<HashMap> collaterals = new ArrayList<>();
        final Integer collateralId = CollateralManagementHelper.createCollateralProduct(this.requestSpec, this.responseSpec);
        Assertions.assertNotNull(collateralId);
        final Integer clientCollateralId = CollateralManagementHelper.createClientCollateral(this.requestSpec, this.responseSpec,
                String.valueOf(clientID), collateralId);
        Assertions.assertNotNull(clientCollateralId);
        addCollaterals(collaterals, clientCollateralId, BigDecimal.valueOf(1));
        final String json = VariableInstallmentsDecliningBalanceHelper.applyForLoanApplication(clientID, loanProductID, null, null,
                "1,00,000.00", collaterals);
        final Integer loanID = this.loanTransactionHelper.getLoanId(json);
        HashMap loanStatusHashMap = LoanStatusChecker.getStatusOfLoan(this.requestSpec, this.responseSpec, loanID);
        LoanStatusChecker.verifyLoanIsPending(loanStatusHashMap);
        Map list = transactionHelper.retrieveSchedule(loanID);
        Map repaymentSchedule = (Map) list.get("repaymentSchedule");
        ArrayList periods = (ArrayList) repaymentSchedule.get("periods");
        String addVariationsjsondata = VariableInstallmentsDecliningBalanceHelper.createModifiyDateVariations(
                new String[] { "20 December 2011", "20 January 2012" }, new String[] { "04 January 2012", "08 February 2012" },
                new String[] { "20000" }); 
        HashMap modifiedReschdule = transactionHelper.validateVariations(addVariationsjsondata, loanID);
        ArrayList newperiods = (ArrayList) modifiedReschdule.get("periods");
        ArrayList toVerifyData = VariableInstallmentsDecliningBalanceHelper.constructVerifyData(
                new String[] { "20 October 2011", "20 November 2011", "04 January 2012", "08 February 2012" },
                new String[] { "26262.38", "26262.38", "20000.0", "33242.97" });
        assertAfterSubmit(newperiods, toVerifyData);
        transactionHelper.submitVariations(addVariationsjsondata, loanID);
        list = transactionHelper.retrieveSchedule(loanID);
        repaymentSchedule = (Map) list.get("repaymentSchedule");
        periods = (ArrayList) repaymentSchedule.get("periods");
        periods.remove(0); 
        assertAfterSubmit(periods, toVerifyData);
    }
    @Test
    public void testDeleteInstallmentsWithInterestTypeFlat() {
        VariableIntallmentsTransactionHelper transactionHelper = new VariableIntallmentsTransactionHelper(requestSpec, responseSpec);
        final String loanProductJson = VariableInstallmentsFlatHelper.createLoanProductWithVaribleConfig(false, NONE);
        Integer loanProductID = this.loanTransactionHelper.getLoanProductId(loanProductJson);
        final Integer clientID = ClientHelper.createClient(this.requestSpec, this.responseSpec);
        ClientHelper.verifyClientCreatedOnServer(this.requestSpec, this.responseSpec, clientID);
        List<HashMap> collaterals = new ArrayList<>();
        final Integer collateralId = CollateralManagementHelper.createCollateralProduct(this.requestSpec, this.responseSpec);
        Assertions.assertNotNull(collateralId);
        final Integer clientCollateralId = CollateralManagementHelper.createClientCollateral(this.requestSpec, this.responseSpec,
                String.valueOf(clientID), collateralId);
        Assertions.assertNotNull(clientCollateralId);
        addCollaterals(collaterals, clientCollateralId, BigDecimal.valueOf(1));
        final String json = VariableInstallmentsFlatHelper.applyForLoanApplication(clientID, loanProductID, null, null, "1,00,000.00",
                collaterals);
        final Integer loanID = this.loanTransactionHelper.getLoanId(json);
        HashMap loanStatusHashMap = LoanStatusChecker.getStatusOfLoan(this.requestSpec, this.responseSpec, loanID);
        LoanStatusChecker.verifyLoanIsPending(loanStatusHashMap);
        Map list = transactionHelper.retrieveSchedule(loanID);
        Map repaymentSchedule = (Map) list.get("repaymentSchedule");
        ArrayList periods = (ArrayList) repaymentSchedule.get("periods");
        ArrayList toDelete = new ArrayList<>();
        toDelete.add(periods.get(1));
        String toDeletedata = VariableInstallmentsFlatHelper.createDeleteVariations(toDelete);
        HashMap modifiedReschdule = transactionHelper.validateVariations(toDeletedata, loanID);
        ArrayList newperiods = (ArrayList) modifiedReschdule.get("periods");
        ArrayList toVerifyData = VariableInstallmentsFlatHelper.constructVerifyData(
                new String[] { "20 November 2011", "20 December 2011", "20 January 2012" },
                new String[] { "36000.0", "36000.0", "36000.0" });
        assertAfterSubmit(newperiods, toVerifyData);
        transactionHelper.submitVariations(toDeletedata, loanID);
        list = transactionHelper.retrieveSchedule(loanID);
        repaymentSchedule = (Map) list.get("repaymentSchedule");
        periods = (ArrayList) repaymentSchedule.get("periods");
        periods.remove(0); 
        assertAfterSubmit(periods, toVerifyData);
    }
    @Test
    public void testAddInstallmentsWithInterestTypeFlat() {
        VariableIntallmentsTransactionHelper transactionHelper = new VariableIntallmentsTransactionHelper(requestSpec, responseSpec);
        final String loanProductJson = VariableInstallmentsFlatHelper.createLoanProductWithVaribleConfig(false, NONE);
        Integer loanProductID = this.loanTransactionHelper.getLoanProductId(loanProductJson);
        final Integer clientID = ClientHelper.createClient(this.requestSpec, this.responseSpec);
        ClientHelper.verifyClientCreatedOnServer(this.requestSpec, this.responseSpec, clientID);
        List<HashMap> collaterals = new ArrayList<>();
        final Integer collateralId = CollateralManagementHelper.createCollateralProduct(this.requestSpec, this.responseSpec);
        Assertions.assertNotNull(collateralId);
        final Integer clientCollateralId = CollateralManagementHelper.createClientCollateral(this.requestSpec, this.responseSpec,
                String.valueOf(clientID), collateralId);
        Assertions.assertNotNull(clientCollateralId);
        addCollaterals(collaterals, clientCollateralId, BigDecimal.valueOf(1));
        final String json = VariableInstallmentsFlatHelper.applyForLoanApplication(clientID, loanProductID, null, null, "1,00,000.00",
                collaterals);
        final Integer loanID = this.loanTransactionHelper.getLoanId(json);
        HashMap loanStatusHashMap = LoanStatusChecker.getStatusOfLoan(this.requestSpec, this.responseSpec, loanID);
        LoanStatusChecker.verifyLoanIsPending(loanStatusHashMap);
        Map list = transactionHelper.retrieveSchedule(loanID);
        Map repaymentSchedule = (Map) list.get("repaymentSchedule");
        ArrayList periods = (ArrayList) repaymentSchedule.get("periods");
        String addVariationsjsondata = VariableInstallmentsFlatHelper.createAddVariations();
        HashMap modifiedReschdule = transactionHelper.validateVariations(addVariationsjsondata, loanID);
        ArrayList newperiods = (ArrayList) modifiedReschdule.get("periods");
        ArrayList toVerifyData = VariableInstallmentsFlatHelper.constructVerifyData(
                new String[] { "20 October 2011", "31 October 2011", "20 November 2011", "20 December 2011", "20 January 2012" },
                new String[] { "21600.0", "6600.0", "26600.0", "26600.0", "26600.0" });
        assertAfterSubmit(newperiods, toVerifyData);
        transactionHelper.submitVariations(addVariationsjsondata, loanID);
        list = transactionHelper.retrieveSchedule(loanID);
        repaymentSchedule = (Map) list.get("repaymentSchedule");
        periods = (ArrayList) repaymentSchedule.get("periods");
        periods.remove(0); 
        assertAfterSubmit(periods, toVerifyData);
    }
    @Test
    public void testModifyInstallmentsWithInterestTypeisFlat() {
        VariableIntallmentsTransactionHelper transactionHelper = new VariableIntallmentsTransactionHelper(requestSpec, responseSpec);
        final String loanProductJson = VariableInstallmentsFlatHelper.createLoanProductWithVaribleConfig(false, NONE);
        Integer loanProductID = this.loanTransactionHelper.getLoanProductId(loanProductJson);
        final Integer clientID = ClientHelper.createClient(this.requestSpec, this.responseSpec);
        ClientHelper.verifyClientCreatedOnServer(this.requestSpec, this.responseSpec, clientID);
        List<HashMap> collaterals = new ArrayList<>();
        final Integer collateralId = CollateralManagementHelper.createCollateralProduct(this.requestSpec, this.responseSpec);
        final Integer clientCollateralId = CollateralManagementHelper.createClientCollateral(this.requestSpec, this.responseSpec,
                String.valueOf(clientID), collateralId);
        addCollaterals(collaterals, clientCollateralId, BigDecimal.valueOf(1));
        final String json = VariableInstallmentsFlatHelper.applyForLoanApplication(clientID, loanProductID, null, null, "1,00,000.00",
                collaterals);
        final Integer loanID = this.loanTransactionHelper.getLoanId(json);
        HashMap loanStatusHashMap = LoanStatusChecker.getStatusOfLoan(this.requestSpec, this.responseSpec, loanID);
        LoanStatusChecker.verifyLoanIsPending(loanStatusHashMap);
        Map list = transactionHelper.retrieveSchedule(loanID);
        Map repaymentSchedule = (Map) list.get("repaymentSchedule");
        ArrayList periods = (ArrayList) repaymentSchedule.get("periods");
        String addVariationsjsondata = VariableInstallmentsFlatHelper.createModifiyVariations((Map) periods.get(1)); 
        HashMap modifiedReschdule = transactionHelper.validateVariations(addVariationsjsondata, loanID);
        ArrayList newperiods = (ArrayList) modifiedReschdule.get("periods");
        ArrayList toVerifyData = VariableInstallmentsFlatHelper.constructVerifyData(
                new String[] { "20 October 2011", "20 November 2011", "20 December 2011", "20 January 2012" },
                new String[] { "32000.0", "25333.33", "25333.33", "25333.34" });
        assertAfterSubmit(newperiods, toVerifyData);
        transactionHelper.submitVariations(addVariationsjsondata, loanID);
        list = transactionHelper.retrieveSchedule(loanID);
        repaymentSchedule = (Map) list.get("repaymentSchedule");
        periods = (ArrayList) repaymentSchedule.get("periods");
        periods.remove(0); 
        assertAfterSubmit(periods, toVerifyData);
    }
    @Test
    public void testAllVariationsWithInterestTypeFlat() {
        VariableIntallmentsTransactionHelper transactionHelper = new VariableIntallmentsTransactionHelper(requestSpec, responseSpec);
        final String loanProductJson = VariableInstallmentsFlatHelper.createLoanProductWithVaribleConfig(false, NONE);
        Integer loanProductID = this.loanTransactionHelper.getLoanProductId(loanProductJson);
        final Integer clientID = ClientHelper.createClient(this.requestSpec, this.responseSpec);
        ClientHelper.verifyClientCreatedOnServer(this.requestSpec, this.responseSpec, clientID);
        List<HashMap> collaterals = new ArrayList<>();
        final Integer collateralId = CollateralManagementHelper.createCollateralProduct(this.requestSpec, this.responseSpec);
        Assertions.assertNotNull(collateralId);
        final Integer clientCollateralId = CollateralManagementHelper.createClientCollateral(this.requestSpec, this.responseSpec,
                String.valueOf(clientID), collateralId);
        Assertions.assertNotNull(clientCollateralId);
        addCollaterals(collaterals, clientCollateralId, BigDecimal.valueOf(1));
        final String json = VariableInstallmentsFlatHelper.applyForLoanApplication(clientID, loanProductID, null, null, "1,00,000.00",
                collaterals);
        final Integer loanID = this.loanTransactionHelper.getLoanId(json);
        HashMap loanStatusHashMap = LoanStatusChecker.getStatusOfLoan(this.requestSpec, this.responseSpec, loanID);
        LoanStatusChecker.verifyLoanIsPending(loanStatusHashMap);
        Map list = transactionHelper.retrieveSchedule(loanID);
        Map repaymentSchedule = (Map) list.get("repaymentSchedule");
        ArrayList periods = (ArrayList) repaymentSchedule.get("periods");
        String addVariationsjsondata = VariableInstallmentsFlatHelper.createAllVariations(); 
        HashMap modifiedReschdule = transactionHelper.validateVariations(addVariationsjsondata, loanID);
        ArrayList newperiods = (ArrayList) modifiedReschdule.get("periods");
        ArrayList toVerifyData = VariableInstallmentsFlatHelper.constructVerifyData(
                new String[] { "20 October 2011", "20 November 2011", "25 December 2011", "20 January 2012" },
                new String[] { "27000.0", "32000.0", "7000.0", "42000.0" });
        assertAfterSubmit(newperiods, toVerifyData);
        transactionHelper.submitVariations(addVariationsjsondata, loanID);
        list = transactionHelper.retrieveSchedule(loanID);
        repaymentSchedule = (Map) list.get("repaymentSchedule");
        periods = (ArrayList) repaymentSchedule.get("periods");
        periods.remove(0); 
        assertAfterSubmit(periods, toVerifyData);
    }
    @Test
    public void testModifyDatesWithInterestTypeFlat() {
        VariableIntallmentsTransactionHelper transactionHelper = new VariableIntallmentsTransactionHelper(requestSpec, responseSpec);
        final String loanProductJson = VariableInstallmentsFlatHelper.createLoanProductWithVaribleConfig(false, NONE);
        Integer loanProductID = this.loanTransactionHelper.getLoanProductId(loanProductJson);
        final Integer clientID = ClientHelper.createClient(this.requestSpec, this.responseSpec);
        ClientHelper.verifyClientCreatedOnServer(this.requestSpec, this.responseSpec, clientID);
        List<HashMap> collaterals = new ArrayList<>();
        final Integer collateralId = CollateralManagementHelper.createCollateralProduct(this.requestSpec, this.responseSpec);
        Assertions.assertNotNull(collateralId);
        final Integer clientCollateralId = CollateralManagementHelper.createClientCollateral(this.requestSpec, this.responseSpec,
                String.valueOf(clientID), collateralId);
        Assertions.assertNotNull(clientCollateralId);
        addCollaterals(collaterals, clientCollateralId, BigDecimal.valueOf(1));
        final String json = VariableInstallmentsFlatHelper.applyForLoanApplication(clientID, loanProductID, null, null, "1,00,000.00",
                collaterals);
        final Integer loanID = this.loanTransactionHelper.getLoanId(json);
        HashMap loanStatusHashMap = LoanStatusChecker.getStatusOfLoan(this.requestSpec, this.responseSpec, loanID);
        LoanStatusChecker.verifyLoanIsPending(loanStatusHashMap);
        Map list = transactionHelper.retrieveSchedule(loanID);
        Map repaymentSchedule = (Map) list.get("repaymentSchedule");
        ArrayList periods = (ArrayList) repaymentSchedule.get("periods");
        String addVariationsjsondata = VariableInstallmentsFlatHelper.createModifiyDateVariations(
                new String[] { "20 December 2011", "20 January 2012" }, new String[] { "04 January 2012", "08 February 2012" },
                new String[] { "20000" }); 
        HashMap modifiedReschdule = transactionHelper.validateVariations(addVariationsjsondata, loanID);
        ArrayList newperiods = (ArrayList) modifiedReschdule.get("periods");
        ArrayList toVerifyData = VariableInstallmentsFlatHelper.constructVerifyData(
                new String[] { "20 October 2011", "20 November 2011", "04 January 2012", "08 February 2012" },
                new String[] { "27306.45", "27306.45", "22306.45", "32306.46" });
        assertAfterSubmit(newperiods, toVerifyData);
        transactionHelper.submitVariations(addVariationsjsondata, loanID);
        list = transactionHelper.retrieveSchedule(loanID);
        repaymentSchedule = (Map) list.get("repaymentSchedule");
        periods = (ArrayList) repaymentSchedule.get("periods");
        periods.remove(0); 
        assertAfterSubmit(periods, toVerifyData);
    }
}
