
package org.apache.fineract.portfolio.client.api;
import static org.apache.fineract.infrastructure.core.domain.AuditableFieldsConstants.CREATED_BY;
import static org.apache.fineract.infrastructure.core.domain.AuditableFieldsConstants.CREATED_DATE;
import static org.apache.fineract.infrastructure.core.domain.AuditableFieldsConstants.LAST_MODIFIED_BY;
import static org.apache.fineract.infrastructure.core.domain.AuditableFieldsConstants.LAST_MODIFIED_DATE;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.fineract.infrastructure.core.api.ApiRequestParameterHelper;
import org.apache.fineract.infrastructure.core.serialization.ApiRequestJsonSerializationSettings;
import org.apache.fineract.infrastructure.core.serialization.ToApiJsonSerializer;
import org.apache.fineract.portfolio.client.domain.Client;
import org.apache.fineract.portfolio.client.domain.ClientRepositoryWrapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
@Profile("test")
@Component
@Path("/internal/client")
@RequiredArgsConstructor
@Slf4j
public class InternalClientInformationApiResource implements InitializingBean {
    private final ClientRepositoryWrapper clientRepositoryWrapper;
    private final ToApiJsonSerializer<Map> toApiJsonSerializer;
    private final ApiRequestParameterHelper apiRequestParameterHelper;
    @Override
    public void afterPropertiesSet() {
        log.warn("------------------------------------------------------------");
        log.warn("                                                            ");
        log.warn("DO NOT USE THIS IN PRODUCTION!");
        log.warn("Internal client services mode is enabled");
        log.warn("DO NOT USE THIS IN PRODUCTION!");
        log.warn("                                                            ");
        log.warn("------------------------------------------------------------");
    }
    @GET
    @Path("{clientId}/audit")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String getClientAuditFields(@Context final UriInfo uriInfo, @PathParam("clientId") Long clientId) {
        log.warn("------------------------------------------------------------");
        log.warn("                                                            ");
        log.warn("Fetching client with {}", clientId);
        log.warn("                                                            ");
        log.warn("------------------------------------------------------------");
        final Client client = clientRepositoryWrapper.findOneWithNotFoundDetection(clientId);
        Map<String, Object> auditFields = new HashMap<>(
                Map.of(CREATED_BY, client.getCreatedBy().orElse(null), CREATED_DATE, client.getCreatedDate().orElse(null), LAST_MODIFIED_BY,
                        client.getLastModifiedBy().orElse(null), LAST_MODIFIED_DATE, client.getLastModifiedDate().orElse(null)));
        final ApiRequestJsonSerializationSettings settings = this.apiRequestParameterHelper.process(uriInfo.getQueryParameters());
        return this.toApiJsonSerializer.serialize(settings, auditFields);
    }
}
