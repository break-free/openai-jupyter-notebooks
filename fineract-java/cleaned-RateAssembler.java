
package org.apache.fineract.portfolio.rate.service;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.apache.fineract.portfolio.loanproduct.LoanProductConstants;
import org.apache.fineract.portfolio.rate.domain.Rate;
import org.apache.fineract.portfolio.rate.domain.RateRepositoryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class RateAssembler {
    private final FromJsonHelper fromApiJsonHelper;
    private final RateRepositoryWrapper rateRepository;
    @Autowired
    public RateAssembler(final FromJsonHelper fromApiJsonHelper, final RateRepositoryWrapper rateRepository) {
        this.fromApiJsonHelper = fromApiJsonHelper;
        this.rateRepository = rateRepository;
    }
    public List<Rate> fromParsedJson(final JsonElement element) {
        final List<Rate> rateItems = new ArrayList<>();
        if (element.isJsonObject()) {
            final JsonObject topLevelJsonElement = element.getAsJsonObject();
            final Locale locale = this.fromApiJsonHelper.extractLocaleParameter(topLevelJsonElement);
            if (topLevelJsonElement.has(LoanProductConstants.RATES_PARAM_NAME)
                    && topLevelJsonElement.get(LoanProductConstants.RATES_PARAM_NAME).isJsonArray()) {
                final JsonArray array = topLevelJsonElement.get(LoanProductConstants.RATES_PARAM_NAME).getAsJsonArray();
                List<Long> idList = new ArrayList<>();
                for (int i = 0; i < array.size(); i++) {
                    final JsonObject rateElement = array.get(i).getAsJsonObject();
                    final Long id = this.fromApiJsonHelper.extractLongNamed("id", rateElement);
                    if (id != null) {
                        final Long rateId = this.fromApiJsonHelper.extractLongNamed("id", rateElement);
                        idList.add(rateId);
                    }
                }
                rateItems.addAll(rateRepository.findMultipleWithNotFoundDetection(idList));
            }
        }
        return rateItems;
    }
}
