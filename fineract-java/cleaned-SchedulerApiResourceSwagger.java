
package org.apache.fineract.infrastructure.jobs.api;
import io.swagger.v3.oas.annotations.media.Schema;
final class SchedulerApiResourceSwagger {
    private SchedulerApiResourceSwagger() {
    }
    @Schema(description = "GetSchedulerResponse")
    public static final class GetSchedulerResponse {
        private GetSchedulerResponse() {
        }
        @Schema(example = "true")
        public boolean active;
    }
}
