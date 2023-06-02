
package org.apache.fineract.portfolio.collateralmanagement.domain;
import java.util.List;
import org.apache.fineract.portfolio.client.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface ClientCollateralManagementRepository
        extends JpaRepository<ClientCollateralManagement, Long>, JpaSpecificationExecutor<ClientCollateralManagement> {
    @Query("select clientCollateral from ClientCollateralManagement clientCollateral where clientCollateral.client=:client")
    List<ClientCollateralManagement> findByClientId(@Param("client") Client client);
}
