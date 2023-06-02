
package org.apache.fineract.portfolio.self.savings.service;
public interface AppuserSavingsMapperReadService {
    Boolean isSavingsMappedToUser(Long savingsId, Long appUserId);
}
