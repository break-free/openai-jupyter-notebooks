
package org.apache.fineract.portfolio.businessevent.domain.client;
import org.apache.fineract.portfolio.client.domain.Client;
public class ClientActivateBusinessEvent extends ClientBusinessEvent {
    public ClientActivateBusinessEvent(Client value) {
        super(value);
    }
}
