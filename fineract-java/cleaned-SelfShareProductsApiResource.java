
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
import org.apache.fineract.portfolio.accounts.constants.ShareAccountApiConstants;
import org.apache.fineract.portfolio.products.api.ProductsApiResource;
import org.apache.fineract.portfolio.self.client.service.AppuserClientMapperReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Path("/self/products/share")
@Component
@Scope("singleton")
@Tag(name = "Self Share Products", description = "")
public class SelfShareProductsApiResource {
    private final ProductsApiResource productsApiResource;
    private final AppuserClientMapperReadService appUserClientMapperReadService;
    @Autowired
    public SelfShareProductsApiResource(final ProductsApiResource productsApiResource,
            final AppuserClientMapperReadService appUserClientMapperReadService) {
        this.productsApiResource = productsApiResource;
        this.appUserClientMapperReadService = appUserClientMapperReadService;
    }
    @GET
    @Path("{productId}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String retrieveProduct(@QueryParam(ShareAccountApiConstants.clientid_paramname) final Long clientId,
            @PathParam("productId") final Long productId, @PathParam("type") final String productType, @Context final UriInfo uriInfo) {
        this.appUserClientMapperReadService.validateAppuserClientsMapping(clientId);
        return this.productsApiResource.retrieveProduct(productId, ShareAccountApiConstants.shareEntityType, uriInfo);
    }
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String retrieveAllProducts(@QueryParam(ShareAccountApiConstants.clientid_paramname) final Long clientId,
            @QueryParam("offset") final Integer offset, @QueryParam("limit") final Integer limit, @Context final UriInfo uriInfo) {
        this.appUserClientMapperReadService.validateAppuserClientsMapping(clientId);
        return this.productsApiResource.retrieveAllProducts(ShareAccountApiConstants.shareEntityType, offset, limit, uriInfo);
    }
}
