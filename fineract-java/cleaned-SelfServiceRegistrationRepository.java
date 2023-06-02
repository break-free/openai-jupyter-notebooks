
package org.apache.fineract.portfolio.self.registration.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface SelfServiceRegistrationRepository
        extends JpaRepository<SelfServiceRegistration, Long>, JpaSpecificationExecutor<SelfServiceRegistration> {
    String FIND_BY_REQUEST_AND_AUTHENTICATION_TOKEN = "select request from SelfServiceRegistration request where request.id = :id and "
            + "request.authenticationToken = :authenticationToken";
    @Query(FIND_BY_REQUEST_AND_AUTHENTICATION_TOKEN)
    SelfServiceRegistration getRequestByIdAndAuthenticationToken(@Param("id") Long id,
            @Param("authenticationToken") String authenticationToken);
}
