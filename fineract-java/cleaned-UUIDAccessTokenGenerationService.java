
package org.apache.fineract.infrastructure.security.service;
import java.util.UUID;
import org.springframework.stereotype.Service;
@Service
public class UUIDAccessTokenGenerationService implements AccessTokenGenerationService {
    @Override
    public String generateRandomToken() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
