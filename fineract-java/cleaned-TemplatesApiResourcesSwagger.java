
package org.apache.fineract.template.api;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import org.apache.fineract.template.domain.TemplateMapper;
final class TemplatesApiResourcesSwagger {
    private TemplatesApiResourcesSwagger() {
    }
    @Schema(description = "GetTemplatesResponse")
    public static final class GetTemplatesResponse {
        private GetTemplatesResponse() {
        }
        @Schema(example = "1")
        public Long id;
        @Schema(example = "Text")
        public String name;
        @Schema(example = "1")
        public Long entity;
        @Schema(example = "0")
        public Long type;
        @Schema(example = "This is a loan for {{loan.clientName}}")
        public String text;
        public List<TemplateMapper> mappers;
    }
    @Schema(description = "GetTemplatesTemplateResponse")
    public static final class GetTemplatesTemplateResponse {
        private GetTemplatesTemplateResponse() {
        }
        @Schema(example = "1")
        public Long id;
        @Schema(example = "Text")
        public String name;
        @Schema(example = "1")
        public Long entity;
        @Schema(example = "0")
        public Long type;
        @Schema(example = "This is a loan for {{loan.clientName}}")
        public String text;
        public List<TemplateMapper> mappers;
    }
    @Schema(description = "PostTemplatesRequest")
    public static final class PostTemplatesRequest {
        private PostTemplatesRequest() {
        }
        @Schema(example = "1")
        public Long id;
        @Schema(example = "Text")
        public String name;
        @Schema(example = "1")
        public Long entity;
        @Schema(example = "0")
        public Long type;
        @Schema(example = "This is a loan for {{loan.clientName}}")
        public String text;
        public List<TemplateMapper> mappers;
    }
    @Schema(description = "PostTemplatesResponse")
    public static final class PostTemplatesResponse {
        private PostTemplatesResponse() {
        }
        @Schema(example = "1")
        public Long resourceId;
    }
    @Schema(description = "GetTemplatesTemplateIdResponse")
    public static final class GetTemplatesTemplateIdResponse {
        private GetTemplatesTemplateIdResponse() {
        }
        @Schema(example = "1")
        public Long id;
        @Schema(example = "Text")
        public String name;
        @Schema(example = "1")
        public Long entity;
        @Schema(example = "0")
        public Long type;
        @Schema(example = "This is a loan for {{loan.clientName}}")
        public String text;
        public List<TemplateMapper> mappers;
    }
    @Schema(description = "PutTemplatesTemplateIdRequest")
    public static final class PutTemplatesTemplateIdRequest {
        private PutTemplatesTemplateIdRequest() {
        }
        @Schema(example = "1")
        public Long id;
        @Schema(example = "Text")
        public String name;
        @Schema(example = "1")
        public Long entity;
        @Schema(example = "0")
        public Long type;
        @Schema(example = "This is a loan for {{loan.clientName}}")
        public String text;
        public List<TemplateMapper> mappers;
    }
    @Schema(description = "PutTemplatesTemplateIdResponse")
    public static final class PutTemplatesTemplateIdResponse {
        private PutTemplatesTemplateIdResponse() {
        }
        @Schema(example = "1")
        public Long resourceId;
    }
    @Schema(description = "DeleteTemplatesTemplateIdResponse")
    public static final class DeleteTemplatesTemplateIdResponse {
        private DeleteTemplatesTemplateIdResponse() {
        }
        @Schema(example = "1")
        public Long resourceId;
    }
}
