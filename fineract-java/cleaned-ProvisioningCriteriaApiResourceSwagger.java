
package org.apache.fineract.organisation.provisioning.api;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collection;
import org.apache.fineract.organisation.provisioning.data.ProvisioningCriteriaDefinitionData;
import org.apache.fineract.portfolio.loanproduct.data.LoanProductData;
final class ProvisioningCriteriaApiResourceSwagger {
    private ProvisioningCriteriaApiResourceSwagger() {
    }
    @Schema(description = "PostProvisioningCriteriaRequest")
    public static final class PostProvisioningCriteriaRequest {
        private PostProvisioningCriteriaRequest() {
        }
        @Schema(example = "High Risk Products Criteria")
        public String criteriaName;
        public Collection<LoanProductData> loanProducts;
        public Collection<ProvisioningCriteriaDefinitionData> provisioningcriteria;
    }
    @Schema(description = "PostProvisioningCriteriaResponse")
    public static final class PostProvisioningCriteriaResponse {
        private PostProvisioningCriteriaResponse() {
        }
        @Schema(example = "1")
        public Long resourceId;
    }
    @Schema(description = "GetProvisioningCriteriaResponse")
    public static final class GetProvisioningCriteriaResponse {
        private GetProvisioningCriteriaResponse() {
        }
        @Schema(example = "1")
        public Long criteriaId;
        @Schema(example = "High Risk Products Criteria")
        public String criteriaName;
        @Schema(example = "mifos")
        public String createdBy;
    }
    @Schema(description = "GetProvisioningCriteriaCriteriaIdResponse")
    public static final class GetProvisioningCriteriaCriteriaIdResponse {
        private GetProvisioningCriteriaCriteriaIdResponse() {
        }
        @Schema(example = "1")
        public Long criteriaId;
        @Schema(example = "High Risk Products Criteria")
        public String criteriaName;
        @Schema(example = "mifos")
        public String createdBy;
        public Collection<LoanProductData> loanProducts;
        public Collection<ProvisioningCriteriaDefinitionData> provisioningcriteria;
    }
    @Schema(description = "PutProvisioningCriteriaRequest")
    public static final class PutProvisioningCriteriaRequest {
        private PutProvisioningCriteriaRequest() {
        }
        @Schema(example = "High Risk Products Criteria")
        public String criteriaName;
        public Collection<LoanProductData> loanProducts;
        public Collection<ProvisioningCriteriaDefinitionData> provisioningcriteria;
    }
    @Schema(description = "PutProvisioningCriteriaResponse")
    public static final class PutProvisioningCriteriaResponse {
        private PutProvisioningCriteriaResponse() {
        }
        static final class PutProvisioningCriteriaResponseChanges {
            private PutProvisioningCriteriaResponseChanges() {}
            @Schema(example = "High Risk Products Criteria")
            public String criteriaName;
        }
        @Schema(example = "1")
        public Long resourceId;
        public PutProvisioningCriteriaResponseChanges changes;
    }
    @Schema(description = "DeleteProvisioningCriteriaResponse")
    public static final class DeleteProvisioningCriteriaResponse {
        private DeleteProvisioningCriteriaResponse() {
        }
        @Schema(example = "1")
        public Long resourceId;
    }
}
