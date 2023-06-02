
package org.apache.fineract.portfolio.self.pockets.service;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Scope("singleton")
public class AccountEntityServiceFactory {
    private Map<String, AccountEntityService> accountEntityServiceHashMap = new HashMap<>();
    @Autowired
    public AccountEntityServiceFactory(final Set<AccountEntityService> accountEntityServices) {
        for (AccountEntityService service : accountEntityServices) {
            this.accountEntityServiceHashMap.put(service.getKey(), service);
        }
    }
    public AccountEntityService getAccountEntityService(final String key) {
        return this.accountEntityServiceHashMap.get(key);
    }
}
