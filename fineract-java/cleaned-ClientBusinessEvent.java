
package org.apache.fineract.portfolio.businessevent.domain.client;
import org.apache.fineract.portfolio.businessevent.domain.AbstractBusinessEvent;
import org.apache.fineract.portfolio.client.domain.Client;
public abstract class ClientBusinessEvent extends AbstractBusinessEvent<Client> {
    public ClientBusinessEvent(Client value) {
        super(value);
    }
}
