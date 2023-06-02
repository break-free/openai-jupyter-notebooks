
package org.apache.fineract.infrastructure.core.debug;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Path("/echo")
@Scope("singleton")
public class EchoHeadersResource {
    @GET
    @Consumes({ MediaType.WILDCARD })
    @Produces({ MediaType.TEXT_PLAIN })
    public String get(@Context HttpHeaders headers) {
        StringBuilder sb = new StringBuilder("Request Headers:\n");
        headers.getRequestHeaders().forEach((k, v) -> sb.append(k).append(" : ").append(v.get(0)).append("\n"));
        return sb.toString();
    }
}
