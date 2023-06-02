
package org.apache.fineract.accounting.common;
import static org.assertj.core.api.Assertions.assertThat;
import io.cucumber.java8.En;
import java.util.List;
import org.apache.fineract.accounting.financialactivityaccount.data.FinancialActivityData;
public class AccountingFinanciaActivityStepDefinitions implements En {
    private List<FinancialActivityData> data;
    public AccountingFinanciaActivityStepDefinitions() {
        Given("All financial activities", () -> {
            this.data = AccountingConstants.FinancialActivity.getAllFinancialActivities();
        });
        Then("The they should not be empty", () -> {
            assertThat(data).isNotEmpty();
        });
    }
}
