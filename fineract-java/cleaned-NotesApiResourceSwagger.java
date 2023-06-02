
package org.apache.fineract.portfolio.note.api;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.ZonedDateTime;
final class NotesApiResourceSwagger {
    private NotesApiResourceSwagger() {}
    @Schema(description = "GetResourceTypeResourceIdNotesResponse")
    public static final class GetResourceTypeResourceIdNotesResponse {
        private GetResourceTypeResourceIdNotesResponse() {}
        static final class GetNotesNoteType {
            private GetNotesNoteType() {}
            @Schema(example = "100")
            public Integer id;
            @Schema(example = "noteType.client")
            public String code;
            @Schema(example = "Client note")
            public String description;
        }
        @Schema(example = "2")
        public Integer id;
        @Schema(example = "1")
        public Integer clientId;
        public GetNotesNoteType noteType;
        @Schema(example = "First note edited")
        public String note;
        @Schema(example = "1")
        public Integer createdById;
        @Schema(example = "mifos")
        public String createdByUsername;
        @Schema(example = "1342498505000")
        public ZonedDateTime createdOn;
        @Schema(example = "1")
        public Integer updatedById;
        @Schema(example = "mifos")
        public String updatedByUsername;
        @Schema(example = "1342498517000")
        public ZonedDateTime updatedOn;
    }
    @Schema(description = "GetResourceTypeResourceIdNotesNoteIdResponse")
    public static final class GetResourceTypeResourceIdNotesNoteIdResponse {
        private GetResourceTypeResourceIdNotesNoteIdResponse() {}
        @Schema(example = "76")
        public Integer id;
        @Schema(example = "1")
        public Integer clientId;
        public GetResourceTypeResourceIdNotesResponse.GetNotesNoteType noteType;
        @Schema(example = "a note about the client")
        public String note;
        @Schema(example = "1")
        public Integer createdById;
        @Schema(example = "mifos")
        public String createdByUsername;
        @Schema(example = "1359463135000")
        public ZonedDateTime createdOn;
        @Schema(example = "1")
        public Integer updatedById;
        @Schema(example = "mifos")
        public String updatedByUsername;
        @Schema(example = "1359463135000")
        public ZonedDateTime updatedOn;
    }
    @Schema(description = "PostResourceTypeResourceIdNotesRequest")
    public static final class PostResourceTypeResourceIdNotesRequest {
        private PostResourceTypeResourceIdNotesRequest() {}
        @Schema(example = "a note about the client")
        public String note;
    }
    @Schema(description = "PostResourceTypeResourceIdNotesResponse")
    public static final class PostResourceTypeResourceIdNotesResponse {
        private PostResourceTypeResourceIdNotesResponse() {}
        @Schema(example = "1")
        public Integer officeId;
        @Schema(example = "1")
        public Integer clientId;
        @Schema(example = "76")
        public Integer resourceId;
    }
    @Schema(description = "PutResourceTypeResourceIdNotesNoteIdRequest")
    public static final class PutResourceTypeResourceIdNotesNoteIdRequest {
        private PutResourceTypeResourceIdNotesNoteIdRequest() {}
        @Schema(example = "a note about the client")
        public String note;
    }
    @Schema(description = "PutResourceTypeResourceIdNotesNoteIdResponse")
    public static final class PutResourceTypeResourceIdNotesNoteIdResponse {
        private PutResourceTypeResourceIdNotesNoteIdResponse() {}
        static final class PutNotesChanges {
            private PutNotesChanges() {}
            @Schema(example = "a note about the client")
            public String note;
        }
        @Schema(example = "1")
        public Integer officeId;
        @Schema(example = "1")
        public Integer clientId;
        @Schema(example = "76")
        public Integer resourceId;
        public PutNotesChanges changes;
    }
    @Schema(description = "DeleteResourceTypeResourceIdNotesNoteIdResponse")
    public static final class DeleteResourceTypeResourceIdNotesNoteIdResponse {
        private DeleteResourceTypeResourceIdNotesNoteIdResponse() {}
        @Schema(example = "76")
        public Integer resourceId;
    }
}
