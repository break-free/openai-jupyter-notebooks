
package org.apache.fineract.portfolio.client.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
interface ClientRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {
    String FIND_CLIENT_BY_ACCOUNT_NUMBER = "select client from Client client where client.accountNumber = :accountNumber";
    @Query(FIND_CLIENT_BY_ACCOUNT_NUMBER)
    Client getClientByAccountNumber(@Param("accountNumber") String accountNumber);
}
