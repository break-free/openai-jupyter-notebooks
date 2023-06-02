
package org.apache.fineract.portfolio.shareproducts.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface ShareProductRepository extends JpaRepository<ShareProduct, Long>, JpaSpecificationExecutor<ShareProduct> {
}
