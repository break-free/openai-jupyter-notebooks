
package org.apache.fineract.accounting.producttoaccountmapping.domain;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface ProductToGLAccountMappingRepository
        extends JpaRepository<ProductToGLAccountMapping, Long>, JpaSpecificationExecutor<ProductToGLAccountMapping> {
    ProductToGLAccountMapping findByProductIdAndProductTypeAndFinancialAccountTypeAndPaymentTypeId(Long productId, int productType,
            int financialAccountType, Long paymentType);
    @Query("select mapping from ProductToGLAccountMapping mapping where mapping.productId= :productId and mapping.productType= :productType and mapping.financialAccountType= :financialAccountType and mapping.charge.id= :chargeId")
    ProductToGLAccountMapping findProductIdAndProductTypeAndFinancialAccountTypeAndChargeId(@Param("productId") Long productId,
            @Param("productType") int productType, @Param("financialAccountType") int financialAccountType,
            @Param("chargeId") Long ChargeId);
    @Query("select mapping from ProductToGLAccountMapping mapping where mapping.productId =:productId and mapping.productType =:productType and mapping.financialAccountType=:financialAccountType and mapping.paymentType is NULL and mapping.charge is NULL")
    ProductToGLAccountMapping findCoreProductToFinAccountMapping(@Param("productId") Long productId, @Param("productType") int productType,
            @Param("financialAccountType") int financialAccountType);
    @Query("select mapping from ProductToGLAccountMapping mapping where mapping.productId =:productId and mapping.productType =:productType and mapping.financialAccountType=1 and mapping.paymentType is not NULL")
    List<ProductToGLAccountMapping> findAllPaymentTypeToFundSourceMappings(@Param("productId") Long productId,
            @Param("productType") int productType);
    @Query("select mapping from ProductToGLAccountMapping mapping where mapping.productId =:productId and mapping.productType =:productType and mapping.financialAccountType=4 and mapping.charge is not NULL")
    List<ProductToGLAccountMapping> findAllFeeToIncomeAccountMappings(@Param("productId") Long productId,
            @Param("productType") int productType);
    @Query("select mapping from ProductToGLAccountMapping mapping where mapping.productId =:productId and mapping.productType =:productType and mapping.financialAccountType=5 and mapping.charge is not NULL")
    List<ProductToGLAccountMapping> findAllPenaltyToIncomeAccountMappings(@Param("productId") Long productId,
            @Param("productType") int productType);
    List<ProductToGLAccountMapping> findByProductIdAndProductType(Long productId, int productType);
}
