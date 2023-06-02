
package org.apache.fineract.infrastructure.security.service;
import org.apache.fineract.infrastructure.security.domain.PlatformUser;
public interface PlatformPasswordEncoder {
    String encode(PlatformUser appUser);
}
