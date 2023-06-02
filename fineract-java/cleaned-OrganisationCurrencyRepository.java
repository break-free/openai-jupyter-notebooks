
package org.apache.fineract.organisation.office.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface OrganisationCurrencyRepository
        extends JpaRepository<OrganisationCurrency, Long>, JpaSpecificationExecutor<OrganisationCurrency> {
    OrganisationCurrency findOneByCode(String currencyCode);
}
