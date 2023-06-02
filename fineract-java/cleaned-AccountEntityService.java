
package org.apache.fineract.portfolio.self.pockets.service;
public interface AccountEntityService {
    String getKey();
    void validateSelfUserAccountMapping(Long accountId);
    String retrieveAccountNumberByAccountId(Long accountId);
}
