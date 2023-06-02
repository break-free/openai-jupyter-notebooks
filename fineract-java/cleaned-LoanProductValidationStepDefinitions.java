
package org.apache.fineract.portfolio.loanproduct;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import io.cucumber.java8.En;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.portfolio.loanproduct.domain.LoanProduct;
import org.apache.fineract.portfolio.loanproduct.domain.LoanProductTrancheDetails;
import org.apache.fineract.portfolio.loanproduct.exception.LoanProductGeneralRuleException;
public class LoanProductValidationStepDefinitions implements En {
    private LoanProduct loanProduct;
    private LoanProductGeneralRuleException ruleException;
    public LoanProductValidationStepDefinitions() {
        Given("/^A loan product with values `allowMultipleDisbursal` is (.*), `disallowExpectedDisbursements` is (.*), `allowApprovedDisbursedAmountsOverApplied` is (.*), `overAppliedCalculationType` is (.*) and `overAppliedNumber` is (.*)$/",
                (String allowMultipleDisbursal, String disallowExpectedDisbursements, String allowApprovedDisbursedAmountsOverApplied,
                        String overAppliedCalculationType, String overAppliedNumber) -> {
                    this.loanProduct = newLoanProduct(Boolean.parseBoolean(allowMultipleDisbursal));
                    if (!StringUtils.isBlank(disallowExpectedDisbursements)) {
                        loanProduct.setDisallowExpectedDisbursements(Boolean.parseBoolean(disallowExpectedDisbursements));
                    }
                    if (!StringUtils.isBlank(allowApprovedDisbursedAmountsOverApplied)) {
                        loanProduct.setAllowApprovedDisbursedAmountsOverApplied(
                                Boolean.parseBoolean(allowApprovedDisbursedAmountsOverApplied));
                    }
                    if (!StringUtils.isBlank(overAppliedCalculationType) && !"-".equals(overAppliedCalculationType)) {
                        loanProduct.setOverAppliedCalculationType(overAppliedCalculationType);
                    }
                    if (!StringUtils.isBlank(overAppliedNumber) && Integer.parseInt(overAppliedNumber) >= 0) {
                        loanProduct.setOverAppliedNumber(Integer.parseInt(overAppliedNumber));
                    }
                });
        When("/^The user validates the loan product before saving$/", () -> {
            this.ruleException = assertThrows(LoanProductGeneralRuleException.class, () -> loanProduct.validateLoanProductPreSave());
        });
        Then("/^An exception (.*) with message template (.*) should be thrown$/", (String exception, String messageTemplate) -> {
            if (!StringUtils.isBlank(exception)) {
                assertEquals(Class.forName(exception.trim()), this.ruleException.getClass());
            }
            if (!StringUtils.isBlank(messageTemplate)) {
                assertThat(messageTemplate.trim(), is(this.ruleException.getGlobalisationMessageCode()));
            }
        });
    }
    private LoanProduct newLoanProduct(boolean allowMultipleDisbursals) {
        LoanProduct lp = new LoanProduct();
        lp.setLoanProducTrancheDetails(new LoanProductTrancheDetails(allowMultipleDisbursals, null, null));
        return lp;
    }
}
