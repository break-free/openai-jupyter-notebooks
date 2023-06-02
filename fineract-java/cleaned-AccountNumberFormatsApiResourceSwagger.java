
package org.apache.fineract.infrastructure.accountnumberformat.api;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Map;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
final class AccountNumberFormatsApiResourceSwagger {
    private AccountNumberFormatsApiResourceSwagger() {
    }
    @Schema(description = "GetAccountNumberFormatsResponse")
    public static final class GetAccountNumberFormatsResponse {
        private GetAccountNumberFormatsResponse() {
        }
    }
    @Schema(description = "GetAccountNumberFormatsIdResponse")
    public static final class GetAccountNumberFormatsIdResponse {
        private GetAccountNumberFormatsIdResponse() {
        }
        @Schema(example = "2")
        public Long id;
        public EnumOptionData accountType;
        public EnumOptionData prefixType;
    }
    @Schema(description = "GetAccountNumberFormatsResponseTemplate")
    public static final class GetAccountNumberFormatsResponseTemplate {
        private GetAccountNumberFormatsResponseTemplate() {
        }
        public List<EnumOptionData> accountTypeOptions;
        public Map<String, List<EnumOptionData>> prefixTypeOptions;
    }
    @Schema(description = "PostAccountNumberFormatsRequest")
    public static final class PostAccountNumberFormatsRequest {
        private PostAccountNumberFormatsRequest() {
        }
        @Schema(example = "1")
        public Long accountType;
        @Schema(example = "101")
        public Long prefixType;
    }
    @Schema(description = "PostAccountNumberFormatsResponse")
    public static final class PostAccountNumberFormatsResponse {
        private PostAccountNumberFormatsResponse() {
        }
        @Schema(example = "4")
        public Long resourceId;
    }
    @Schema(description = "PutAccountNumberFormatsRequest")
    public static final class PutAccountNumberFormatsRequest {
        private PutAccountNumberFormatsRequest() {
        }
        @Schema(example = "1")
        public Long prefixType;
    }
    @Schema(description = "PutAccountNumberFormatsResponse")
    public static final class PutAccountNumberFormatsResponse {
        private PutAccountNumberFormatsResponse() {
        }
        public static final class PutAccountNumberFormatschangesSwagger {
            private PutAccountNumberFormatschangesSwagger() {
            }
            @Schema(example = "OFFICE_NAME")
            public Long prefixType;
        }
        @Schema(example = "2")
        public Long resourceId;
        public PutAccountNumberFormatschangesSwagger changes;
    }
    @Schema(description = "DeleteAccountNumberFormatsResponse")
    public static final class DeleteAccountNumberFormatsResponse {
        private DeleteAccountNumberFormatsResponse() {
        }
        @Schema(example = "2")
        public Long resourceId;
    }
}
