
package org.apache.fineract.useradministration.domain;
public interface UserDomainService {
    void create(AppUser appUser, Boolean sendPasswordToEmail);
}
