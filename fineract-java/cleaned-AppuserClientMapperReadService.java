
package org.apache.fineract.portfolio.self.client.service;
public interface AppuserClientMapperReadService {
    Boolean isClientMappedToUser(Long clientId, Long appUserId);
    void validateAppuserClientsMapping(Long clientId);
}
