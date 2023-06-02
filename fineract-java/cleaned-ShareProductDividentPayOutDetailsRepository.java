
package org.apache.fineract.portfolio.shareproducts.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface ShareProductDividentPayOutDetailsRepository
        extends JpaRepository<ShareProductDividendPayOutDetails, Long>, JpaSpecificationExecutor<ShareProductDividendPayOutDetails> {
}
