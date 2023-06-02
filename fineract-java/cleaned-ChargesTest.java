
package org.apache.fineract.integrationtests;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.fineract.integrationtests.common.Utils;
import org.apache.fineract.integrationtests.common.charges.ChargesHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
@SuppressWarnings({ "rawtypes" })
public class ChargesTest {
    private ResponseSpecification responseSpec;
    private RequestSpecification requestSpec;
    @BeforeEach
    public void setup() {
        Utils.initializeRESTAssured();
        this.requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        this.requestSpec.header("Authorization", "Basic " + Utils.loginIntoServerAndGetBase64EncodedAuthenticationKey());
        this.responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
    }
    @Test
    public void testChargesForLoans() {
        ArrayList<HashMap> allChargesData = ChargesHelper.getCharges(this.requestSpec, this.responseSpec);
        Assertions.assertNotNull(allChargesData);
        final Integer disbursementChargeId = ChargesHelper.createCharges(this.requestSpec, this.responseSpec,
                ChargesHelper.getLoanDisbursementJSON());
        Assertions.assertNotNull(disbursementChargeId);
        HashMap changes = ChargesHelper.updateCharges(this.requestSpec, this.responseSpec, disbursementChargeId,
                ChargesHelper.getModifyChargeJSON());
        HashMap chargeDataAfterChanges = ChargesHelper.getChargeById(this.requestSpec, this.responseSpec, disbursementChargeId);
        Assertions.assertEquals(chargeDataAfterChanges.get("amount"), changes.get("amount"), "Verifying Charge after Modification");
        changes = ChargesHelper.updateCharges(this.requestSpec, this.responseSpec, disbursementChargeId,
                ChargesHelper.getModifyChargeAsPecentageAmountJSON());
        chargeDataAfterChanges = ChargesHelper.getChargeById(this.requestSpec, this.responseSpec, disbursementChargeId);
        HashMap chargeChangedData = (HashMap) chargeDataAfterChanges.get("chargePaymentMode");
        Assertions.assertEquals(chargeChangedData.get("id"), changes.get("chargePaymentMode"), "Verifying Charge after Modification");
        chargeChangedData = (HashMap) chargeDataAfterChanges.get("chargeCalculationType");
        Assertions.assertEquals(chargeChangedData.get("id"), changes.get("chargeCalculationType"), "Verifying Charge after Modification");
        changes = ChargesHelper.updateCharges(this.requestSpec, this.responseSpec, disbursementChargeId,
                ChargesHelper.getModifyChargeAsPecentageLoanAmountWithInterestJSON());
        chargeDataAfterChanges = ChargesHelper.getChargeById(this.requestSpec, this.responseSpec, disbursementChargeId);
        chargeChangedData = (HashMap) chargeDataAfterChanges.get("chargeCalculationType");
        Assertions.assertEquals(chargeChangedData.get("id"), changes.get("chargeCalculationType"), "Verifying Charge after Modification");
        changes = ChargesHelper.updateCharges(this.requestSpec, this.responseSpec, disbursementChargeId,
                ChargesHelper.getModifyChargeAsPercentageInterestJSON());
        chargeDataAfterChanges = ChargesHelper.getChargeById(this.requestSpec, this.responseSpec, disbursementChargeId);
        chargeChangedData = (HashMap) chargeDataAfterChanges.get("chargeCalculationType");
        Assertions.assertEquals(chargeChangedData.get("id"), changes.get("chargeCalculationType"), "Verifying Charge after Modification");
        Integer chargeIdAfterDeletion = ChargesHelper.deleteCharge(this.responseSpec, this.requestSpec, disbursementChargeId);
        Assertions.assertEquals(disbursementChargeId, chargeIdAfterDeletion, "Verifying Charge ID after deletion");
        final Integer specifiedDueDateChargeId = ChargesHelper.createCharges(this.requestSpec, this.responseSpec,
                ChargesHelper.getLoanSpecifiedDueDateJSON());
        Assertions.assertNotNull(specifiedDueDateChargeId);
        changes = ChargesHelper.updateCharges(this.requestSpec, this.responseSpec, specifiedDueDateChargeId,
                ChargesHelper.getModifyChargeJSON());
        chargeDataAfterChanges = ChargesHelper.getChargeById(this.requestSpec, this.responseSpec, specifiedDueDateChargeId);
        Assertions.assertEquals(chargeDataAfterChanges.get("amount"), changes.get("amount"), "Verifying Charge after Modification");
        changes = ChargesHelper.updateCharges(this.requestSpec, this.responseSpec, specifiedDueDateChargeId,
                ChargesHelper.getModifyChargeAsPecentageAmountJSON());
        chargeDataAfterChanges = ChargesHelper.getChargeById(this.requestSpec, this.responseSpec, specifiedDueDateChargeId);
        chargeChangedData = (HashMap) chargeDataAfterChanges.get("chargePaymentMode");
        Assertions.assertEquals(chargeChangedData.get("id"), changes.get("chargePaymentMode"), "Verifying Charge after Modification");
        chargeChangedData = (HashMap) chargeDataAfterChanges.get("chargeCalculationType");
        Assertions.assertEquals(chargeChangedData.get("id"), changes.get("chargeCalculationType"), "Verifying Charge after Modification");
        changes = ChargesHelper.updateCharges(this.requestSpec, this.responseSpec, specifiedDueDateChargeId,
                ChargesHelper.getModifyChargeAsPecentageLoanAmountWithInterestJSON());
        chargeDataAfterChanges = ChargesHelper.getChargeById(this.requestSpec, this.responseSpec, specifiedDueDateChargeId);
        chargeChangedData = (HashMap) chargeDataAfterChanges.get("chargeCalculationType");
        Assertions.assertEquals(chargeChangedData.get("id"), changes.get("chargeCalculationType"), "Verifying Charge after Modification");
        changes = ChargesHelper.updateCharges(this.requestSpec, this.responseSpec, specifiedDueDateChargeId,
                ChargesHelper.getModifyChargeAsPercentageInterestJSON());
        chargeDataAfterChanges = ChargesHelper.getChargeById(this.requestSpec, this.responseSpec, specifiedDueDateChargeId);
        chargeChangedData = (HashMap) chargeDataAfterChanges.get("chargeCalculationType");
        Assertions.assertEquals(chargeChangedData.get("id"), changes.get("chargeCalculationType"), "Verifying Charge after Modification");
        chargeIdAfterDeletion = ChargesHelper.deleteCharge(this.responseSpec, this.requestSpec, specifiedDueDateChargeId);
        Assertions.assertEquals(specifiedDueDateChargeId, chargeIdAfterDeletion, "Verifying Charge ID after deletion");
        final Integer installmentFeeChargeId = ChargesHelper.createCharges(this.requestSpec, this.responseSpec,
                ChargesHelper.getLoanInstallmentFeeJSON());
        changes = ChargesHelper.updateCharges(this.requestSpec, this.responseSpec, installmentFeeChargeId,
                ChargesHelper.getModifyChargeJSON());
        chargeDataAfterChanges = ChargesHelper.getChargeById(this.requestSpec, this.responseSpec, installmentFeeChargeId);
        Assertions.assertEquals(chargeDataAfterChanges.get("amount"), changes.get("amount"), "Verifying Charge after Modification");
        changes = ChargesHelper.updateCharges(this.requestSpec, this.responseSpec, installmentFeeChargeId,
                ChargesHelper.getModifyChargeAsPecentageAmountJSON());
        chargeDataAfterChanges = ChargesHelper.getChargeById(this.requestSpec, this.responseSpec, installmentFeeChargeId);
        chargeChangedData = (HashMap) chargeDataAfterChanges.get("chargePaymentMode");
        Assertions.assertEquals(chargeChangedData.get("id"), changes.get("chargePaymentMode"), "Verifying Charge after Modification");
        chargeChangedData = (HashMap) chargeDataAfterChanges.get("chargeCalculationType");
        Assertions.assertEquals(chargeChangedData.get("id"), changes.get("chargeCalculationType"), "Verifying Charge after Modification");
        changes = ChargesHelper.updateCharges(this.requestSpec, this.responseSpec, installmentFeeChargeId,
                ChargesHelper.getModifyChargeAsPecentageLoanAmountWithInterestJSON());
        chargeDataAfterChanges = ChargesHelper.getChargeById(this.requestSpec, this.responseSpec, installmentFeeChargeId);
        chargeChangedData = (HashMap) chargeDataAfterChanges.get("chargeCalculationType");
        Assertions.assertEquals(chargeChangedData.get("id"), changes.get("chargeCalculationType"), "Verifying Charge after Modification");
        changes = ChargesHelper.updateCharges(this.requestSpec, this.responseSpec, installmentFeeChargeId,
                ChargesHelper.getModifyChargeAsPercentageInterestJSON());
        chargeDataAfterChanges = ChargesHelper.getChargeById(this.requestSpec, this.responseSpec, installmentFeeChargeId);
        chargeChangedData = (HashMap) chargeDataAfterChanges.get("chargeCalculationType");
        Assertions.assertEquals(chargeChangedData.get("id"), changes.get("chargeCalculationType"), "Verifying Charge after Modification");
        chargeIdAfterDeletion = ChargesHelper.deleteCharge(this.responseSpec, this.requestSpec, installmentFeeChargeId);
        Assertions.assertEquals(installmentFeeChargeId, chargeIdAfterDeletion, "Verifying Charge ID after deletion");
        final Integer overdueFeeChargeId = ChargesHelper.createCharges(this.requestSpec, this.responseSpec,
                ChargesHelper.getLoanOverdueFeeJSON());
        Assertions.assertNotNull(overdueFeeChargeId);
        changes = ChargesHelper.updateCharges(this.requestSpec, this.responseSpec, overdueFeeChargeId, ChargesHelper.getModifyChargeJSON());
        chargeDataAfterChanges = ChargesHelper.getChargeById(this.requestSpec, this.responseSpec, overdueFeeChargeId);
        Assertions.assertEquals(chargeDataAfterChanges.get("amount"), changes.get("amount"), "Verifying Charge after Modification");
        changes = ChargesHelper.updateCharges(this.requestSpec, this.responseSpec, overdueFeeChargeId,
                ChargesHelper.getModifyChargeAsPecentageAmountJSON());
        chargeDataAfterChanges = ChargesHelper.getChargeById(this.requestSpec, this.responseSpec, overdueFeeChargeId);
        chargeChangedData = (HashMap) chargeDataAfterChanges.get("chargePaymentMode");
        Assertions.assertEquals(chargeChangedData.get("id"), changes.get("chargePaymentMode"), "Verifying Charge after Modification");
        chargeChangedData = (HashMap) chargeDataAfterChanges.get("chargeCalculationType");
        Assertions.assertEquals(chargeChangedData.get("id"), changes.get("chargeCalculationType"), "Verifying Charge after Modification");
        changes = ChargesHelper.updateCharges(this.requestSpec, this.responseSpec, overdueFeeChargeId,
                ChargesHelper.getModifyChargeAsPecentageLoanAmountWithInterestJSON());
        chargeDataAfterChanges = ChargesHelper.getChargeById(this.requestSpec, this.responseSpec, overdueFeeChargeId);
        chargeChangedData = (HashMap) chargeDataAfterChanges.get("chargeCalculationType");
        Assertions.assertEquals(chargeChangedData.get("id"), changes.get("chargeCalculationType"), "Verifying Charge after Modification");
        changes = ChargesHelper.updateCharges(this.requestSpec, this.responseSpec, overdueFeeChargeId,
                ChargesHelper.getModifyChargeAsPercentageInterestJSON());
        chargeDataAfterChanges = ChargesHelper.getChargeById(this.requestSpec, this.responseSpec, overdueFeeChargeId);
        chargeChangedData = (HashMap) chargeDataAfterChanges.get("chargeCalculationType");
        Assertions.assertEquals(chargeChangedData.get("id"), changes.get("chargeCalculationType"), "Verifying Charge after Modification");
        changes = ChargesHelper.updateCharges(this.requestSpec, this.responseSpec, overdueFeeChargeId,
                ChargesHelper.getModifyChargeFeeFrequencyAsYearsJSON());
        chargeDataAfterChanges = ChargesHelper.getChargeById(this.requestSpec, this.responseSpec, overdueFeeChargeId);
        chargeChangedData = (HashMap) chargeDataAfterChanges.get("feeFrequency");
        Assertions.assertEquals(chargeChangedData.get("id"), changes.get("feeFrequency"), "Verifying Charge after Modification");
        chargeIdAfterDeletion = ChargesHelper.deleteCharge(this.responseSpec, this.requestSpec, overdueFeeChargeId);
        Assertions.assertEquals(overdueFeeChargeId, chargeIdAfterDeletion, "Verifying Charge ID after deletion");
    }
    @Test
    public void testChargesForSavings() {
        final Integer specifiedDueDateChargeId = ChargesHelper.createCharges(this.requestSpec, this.responseSpec,
                ChargesHelper.getSavingsSpecifiedDueDateJSON());
        Assertions.assertNotNull(specifiedDueDateChargeId);
        HashMap changes = ChargesHelper.updateCharges(this.requestSpec, this.responseSpec, specifiedDueDateChargeId,
                ChargesHelper.getModifyChargeJSON());
        HashMap chargeDataAfterChanges = ChargesHelper.getChargeById(this.requestSpec, this.responseSpec, specifiedDueDateChargeId);
        Assertions.assertEquals(chargeDataAfterChanges.get("amount"), changes.get("amount"), "Verifying Charge after Modification");
        Integer chargeIdAfterDeletion = ChargesHelper.deleteCharge(this.responseSpec, this.requestSpec, specifiedDueDateChargeId);
        Assertions.assertEquals(specifiedDueDateChargeId, chargeIdAfterDeletion, "Verifying Charge ID after deletion");
        final Integer savingsActivationChargeId = ChargesHelper.createCharges(this.requestSpec, this.responseSpec,
                ChargesHelper.getSavingsActivationFeeJSON());
        Assertions.assertNotNull(savingsActivationChargeId);
        changes = ChargesHelper.updateCharges(this.requestSpec, this.responseSpec, savingsActivationChargeId,
                ChargesHelper.getModifyChargeJSON());
        chargeDataAfterChanges = ChargesHelper.getChargeById(this.requestSpec, this.responseSpec, savingsActivationChargeId);
        Assertions.assertEquals(chargeDataAfterChanges.get("amount"), changes.get("amount"), "Verifying Charge after Modification");
        chargeIdAfterDeletion = ChargesHelper.deleteCharge(this.responseSpec, this.requestSpec, savingsActivationChargeId);
        Assertions.assertEquals(savingsActivationChargeId, chargeIdAfterDeletion, "Verifying Charge ID after deletion");
        final Integer withdrawalFeeChargeId = ChargesHelper.createCharges(this.requestSpec, this.responseSpec,
                ChargesHelper.getSavingsWithdrawalFeeJSON());
        Assertions.assertNotNull(withdrawalFeeChargeId);
        changes = ChargesHelper.updateCharges(this.requestSpec, this.responseSpec, withdrawalFeeChargeId,
                ChargesHelper.getModifyWithdrawalFeeSavingsChargeJSON());
        chargeDataAfterChanges = ChargesHelper.getChargeById(this.requestSpec, this.responseSpec, withdrawalFeeChargeId);
        HashMap chargeChangedData = (HashMap) chargeDataAfterChanges.get("chargeCalculationType");
        Assertions.assertEquals(chargeChangedData.get("id"), changes.get("chargeCalculationType"), "Verifying Charge after Modification");
        chargeIdAfterDeletion = ChargesHelper.deleteCharge(this.responseSpec, this.requestSpec, withdrawalFeeChargeId);
        Assertions.assertEquals(withdrawalFeeChargeId, chargeIdAfterDeletion, "Verifying Charge ID after deletion");
        final Integer annualFeeChargeId = ChargesHelper.createCharges(this.requestSpec, this.responseSpec,
                ChargesHelper.getSavingsAnnualFeeJSON());
        Assertions.assertNotNull(annualFeeChargeId);
        changes = ChargesHelper.updateCharges(this.requestSpec, this.responseSpec, annualFeeChargeId, ChargesHelper.getModifyChargeJSON());
        chargeDataAfterChanges = ChargesHelper.getChargeById(this.requestSpec, this.responseSpec, annualFeeChargeId);
        Assertions.assertEquals(chargeDataAfterChanges.get("amount"), changes.get("amount"), "Verifying Charge after Modification");
        chargeIdAfterDeletion = ChargesHelper.deleteCharge(this.responseSpec, this.requestSpec, annualFeeChargeId);
        Assertions.assertEquals(annualFeeChargeId, chargeIdAfterDeletion, "Verifying Charge ID after deletion");
        final Integer monthlyFeeChargeId = ChargesHelper.createCharges(this.requestSpec, this.responseSpec,
                ChargesHelper.getSavingsMonthlyFeeJSON());
        Assertions.assertNotNull(monthlyFeeChargeId);
        changes = ChargesHelper.updateCharges(this.requestSpec, this.responseSpec, monthlyFeeChargeId, ChargesHelper.getModifyChargeJSON());
        chargeDataAfterChanges = ChargesHelper.getChargeById(this.requestSpec, this.responseSpec, monthlyFeeChargeId);
        Assertions.assertEquals(chargeDataAfterChanges.get("amount"), changes.get("amount"), "Verifying Charge after Modification");
        chargeIdAfterDeletion = ChargesHelper.deleteCharge(this.responseSpec, this.requestSpec, monthlyFeeChargeId);
        Assertions.assertEquals(monthlyFeeChargeId, chargeIdAfterDeletion, "Verifying Charge ID after deletion");
        final Integer overdraftFeeChargeId = ChargesHelper.createCharges(this.requestSpec, this.responseSpec,
                ChargesHelper.getSavingsOverdraftFeeJSON());
        Assertions.assertNotNull(overdraftFeeChargeId);
        changes = ChargesHelper.updateCharges(this.requestSpec, this.responseSpec, overdraftFeeChargeId,
                ChargesHelper.getModifyChargeJSON());
        chargeDataAfterChanges = ChargesHelper.getChargeById(this.requestSpec, this.responseSpec, overdraftFeeChargeId);
        Assertions.assertEquals(chargeDataAfterChanges.get("amount"), changes.get("amount"), "Verifying Charge after Modification");
        chargeIdAfterDeletion = ChargesHelper.deleteCharge(this.responseSpec, this.requestSpec, overdraftFeeChargeId);
        Assertions.assertEquals(overdraftFeeChargeId, chargeIdAfterDeletion, "Verifying Charge ID after deletion");
    }
}
