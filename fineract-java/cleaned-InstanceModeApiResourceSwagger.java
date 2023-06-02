
package org.apache.fineract.infrastructure.instancemode.api;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;
public class InstanceModeApiResourceSwagger {
    @ToString
    @Schema(description = "ChangeInstanceModeRequest")
    @Getter
    public static final class ChangeInstanceModeRequest {
        @Schema(required = true, example = "true")
        public boolean readEnabled;
        @Schema(required = true, example = "true")
        public boolean writeEnabled;
        @Schema(required = true, example = "true")
        public boolean batchWorkerEnabled;
        @Schema(required = true, example = "true")
        public boolean batchManagerEnabled;
    }
}
