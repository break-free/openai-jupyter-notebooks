
package org.apache.fineract.organisation.monetary.api;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collection;
import org.apache.fineract.organisation.monetary.data.CurrencyData;
final class CurrenciesApiResourceSwagger {
    private CurrenciesApiResourceSwagger() {
    }
    @Schema(description = "GetCurrenciesResponse")
    public static final class GetCurrenciesResponse {
        private GetCurrenciesResponse() {
        }
        public Collection<CurrencyData> selectedCurrencyOptions;
        public Collection<CurrencyData> currencyOptions;
    }
    @Schema(description = "PutCurrenciesRequest")
    public static final class PutCurrenciesRequest {
        private PutCurrenciesRequest() {
        }
        @Schema(example = "[\"KES\",\n" + "        \"BND\",\n" + "        \"LBP\",\n" + "        \"GHC\",\n" + "        \"USD\",\n"
                + "        \"XOF\",\n" + "        \"AED\",\n" + "        \"AMD\"]")
        public String[] currencies;
    }
    @Schema(description = "PutCurrenciesResponse")
    public static final class PutCurrenciesResponse {
        private PutCurrenciesResponse() {
        }
        @Schema(example = "[\"KES\",\n" + "        \"BND\",\n" + "        \"LBP\",\n" + "        \"GHC\",\n" + "        \"USD\",\n"
                + "        \"XOF\",\n" + "        \"AED\",\n" + "        \"AMD\"]")
        public String[] currencies;
    }
}
