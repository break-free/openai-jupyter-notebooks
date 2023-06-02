
package org.apache.fineract.mix.api;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import org.apache.fineract.infrastructure.core.api.ApiRequestParameterHelper;
import org.apache.fineract.infrastructure.core.serialization.ApiRequestJsonSerializationSettings;
import org.apache.fineract.infrastructure.core.serialization.ToApiJsonSerializer;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.mix.data.MixTaxonomyData;
import org.apache.fineract.mix.service.MixTaxonomyReadPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Path("/mixtaxonomy")
@Component
@Scope("singleton")
@Tag(name = "Mix Taxonomy", description = "")
public class MixTaxonomyApiResource {
    private final Set<String> responseDataParameters = new HashSet<>(
            Arrays.asList("taxonomyId", "name", "namespace", "dimension", "description"));
    private final PlatformSecurityContext context;
    private final ToApiJsonSerializer<MixTaxonomyData> toApiJsonSerializer;
    private final MixTaxonomyReadPlatformService readTaxonomyService;
    private final ApiRequestParameterHelper apiRequestParameterHelper;
    @Autowired
    public MixTaxonomyApiResource(final PlatformSecurityContext context, final ToApiJsonSerializer<MixTaxonomyData> toApiJsonSerializer,
            final MixTaxonomyReadPlatformService readTaxonomyService, final ApiRequestParameterHelper apiRequestParameterHelper) {
        this.context = context;
        this.toApiJsonSerializer = toApiJsonSerializer;
        this.readTaxonomyService = readTaxonomyService;
        this.apiRequestParameterHelper = apiRequestParameterHelper;
    }
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String retrieveAll(@Context final UriInfo uriInfo) {
        this.context.authenticatedUser();
        final List<MixTaxonomyData> taxonomyDatas = this.readTaxonomyService.retrieveAll();
        final ApiRequestJsonSerializationSettings settings = this.apiRequestParameterHelper.process(uriInfo.getQueryParameters());
        return this.toApiJsonSerializer.serialize(settings, taxonomyDatas, this.responseDataParameters);
    }
}
