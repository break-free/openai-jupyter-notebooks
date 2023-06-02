
package org.apache.fineract.organisation.workingdays.api;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collection;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
final class WorkingDaysApiResourceSwagger {
    private WorkingDaysApiResourceSwagger() {
    }
    @Schema(description = "GetWorkingDaysResponse")
    public static final class GetWorkingDaysResponse {
        private GetWorkingDaysResponse() {
        }
        @Schema(example = "1")
        public Long id;
        @Schema(example = "FREQ=WEEKLY;INTERVAL=1;BYDAY=MO,TU,WE,TH,FR")
        public String recurrence;
        public EnumOptionData repaymentRescheduleType;
        @Schema(example = "true")
        public Boolean extendTermForDailyRepayments;
    }
    @Schema(description = "GetWorkingDaysTemplateResponse")
    public static final class GetWorkingDaysTemplateResponse {
        private GetWorkingDaysTemplateResponse() {
        }
        public Collection<EnumOptionData> repaymentRescheduleOptions;
    }
    @Schema(description = "PutWorkingDaysRequest")
    public static final class PutWorkingDaysRequest {
        private PutWorkingDaysRequest() {
        }
        @Schema(example = "FREQ=WEEKLY;INTERVAL=1;BYDAY=MO,TU,WE,TH,FR")
        public String recurrence;
        @Schema(example = "en")
        public String locale;
        @Schema(example = "4")
        public EnumOptionData repaymentRescheduleType;
        @Schema(example = "true")
        public Boolean extendTermForDailyRepayments;
    }
    @Schema(description = "PutWorkingDaysResponse")
    public static final class PutWorkingDaysResponse {
        private PutWorkingDaysResponse() {
        }
        @Schema(example = "1")
        public Long resourceId;
    }
}
