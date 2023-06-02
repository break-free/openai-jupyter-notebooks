
package org.apache.fineract.portfolio.businessevent.domain.client;
import org.apache.fineract.portfolio.client.domain.Client;
public class ClientRejectBusinessEvent extends ClientBusinessEvent {
    public ClientRejectBusinessEvent(Client value) {
        super(value);
    }
}
