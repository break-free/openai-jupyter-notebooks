
package org.apache.fineract.batch.api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.batch.domain.BatchRequest;
import org.apache.fineract.batch.domain.BatchResponse;
import org.apache.fineract.batch.serialization.BatchRequestJsonHelper;
import org.apache.fineract.batch.service.BatchApiService;
import org.apache.fineract.infrastructure.core.serialization.ToApiJsonSerializer;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Path("/batches")
@Component
@Scope("singleton")
@Tag(name = "Batch API", description = "The Apache Fineract Batch API enables a consumer to access significant amounts of data in a single call or to make changes to several objects at once. Batching allows a consumer to pass instructions for several operations in a single HTTP request. A consumer can also specify dependencies between related operations. Once all operations have been completed, a consolidated response will be passed back and the HTTP connection will be closed.\n"
        + "\n"
        + "The Batch API takes in an array of logical HTTP requests represented as JSON arrays - each request has a requestId (the id of a request used to specify the sequence and as a dependency between requests), a method (corresponding to HTTP method GET/PUT/POST/DELETE etc.), a relativeUrl (the portion of the URL after https:
        + "\n"
        + "Batch API uses Json Path to handle dependent parameters. For example, if request '2' is referencing request '1' and in the \"body\" or in \"relativeUrl\" of request '2', there is a dependent parameter (which will look like \"$.parameter_name\"), then Batch API will internally substitute this dependent parameter from the response body of request '1'.\n"
        + "\n"
        + "Batch API is able to handle deeply nested dependent requests as well nested parameters. As shown in the example, requests are dependent on each other as, 1<--2<--6, i.e a nested dependency, where request '6' is not directly dependent on request '1' but still it is one of the nested child of request '1'. In the same way Batch API could handle a deeply nested dependent value, such as {..[..{..,$.parameter_name,..}..]}.")
@RequiredArgsConstructor
public class BatchApiResource {
    private final PlatformSecurityContext context;
    private final ToApiJsonSerializer<BatchResponse> toApiJsonSerializer;
    private final BatchApiService service;
    private final BatchRequestJsonHelper batchRequestJsonHelper;
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @Operation(summary = "Batch requests in a single transaction", description = "The Apache Fineract Batch API is also capable of executing all the requests in a single transaction, by setting a Query Parameter, \"enclosingTransaction=true\". So, if one or more of the requests in a batch returns an erroneous response all of the Data base transactions made by other successful requests will be rolled back.\n"
            + "\n"
            + "If there has been a rollback in a transaction then a single response will be provided, with a '400' status code and a body consisting of the error details of the first failed request.")
    @RequestBody(required = true, content = @Content(array = @ArraySchema(schema = @Schema(implementation = BatchRequest.class, description = "request body"))))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(array = @ArraySchema(schema = @Schema(implementation = BatchResponse.class)))) })
    public String handleBatchRequests(
            @DefaultValue("false") @QueryParam("enclosingTransaction") @Parameter(description = "enclosingTransaction", required = false) final boolean enclosingTransaction,
            @Parameter(hidden = true) final String jsonRequestString, @Context UriInfo uriInfo) {
        this.context.authenticatedUser();
        final List<BatchRequest> requestList = this.batchRequestJsonHelper.extractList(jsonRequestString);
        List<BatchResponse> result = new ArrayList<>();
        if (enclosingTransaction) {
            result = service.handleBatchRequestsWithEnclosingTransaction(requestList, uriInfo);
        } else {
            result = service.handleBatchRequestsWithoutEnclosingTransaction(requestList, uriInfo);
        }
        return this.toApiJsonSerializer.serialize(result);
    }
}
