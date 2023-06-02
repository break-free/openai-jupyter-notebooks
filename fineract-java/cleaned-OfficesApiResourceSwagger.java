
package org.apache.fineract.organisation.office.api;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.Collection;
final class OfficesApiResourceSwagger {
    private OfficesApiResourceSwagger() {
    }
    @Schema(description = "GetOfficesResponse")
    public static final class GetOfficesResponse {
        private GetOfficesResponse() {
        }
        @Schema(example = "1")
        public Long id;
        @Schema(example = "Head Office")
        public String name;
        @Schema(example = "Head Office")
        public String nameDecorated;
        @Schema(example = "1")
        public String externalId;
        @Schema(example = "[2009, 1, 1]")
        public LocalDate openingDate;
        @Schema(example = ".")
        public String hierarchy;
    }
    @Schema(description = "GetOfficesTemplateResponse")
    public static final class GetOfficesTemplateResponse {
        private GetOfficesTemplateResponse() {
        }
        @Schema(example = "[2009, 1, 1]")
        public LocalDate openingDate;
        public Collection<GetOfficesResponse> allowedParents;
    }
    @Schema(description = "PostOfficesRequest")
    public static final class PostOfficesRequest {
        private PostOfficesRequest() {
        }
        @Schema(example = "Good Friday")
        public String name;
        @Schema(example = "dd MMMM yyyy")
        public String dateFormat;
        @Schema(example = "en")
        public String locale;
        @Schema(example = "01 July 2007")
        public LocalDate openingDate;
        @Schema(example = "2")
        public Long parentId;
        @Schema(example = "SYS54-88")
        public String externalId;
    }
    @Schema(description = "PostOfficesResponse")
    public static final class PostOfficesResponse {
        private PostOfficesResponse() {
        }
        @Schema(example = "3")
        public Long officeId;
        @Schema(example = "3")
        public Long resourceId;
    }
    @Schema(description = "PutOfficesOfficeIdRequest")
    public static final class PutOfficesOfficeIdRequest {
        private PutOfficesOfficeIdRequest() {
        }
        @Schema(example = "Name is updated")
        public String name;
    }
    @Schema(description = "PutOfficesOfficeIdResponse")
    public static final class PutOfficesOfficeIdResponse {
        private PutOfficesOfficeIdResponse() {
        }
        static final class PutOfficesOfficeIdResponseChanges {
            private PutOfficesOfficeIdResponseChanges() {
            }
            @Schema(example = "Name is updated")
            public String name;
        }
        @Schema(example = "3")
        public Long officeId;
        @Schema(example = "3")
        public Long resourceId;
        public PutOfficesOfficeIdResponseChanges changes;
    }
}
