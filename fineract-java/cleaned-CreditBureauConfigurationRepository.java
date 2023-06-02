
package org.apache.fineract.infrastructure.creditbureau.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface CreditBureauConfigurationRepository
        extends JpaRepository<CreditBureauConfiguration, Long>, JpaSpecificationExecutor<CreditBureauConfiguration> {
    @Query("SELECT creditBureauConfig from CreditBureauConfiguration creditBureauConfig where creditBureauConfig.organisationCreditbureau.id = :creditBureauID and creditBureauConfig.configurationKey = :configurationKey ")
    CreditBureauConfiguration getCreditBureauConfigData(@Param("creditBureauID") Integer creditBureauID,
            @Param("configurationKey") String parameterName);
    @Query("SELECT creditBureauConfig from CreditBureauConfiguration creditBureauConfig where creditBureauConfig.organisationCreditbureau.id = :creditBureauID")
    CreditBureauConfiguration getCreditBureauConfigurationData(@Param("creditBureauID") Integer creditBureauID);
    @Query("SELECT creditBureauConfig from CreditBureauConfiguration creditBureauConfig where creditBureauConfig.configurationKey = :configurationKey")
    CreditBureauConfiguration getCreditBureauConfigurationValueData(@Param("configurationKey") String parameterName);
}
