
package org.apache.fineract.portfolio.self.products.api;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import org.apache.fineract.portfolio.savings.SavingsApiConstants;
import org.apache.fineract.portfolio.savings.api.SavingsProductsApiResource;
import org.apache.fineract.portfolio.self.client.service.AppuserClientMapperReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Path("/self/savingsproducts")
@Component
@Scope("singleton")
@Tag(name = "Self Savings Products", description = "")
public class SelfSavingsProductsApiResource {
    private final SavingsProductsApiResource savingsProductsApiResource;
    private final AppuserClientMapperReadService appUserClientMapperReadService;
    @Autowired
    public SelfSavingsProductsApiResource(final SavingsProductsApiResource savingsProductsApiResource,
            final AppuserClientMapperReadService appUserClientMapperReadService) {
        this.savingsProductsApiResource = savingsProductsApiResource;
        this.appUserClientMapperReadService = appUserClientMapperReadService;
    }
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String retrieveAll(@QueryParam(SavingsApiConstants.clientIdParamName) final Long clientId, @Context final UriInfo uriInfo) {
        this.appUserClientMapperReadService.validateAppuserClientsMapping(clientId);
        return this.savingsProductsApiResource.retrieveAll(uriInfo);
    }
    @GET
    @Path("{productId}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String retrieveOne(@PathParam(SavingsApiConstants.productIdParamName) final Long productId,
            @QueryParam(SavingsApiConstants.clientIdParamName) final Long clientId, @Context final UriInfo uriInfo) {
        this.appUserClientMapperReadService.validateAppuserClientsMapping(clientId);
        return this.savingsProductsApiResource.retrieveOne(productId, uriInfo);
    }
}
