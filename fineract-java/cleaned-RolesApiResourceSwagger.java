
package org.apache.fineract.useradministration.api;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collection;
@SuppressWarnings({ "MemberName" })
final class RolesApiResourceSwagger {
    private RolesApiResourceSwagger() {
    }
    @Schema(description = "GetRolesResponse")
    public static final class GetRolesResponse {
        private GetRolesResponse() {
        }
        @Schema(example = "1")
        public Long id;
        @Schema(example = "Super Users")
        public String name;
        @Schema(example = "This role provides all application permissions.")
        public String description;
    }
    @Schema(description = "GetRolesRoleIdResponse")
    public static final class GetRolesRoleIdResponse {
        private GetRolesRoleIdResponse() {
        }
        @Schema(example = "1")
        public Long id;
        @Schema(example = "Super Users")
        public String name;
        @Schema(example = "This role provides all application permissions.")
        public String description;
    }
    @Schema(description = "PostRolesRequest")
    public static final class PostRolesRequest {
        private PostRolesRequest() {
        }
        @Schema(example = "Another Role Name")
        public String name;
        @Schema(example = "A description outlining the purpose of this role in relation to the application.")
        public String description;
    }
    @Schema(description = "PostRolesResponse")
    public static final class PostRolesResponse {
        private PostRolesResponse() {
        }
        @Schema(example = "1")
        public Long resourceId;
    }
    @Schema(description = "PutRolesRoleIdRequest")
    public static final class PutRolesRoleIdRequest {
        private PutRolesRoleIdRequest() {
        }
        @Schema(example = "some description(changed)")
        public String description;
    }
    @Schema(description = "PutRolesRoleIdResponse")
    public static final class PutRolesRoleIdResponse {
        private PutRolesRoleIdResponse() {
        }
        static final class PutRolesRoleIdResponseChanges {
            private PutRolesRoleIdResponseChanges() {}
            @Schema(example = "some description(changed)")
            public String description;
        }
        @Schema(example = "1")
        public Long resourceId;
        public PutRolesRoleIdResponseChanges changes;
    }
    @Schema(description = "PostRolesRoleIdResponse")
    public static final class PostRolesRoleIdResponse {
        private PostRolesRoleIdResponse() {
        }
        @Schema(example = "1")
        public Long resourceId;
    }
    @Schema(description = "GetRolesRoleIdPermissionsResponse")
    public static final class GetRolesRoleIdPermissionsResponse {
        private GetRolesRoleIdPermissionsResponse() {
        }
        static final class GetRolesRoleIdPermissionsResponsePermissionData {
            private GetRolesRoleIdPermissionsResponsePermissionData() {
            }
            @Schema(example = "authorisation")
            public String grouping;
            @Schema(example = "READ_PERMISSION")
            public String code;
            @Schema(example = "PERMISSION")
            public String entityName;
            @Schema(example = "READ")
            public String actionName;
            @Schema(example = "false")
            public Boolean selected;
        }
        @Schema(example = "1")
        public Long id;
        @Schema(example = "Super Users")
        public String name;
        @Schema(example = "This role provides all application permissions.")
        public String description;
        public Collection<GetRolesRoleIdPermissionsResponsePermissionData> permissionUsageData;
    }
    @Schema(description = "PutRolesRoleIdPermissionsRequest")
    public static final class PutRolesRoleIdPermissionsRequest {
        private PutRolesRoleIdPermissionsRequest() {
        }
        static final class PostRolesRoleIdPermissionsResponsePermissions {
            private PostRolesRoleIdPermissionsResponsePermissions() {
            }
            @Schema(example = "true")
            public String ALL_FUNCTIONS_READ;
        }
        public PutRolesRoleIdPermissionsRequest.PostRolesRoleIdPermissionsResponsePermissions permissions;
    }
    @Schema(description = "PutRolesRoleIdPermissionsResponse")
    public static final class PutRolesRoleIdPermissionsResponse {
        private PutRolesRoleIdPermissionsResponse() {
        }
        static final class PostRolesRoleIdPermissionsResponsePermissions {
            private PostRolesRoleIdPermissionsResponsePermissions() {
            }
            @Schema(example = "true")
            public String ALL_FUNCTIONS_READ;
        }
        @Schema(example = "8")
        public Long resourceId;
        public PutRolesRoleIdPermissionsRequest.PostRolesRoleIdPermissionsResponsePermissions permissions;
    }
    @Schema(description = "DeleteRolesRoleIdResponse")
    public static final class DeleteRolesRoleIdResponse {
        private DeleteRolesRoleIdResponse() {
        }
        @Schema(example = "1")
        public Long resourceId;
    }
}
