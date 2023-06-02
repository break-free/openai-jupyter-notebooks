
package org.apache.fineract.portfolio.self.client.service;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.portfolio.client.exception.ClientNotFoundException;
import org.apache.fineract.useradministration.domain.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
@Service
public class AppuserClientMapperReadServiceImpl implements AppuserClientMapperReadService {
    private final JdbcTemplate jdbcTemplate;
    private final PlatformSecurityContext context;
    @Autowired
    public AppuserClientMapperReadServiceImpl(final JdbcTemplate jdbcTemplate, final PlatformSecurityContext context) {
        this.jdbcTemplate = jdbcTemplate;
        this.context = context;
    }
    @Override
    public Boolean isClientMappedToUser(Long clientId, Long appUserId) {
        return this.jdbcTemplate.queryForObject(
                "select case when (count(*) > 0) then true else false end "
                        + " from m_selfservice_user_client_mapping where client_id = ? and appuser_id = ?",
                Boolean.class, clientId, appUserId);
    }
    @Override
    public void validateAppuserClientsMapping(final Long clientId) {
        AppUser user = this.context.authenticatedUser();
        if (clientId != null) {
            final boolean mappedClientId = isClientMappedToUser(clientId, user.getId());
            if (!mappedClientId) {
                throw new ClientNotFoundException(clientId);
            }
        } else {
            throw new ClientNotFoundException(clientId);
        }
    }
}
