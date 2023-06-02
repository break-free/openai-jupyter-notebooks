
package org.apache.fineract.portfolio.repaymentwithpostdatedchecks.service;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepaymentScheduleInstallment;
import org.apache.fineract.portfolio.repaymentwithpostdatedchecks.domain.PostDatedChecks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class RepaymentWithPostDatedChecksAssembler {
    private final FromJsonHelper fromApiJsonHelper;
    @Autowired
    public RepaymentWithPostDatedChecksAssembler(final FromJsonHelper fromJsonHelper) {
        this.fromApiJsonHelper = fromJsonHelper;
    }
    public Set<PostDatedChecks> fromParsedJson(final String json, final Loan loan) {
        final Set<PostDatedChecks> postDatedChecks = new HashSet<>();
        final JsonElement jsonElement = this.fromApiJsonHelper.parse(json);
        final List<LoanRepaymentScheduleInstallment> loanRepaymentScheduleInstallments = loan.getRepaymentScheduleInstallments();
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        final Locale locale = this.fromApiJsonHelper.extractLocaleParameter(jsonObject);
        if (jsonObject.has("postDatedChecks") && jsonObject.get("postDatedChecks").isJsonArray()) {
            JsonArray postDatedCheckArray = jsonObject.get("postDatedChecks").getAsJsonArray();
            for (int i = 0; i < postDatedCheckArray.size(); i++) {
                final JsonObject postDatedCheck = postDatedCheckArray.get(i).getAsJsonObject();
                if (postDatedCheck == null) {
                    continue;
                }
                final String name = this.fromApiJsonHelper.extractStringNamed("name", postDatedCheck);
                final BigDecimal amount = this.fromApiJsonHelper.extractBigDecimalNamed("amount", postDatedCheck, locale);
                final Integer installmentId = this.fromApiJsonHelper.extractIntegerNamed("installmentId", postDatedCheck, locale);
                final List<LoanRepaymentScheduleInstallment> installmentList = loanRepaymentScheduleInstallments.stream()
                        .filter(repayment -> repayment.getInstallmentNumber().equals(installmentId)).collect(Collectors.toList());
                final Long accountNo = this.fromApiJsonHelper.extractLongNamed("accountNo", postDatedCheck);
                final Long checkNo = this.fromApiJsonHelper.extractLongNamed("checkNo", postDatedCheck);
                postDatedChecks.add(PostDatedChecks.instanceOf(accountNo, name, amount, installmentList.get(0), loan, checkNo));
            }
        }
        return postDatedChecks;
    }
}
