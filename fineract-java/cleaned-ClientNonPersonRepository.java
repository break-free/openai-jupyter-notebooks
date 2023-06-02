
package org.apache.fineract.portfolio.client.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface ClientNonPersonRepository extends JpaRepository<ClientNonPerson, Long>, JpaSpecificationExecutor<ClientNonPerson> {
    @Query("select clientNonPerson from ClientNonPerson clientNonPerson where clientNonPerson.client.id = :clientId")
    ClientNonPerson findByClientId(@Param("clientId") Long clientId);
}
