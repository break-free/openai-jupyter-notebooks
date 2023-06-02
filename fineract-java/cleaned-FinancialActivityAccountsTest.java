
package org.apache.fineract.integrationtests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.HashMap;
import java.util.List;
import org.apache.fineract.accounting.common.AccountingConstants.FinancialActivity;
import org.apache.fineract.accounting.financialactivityaccount.exception.DuplicateFinancialActivityAccountFoundException;
import org.apache.fineract.accounting.financialactivityaccount.exception.FinancialActivityAccountInvalidException;
import org.apache.fineract.integrationtests.common.CommonConstants;
import org.apache.fineract.integrationtests.common.Utils;
import org.apache.fineract.integrationtests.common.accounting.Account;
import org.apache.fineract.integrationtests.common.accounting.AccountHelper;
import org.apache.fineract.integrationtests.common.accounting.FinancialActivityAccountHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
@SuppressWarnings("rawtypes")
public class FinancialActivityAccountsTest {
    private ResponseSpecification responseSpec;
    private ResponseSpecification responseSpecForValidationError;
    private ResponseSpecification responseSpecForDomainRuleViolation;
    private ResponseSpecification responseSpecForResourceNotFoundError;
    private RequestSpecification requestSpec;
    private AccountHelper accountHelper;
    private FinancialActivityAccountHelper financialActivityAccountHelper;
    private final Integer assetTransferFinancialActivityId = FinancialActivity.ASSET_TRANSFER.getValue();
    public static final Integer LIABILITY_TRANSFER_FINANCIAL_ACTIVITY_ID = FinancialActivity.LIABILITY_TRANSFER.getValue();
    @BeforeEach
    public void setup() {
        Utils.initializeRESTAssured();
        this.requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        this.requestSpec.header("Authorization", "Basic " + Utils.loginIntoServerAndGetBase64EncodedAuthenticationKey());
        this.responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
        this.responseSpecForValidationError = new ResponseSpecBuilder().expectStatusCode(400).build();
        this.responseSpecForDomainRuleViolation = new ResponseSpecBuilder().expectStatusCode(403).build();
        this.responseSpecForResourceNotFoundError = new ResponseSpecBuilder().expectStatusCode(404).build();
        this.accountHelper = new AccountHelper(this.requestSpec, this.responseSpec);
        this.financialActivityAccountHelper = new FinancialActivityAccountHelper(this.requestSpec);
    }
    @SuppressWarnings("unchecked")
    @Test
    public void testFinancialActivityAccounts() {
        Account liabilityTransferAccount = accountHelper.createLiabilityAccount();
        Account assetTransferAccount = accountHelper.createAssetAccount();
        Assertions.assertNotNull(assetTransferAccount);
        Assertions.assertNotNull(liabilityTransferAccount);
        Integer financialActivityAccountId = (Integer) financialActivityAccountHelper.createFinancialActivityAccount(
                LIABILITY_TRANSFER_FINANCIAL_ACTIVITY_ID, liabilityTransferAccount.getAccountID(), responseSpec,
                CommonConstants.RESPONSE_RESOURCE_ID);
        Assertions.assertNotNull(financialActivityAccountId);
        assertFinancialActivityAccountCreation(financialActivityAccountId, LIABILITY_TRANSFER_FINANCIAL_ACTIVITY_ID,
                liabilityTransferAccount);
        Account newLiabilityTransferAccount = accountHelper.createLiabilityAccount();
        Assertions.assertNotNull(newLiabilityTransferAccount);
        HashMap changes = (HashMap) financialActivityAccountHelper.updateFinancialActivityAccount(financialActivityAccountId,
                LIABILITY_TRANSFER_FINANCIAL_ACTIVITY_ID, newLiabilityTransferAccount.getAccountID(), responseSpec,
                CommonConstants.RESPONSE_CHANGES);
        Assertions.assertEquals(newLiabilityTransferAccount.getAccountID(), changes.get("glAccountId"));
        assertFinancialActivityAccountCreation(financialActivityAccountId, LIABILITY_TRANSFER_FINANCIAL_ACTIVITY_ID,
                newLiabilityTransferAccount);
        List<HashMap> invalidFinancialActivityUpdateError = (List<HashMap>) financialActivityAccountHelper.updateFinancialActivityAccount(
                financialActivityAccountId, 232, newLiabilityTransferAccount.getAccountID(), responseSpecForValidationError,
                CommonConstants.RESPONSE_ERROR);
        assertEquals("validation.msg.financialactivityaccount.financialActivityId.is.not.one.of.expected.enumerations",
                invalidFinancialActivityUpdateError.get(0).get(CommonConstants.RESPONSE_ERROR_MESSAGE_CODE));
        List<HashMap> duplicateFinancialActivityAccountError = (List<HashMap>) financialActivityAccountHelper
                .createFinancialActivityAccount(LIABILITY_TRANSFER_FINANCIAL_ACTIVITY_ID, liabilityTransferAccount.getAccountID(),
                        responseSpecForDomainRuleViolation, CommonConstants.RESPONSE_ERROR);
        assertEquals(DuplicateFinancialActivityAccountFoundException.getErrorcode(),
                duplicateFinancialActivityAccountError.get(0).get(CommonConstants.RESPONSE_ERROR_MESSAGE_CODE));
        List<HashMap> invalidFinancialActivityAccountError = (List<HashMap>) financialActivityAccountHelper.updateFinancialActivityAccount(
                financialActivityAccountId, assetTransferFinancialActivityId, newLiabilityTransferAccount.getAccountID(),
                responseSpecForDomainRuleViolation, CommonConstants.RESPONSE_ERROR);
        assertEquals(FinancialActivityAccountInvalidException.getErrorcode(),
                invalidFinancialActivityAccountError.get(0).get(CommonConstants.RESPONSE_ERROR_MESSAGE_CODE));
        Integer deletedFinancialActivityAccountId = financialActivityAccountHelper
                .deleteFinancialActivityAccount(financialActivityAccountId, responseSpec, CommonConstants.RESPONSE_RESOURCE_ID);
        Assertions.assertNotNull(deletedFinancialActivityAccountId);
        Assertions.assertEquals(financialActivityAccountId, deletedFinancialActivityAccountId);
        financialActivityAccountHelper.getFinancialActivityAccount(deletedFinancialActivityAccountId, responseSpecForResourceNotFoundError);
    }
    private void assertFinancialActivityAccountCreation(Integer financialActivityAccountId, Integer financialActivityId,
            Account glAccount) {
        HashMap mappingDetails = financialActivityAccountHelper.getFinancialActivityAccount(financialActivityAccountId, responseSpec);
        Assertions.assertEquals(financialActivityId, ((HashMap) mappingDetails.get("financialActivityData")).get("id"));
        Assertions.assertEquals(glAccount.getAccountID(), ((HashMap) mappingDetails.get("glAccountData")).get("id"));
    }
    @AfterEach
    public void tearDown() {
        List<HashMap> financialActivities = this.financialActivityAccountHelper.getAllFinancialActivityAccounts(this.responseSpec);
        for (HashMap financialActivity : financialActivities) {
            Integer financialActivityAccountId = (Integer) financialActivity.get("id");
            Integer deletedFinancialActivityAccountId = this.financialActivityAccountHelper
                    .deleteFinancialActivityAccount(financialActivityAccountId, this.responseSpec, CommonConstants.RESPONSE_RESOURCE_ID);
            Assertions.assertNotNull(deletedFinancialActivityAccountId);
            Assertions.assertEquals(financialActivityAccountId, deletedFinancialActivityAccountId);
        }
    }
}
