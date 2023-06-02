
package org.apache.fineract.portfolio.businessevent.domain.client;
import org.apache.fineract.portfolio.client.domain.Client;
public class ClientCreateBusinessEvent extends ClientBusinessEvent {
    public ClientCreateBusinessEvent(Client value) {
        super(value);
    }
}
