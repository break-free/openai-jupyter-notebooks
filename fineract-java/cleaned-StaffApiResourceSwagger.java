
package org.apache.fineract.organisation.staff.api;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
final class StaffApiResourceSwagger {
    private StaffApiResourceSwagger() {
    }
    @Schema(description = "PostStaffRequest")
    public static final class PostStaffRequest {
        private PostStaffRequest() {
        }
        @Schema(example = "1")
        public Long officeId;
        @Schema(example = "John")
        public String firstname;
        @Schema(example = "Doe")
        public String lastname;
        @Schema(example = "true")
        public Boolean isLoanOfficer;
        @Schema(example = "17H")
        public String externalId;
        @Schema(example = "+353851239876")
        public String mobileNo;
        @Schema(example = "true")
        public Boolean isActive;
        @Schema(example = "01 January 2009")
        public LocalDate joiningDate;
        @Schema(example = "en")
        public String locale;
        @Schema(example = "dd MMMM yyyy")
        public String dateFormat;
    }
    @Schema(description = "PostStaffResponse")
    public static final class CreateStaffResponse {
        private CreateStaffResponse() {
        }
        @Schema(example = "1")
        public Long officeId;
        @Schema(example = "1")
        public Long resourceId;
    }
    @Schema(description = "GetStaffResponse")
    public static final class RetrieveOneResponse {
        private RetrieveOneResponse() {
        }
        @Schema(example = "1")
        public Long id;
        @Schema(example = "John")
        public String firstname;
        @Schema(example = "Doe")
        public String lastname;
        @Schema(example = "Doe, John")
        public String displayName;
        @Schema(example = "1")
        public Long officeId;
        @Schema(example = "Head Office")
        public String officeName;
        @Schema(example = "true")
        public Boolean isLoanOfficer;
        @Schema(example = "17H")
        public String externalId;
        @Schema(example = "+353851239876")
        public Boolean isActive;
        @Schema(example = "[2009,8,1]")
        public LocalDate joiningDate;
    }
    @Schema(description = "PutStaffRequest")
    public static final class PutStaffRequest {
        private PutStaffRequest() {
        }
        @Schema(example = "false")
        public Boolean isLoanOfficer;
        @Schema(example = "17Hbb")
        public String externalId;
    }
    @Schema(description = "PutStaffResponse")
    public static final class UpdateStaffResponse {
        private UpdateStaffResponse() {
        }
        static final class PutStaffResponseChanges {
            private PutStaffResponseChanges() {}
            @Schema(example = "false")
            public Boolean isLoanOfficer;
            @Schema(example = "17Hbb")
            public String externalId;
        }
        @Schema(example = "1")
        public Long officeId;
        @Schema(example = "1")
        public Long resourceId;
    }
}
