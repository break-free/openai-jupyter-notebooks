
package org.apache.fineract.accounting.producttoaccountmapping.service;
import java.util.List;
import java.util.Map;
import org.apache.fineract.accounting.producttoaccountmapping.data.ChargeToGLAccountMapper;
import org.apache.fineract.accounting.producttoaccountmapping.data.PaymentTypeToGLAccountMapper;
public interface ProductToGLAccountMappingReadPlatformService {
    Map<String, Object> fetchAccountMappingDetailsForLoanProduct(Long loanProductId, Integer accountingType);
    List<PaymentTypeToGLAccountMapper> fetchPaymentTypeToFundSourceMappingsForLoanProduct(Long loanProductId);
    List<ChargeToGLAccountMapper> fetchFeeToGLAccountMappingsForLoanProduct(Long loanProductId);
    List<ChargeToGLAccountMapper> fetchPenaltyToIncomeAccountMappingsForLoanProduct(Long loanProductId);
    Map<String, Object> fetchAccountMappingDetailsForSavingsProduct(Long savingsProductId, Integer accountingType);
    List<PaymentTypeToGLAccountMapper> fetchPaymentTypeToFundSourceMappingsForSavingsProduct(Long savingsProductId);
    List<ChargeToGLAccountMapper> fetchFeeToIncomeAccountMappingsForSavingsProduct(Long savingsProductId);
    List<ChargeToGLAccountMapper> fetchPenaltyToIncomeAccountMappingsForSavingsProduct(Long savingsProductId);
    Map<String, Object> fetchAccountMappingDetailsForShareProduct(Long productId, Integer accountingType);
    List<PaymentTypeToGLAccountMapper> fetchPaymentTypeToFundSourceMappingsForShareProduct(Long productId);
    List<ChargeToGLAccountMapper> fetchFeeToIncomeAccountMappingsForShareProduct(Long productId);
}
