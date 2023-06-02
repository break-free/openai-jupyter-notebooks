
package org.apache.fineract.accounting.producttoaccountmapping.service;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.accounting.common.AccountingConstants.CashAccountsForLoan;
import org.apache.fineract.accounting.common.AccountingConstants.LoanProductAccountingParams;
import org.apache.fineract.accounting.glaccount.domain.GLAccount;
import org.apache.fineract.accounting.glaccount.domain.GLAccountRepository;
import org.apache.fineract.accounting.glaccount.domain.GLAccountRepositoryWrapper;
import org.apache.fineract.accounting.glaccount.domain.GLAccountType;
import org.apache.fineract.accounting.producttoaccountmapping.domain.PortfolioProductType;
import org.apache.fineract.accounting.producttoaccountmapping.domain.ProductToGLAccountMapping;
import org.apache.fineract.accounting.producttoaccountmapping.domain.ProductToGLAccountMappingRepository;
import org.apache.fineract.accounting.producttoaccountmapping.exception.ProductToGLAccountMappingInvalidException;
import org.apache.fineract.accounting.producttoaccountmapping.exception.ProductToGLAccountMappingNotFoundException;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.apache.fineract.portfolio.charge.domain.Charge;
import org.apache.fineract.portfolio.charge.domain.ChargeRepositoryWrapper;
import org.apache.fineract.portfolio.paymenttype.domain.PaymentType;
import org.apache.fineract.portfolio.paymenttype.domain.PaymentTypeRepositoryWrapper;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class ProductToGLAccountMappingHelper {
    protected final GLAccountRepository accountRepository;
    protected final ProductToGLAccountMappingRepository accountMappingRepository;
    protected final FromJsonHelper fromApiJsonHelper;
    private final ChargeRepositoryWrapper chargeRepositoryWrapper;
    protected final GLAccountRepositoryWrapper accountRepositoryWrapper;
    private final PaymentTypeRepositoryWrapper paymentTypeRepositoryWrapper;
    public void saveProductToAccountMapping(final JsonElement element, final String paramName, final Long productId,
            final int placeHolderTypeId, final GLAccountType expectedAccountType, final PortfolioProductType portfolioProductType) {
        final Long accountId = this.fromApiJsonHelper.extractLongNamed(paramName, element);
        if (accountId != null) { 
            final GLAccount glAccount = getAccountByIdAndType(paramName, expectedAccountType, accountId);
            final ProductToGLAccountMapping accountMapping = new ProductToGLAccountMapping(glAccount, productId,
                    portfolioProductType.getValue(), placeHolderTypeId);
            this.accountMappingRepository.saveAndFlush(accountMapping);
        }
    }
    public void mergeProductToAccountMappingChanges(final JsonElement element, final String paramName, final Long productId,
            final int accountTypeId, final String accountTypeName, final Map<String, Object> changes,
            final GLAccountType expectedAccountType, final PortfolioProductType portfolioProductType) {
        final Long accountId = this.fromApiJsonHelper.extractLongNamed(paramName, element);
        if (accountId != null) {
            final ProductToGLAccountMapping accountMapping = this.accountMappingRepository.findCoreProductToFinAccountMapping(productId,
                    portfolioProductType.getValue(), accountTypeId);
            if (accountMapping == null) {
                ArrayList<String> optionalProductToGLAccountMappingEntries = new ArrayList<String>();
                optionalProductToGLAccountMappingEntries.add("goodwillCreditAccountId");
                if (optionalProductToGLAccountMappingEntries.contains(paramName)) {
                    saveProductToAccountMapping(element, paramName, productId, accountTypeId, expectedAccountType, portfolioProductType);
                } else {
                    throw new ProductToGLAccountMappingNotFoundException(portfolioProductType, productId, accountTypeName);
                }
            } else {
                if (!accountMapping.getGlAccount().getId().equals(accountId)) {
                    final GLAccount glAccount = getAccountByIdAndType(paramName, expectedAccountType, accountId);
                    changes.put(paramName, accountId);
                    accountMapping.setGlAccount(glAccount);
                    this.accountMappingRepository.saveAndFlush(accountMapping);
                }
            }
        }
    }
    public void createOrmergeProductToAccountMappingChanges(final JsonElement element, final String paramName, final Long productId,
            final int accountTypeId, final Map<String, Object> changes, final GLAccountType expectedAccountType,
            final PortfolioProductType portfolioProductType) {
        final Long accountId = this.fromApiJsonHelper.extractLongNamed(paramName, element);
        if (accountId != null) {
            final ProductToGLAccountMapping accountMapping = this.accountMappingRepository.findCoreProductToFinAccountMapping(productId,
                    portfolioProductType.getValue(), accountTypeId);
            if (accountMapping == null) {
                final GLAccount glAccount = getAccountByIdAndType(paramName, expectedAccountType, accountId);
                changes.put(paramName, accountId);
                ProductToGLAccountMapping newAccountMapping = new ProductToGLAccountMapping(glAccount, productId,
                        portfolioProductType.getValue(), accountTypeId);
                this.accountMappingRepository.saveAndFlush(newAccountMapping);
            } else if (!accountMapping.getGlAccount().getId().equals(accountId)) {
                final GLAccount glAccount = getAccountByIdAndType(paramName, expectedAccountType, accountId);
                changes.put(paramName, accountId);
                accountMapping.setGlAccount(glAccount);
                this.accountMappingRepository.saveAndFlush(accountMapping);
            }
        }
    }
    public void savePaymentChannelToFundSourceMappings(final JsonCommand command, final JsonElement element, final Long productId,
            final Map<String, Object> changes, final PortfolioProductType portfolioProductType) {
        final JsonArray paymentChannelMappingArray = this.fromApiJsonHelper
                .extractJsonArrayNamed(LoanProductAccountingParams.PAYMENT_CHANNEL_FUND_SOURCE_MAPPING.getValue(), element);
        if (paymentChannelMappingArray != null) {
            if (changes != null) {
                changes.put(LoanProductAccountingParams.PAYMENT_CHANNEL_FUND_SOURCE_MAPPING.getValue(),
                        command.jsonFragment(LoanProductAccountingParams.PAYMENT_CHANNEL_FUND_SOURCE_MAPPING.getValue()));
            }
            for (int i = 0; i < paymentChannelMappingArray.size(); i++) {
                final JsonObject jsonObject = paymentChannelMappingArray.get(i).getAsJsonObject();
                final Long paymentTypeId = jsonObject.get(LoanProductAccountingParams.PAYMENT_TYPE.getValue()).getAsLong();
                final Long paymentSpecificFundAccountId = jsonObject.get(LoanProductAccountingParams.FUND_SOURCE.getValue()).getAsLong();
                savePaymentChannelToFundSourceMapping(productId, paymentTypeId, paymentSpecificFundAccountId, portfolioProductType);
            }
        }
    }
    public void saveChargesToGLAccountMappings(final JsonCommand command, final JsonElement element, final Long productId,
            final Map<String, Object> changes, final PortfolioProductType portfolioProductType, final boolean isPenalty) {
        String arrayName;
        if (isPenalty) {
            arrayName = LoanProductAccountingParams.PENALTY_INCOME_ACCOUNT_MAPPING.getValue();
        } else {
            arrayName = LoanProductAccountingParams.FEE_INCOME_ACCOUNT_MAPPING.getValue();
        }
        final JsonArray chargeToIncomeAccountMappingArray = this.fromApiJsonHelper.extractJsonArrayNamed(arrayName, element);
        if (chargeToIncomeAccountMappingArray != null) {
            if (changes != null) {
                changes.put(LoanProductAccountingParams.FEE_INCOME_ACCOUNT_MAPPING.getValue(),
                        command.jsonFragment(LoanProductAccountingParams.FEE_INCOME_ACCOUNT_MAPPING.getValue()));
            }
            for (int i = 0; i < chargeToIncomeAccountMappingArray.size(); i++) {
                final JsonObject jsonObject = chargeToIncomeAccountMappingArray.get(i).getAsJsonObject();
                final Long chargeId = jsonObject.get(LoanProductAccountingParams.CHARGE_ID.getValue()).getAsLong();
                final Long incomeAccountId = jsonObject.get(LoanProductAccountingParams.INCOME_ACCOUNT_ID.getValue()).getAsLong();
                saveChargeToFundSourceMapping(productId, chargeId, incomeAccountId, portfolioProductType, isPenalty);
            }
        }
    }
    public void updateChargeToIncomeAccountMappings(final JsonCommand command, final JsonElement element, final Long productId,
            final Map<String, Object> changes, final PortfolioProductType portfolioProductType, final boolean isPenalty) {
        List<ProductToGLAccountMapping> existingChargeToIncomeAccountMappings;
        String arrayFragmentName;
        if (isPenalty) {
            existingChargeToIncomeAccountMappings = this.accountMappingRepository.findAllPenaltyToIncomeAccountMappings(productId,
                    portfolioProductType.getValue());
            arrayFragmentName = LoanProductAccountingParams.PENALTY_INCOME_ACCOUNT_MAPPING.getValue();
        } else {
            existingChargeToIncomeAccountMappings = this.accountMappingRepository.findAllFeeToIncomeAccountMappings(productId,
                    portfolioProductType.getValue());
            arrayFragmentName = LoanProductAccountingParams.FEE_INCOME_ACCOUNT_MAPPING.getValue();
        }
        final JsonArray chargeToIncomeAccountMappingArray = this.fromApiJsonHelper.extractJsonArrayNamed(arrayFragmentName, element);
        final Map<Long, Long> inputChargeToIncomeAccountMap = new HashMap<>();
        final Set<Long> existingCharges = new HashSet<>();
        if (chargeToIncomeAccountMappingArray != null) {
            if (changes != null) {
                changes.put(arrayFragmentName, command.jsonFragment(arrayFragmentName));
            }
            for (int i = 0; i < chargeToIncomeAccountMappingArray.size(); i++) {
                final JsonObject jsonObject = chargeToIncomeAccountMappingArray.get(i).getAsJsonObject();
                final Long chargeId = jsonObject.get(LoanProductAccountingParams.CHARGE_ID.getValue()).getAsLong();
                final Long incomeAccountId = jsonObject.get(LoanProductAccountingParams.INCOME_ACCOUNT_ID.getValue()).getAsLong();
                inputChargeToIncomeAccountMap.put(chargeId, incomeAccountId);
            }
            if (inputChargeToIncomeAccountMap.size() == 0) {
                this.accountMappingRepository.deleteAllInBatch(existingChargeToIncomeAccountMappings);
            } 
            else {
                for (final ProductToGLAccountMapping chargeToIncomeAccountMapping : existingChargeToIncomeAccountMappings) {
                    final Long currentCharge = chargeToIncomeAccountMapping.getCharge().getId();
                    existingCharges.add(currentCharge);
                    if (inputChargeToIncomeAccountMap.containsKey(currentCharge)) {
                        final Long newGLAccountId = inputChargeToIncomeAccountMap.get(currentCharge);
                        if (!newGLAccountId.equals(chargeToIncomeAccountMapping.getGlAccount().getId())) {
                            final GLAccount glAccount;
                            if (isPenalty) {
                                glAccount = getAccountByIdAndType(LoanProductAccountingParams.INCOME_ACCOUNT_ID.getValue(),
                                        GLAccountType.INCOME, newGLAccountId);
                            } else {
                                List<GLAccountType> allowedAccountTypes = getAllowedAccountTypesForFeeMapping();
                                glAccount = getAccountByIdAndType(LoanProductAccountingParams.INCOME_ACCOUNT_ID.getValue(),
                                        allowedAccountTypes, newGLAccountId);
                            }
                            chargeToIncomeAccountMapping.setGlAccount(glAccount);
                            this.accountMappingRepository.saveAndFlush(chargeToIncomeAccountMapping);
                        }
                    } 
                    else {
                        this.accountMappingRepository.delete(chargeToIncomeAccountMapping);
                    }
                }
                final Set<Long> incomingCharges = inputChargeToIncomeAccountMap.keySet();
                incomingCharges.removeAll(existingCharges);
                for (final Long newCharge : incomingCharges) {
                    final Long newGLAccountId = inputChargeToIncomeAccountMap.get(newCharge);
                    saveChargeToFundSourceMapping(productId, newCharge, newGLAccountId, portfolioProductType, isPenalty);
                }
            }
        }
    }
    public void updatePaymentChannelToFundSourceMappings(final JsonCommand command, final JsonElement element, final Long productId,
            final Map<String, Object> changes, final PortfolioProductType portfolioProductType) {
        final List<ProductToGLAccountMapping> existingPaymentChannelToFundSourceMappings = this.accountMappingRepository
                .findAllPaymentTypeToFundSourceMappings(productId, portfolioProductType.getValue());
        final JsonArray paymentChannelMappingArray = this.fromApiJsonHelper
                .extractJsonArrayNamed(LoanProductAccountingParams.PAYMENT_CHANNEL_FUND_SOURCE_MAPPING.getValue(), element);
        final Map<Long, Long> inputPaymentChannelFundSourceMap = new HashMap<>();
        final Set<Long> existingPaymentTypes = new HashSet<>();
        if (paymentChannelMappingArray != null) {
            if (changes != null) {
                changes.put(LoanProductAccountingParams.PAYMENT_CHANNEL_FUND_SOURCE_MAPPING.getValue(),
                        command.jsonFragment(LoanProductAccountingParams.PAYMENT_CHANNEL_FUND_SOURCE_MAPPING.getValue()));
            }
            for (int i = 0; i < paymentChannelMappingArray.size(); i++) {
                final JsonObject jsonObject = paymentChannelMappingArray.get(i).getAsJsonObject();
                final Long paymentTypeId = jsonObject.get(LoanProductAccountingParams.PAYMENT_TYPE.getValue()).getAsLong();
                final Long paymentSpecificFundAccountId = jsonObject.get(LoanProductAccountingParams.FUND_SOURCE.getValue()).getAsLong();
                inputPaymentChannelFundSourceMap.put(paymentTypeId, paymentSpecificFundAccountId);
            }
            if (inputPaymentChannelFundSourceMap.size() == 0) {
                this.accountMappingRepository.deleteAllInBatch(existingPaymentChannelToFundSourceMappings);
            } 
            else {
                for (final ProductToGLAccountMapping existingPaymentChannelToFundSourceMapping : existingPaymentChannelToFundSourceMappings) {
                    final Long currentPaymentChannelId = existingPaymentChannelToFundSourceMapping.getPaymentType().getId();
                    existingPaymentTypes.add(currentPaymentChannelId);
                    if (inputPaymentChannelFundSourceMap.containsKey(currentPaymentChannelId)) {
                        final Long newGLAccountId = inputPaymentChannelFundSourceMap.get(currentPaymentChannelId);
                        if (!newGLAccountId.equals(existingPaymentChannelToFundSourceMapping.getGlAccount().getId())) {
                            final GLAccount glAccount = getAccountById(LoanProductAccountingParams.FUND_SOURCE.getValue(), newGLAccountId);
                            existingPaymentChannelToFundSourceMapping.setGlAccount(glAccount);
                            this.accountMappingRepository.saveAndFlush(existingPaymentChannelToFundSourceMapping);
                        }
                    } 
                    else {
                        this.accountMappingRepository.delete(existingPaymentChannelToFundSourceMapping);
                    }
                }
                final Set<Long> incomingPaymentTypes = inputPaymentChannelFundSourceMap.keySet();
                incomingPaymentTypes.removeAll(existingPaymentTypes);
                for (final Long newPaymentType : incomingPaymentTypes) {
                    final Long newGLAccountId = inputPaymentChannelFundSourceMap.get(newPaymentType);
                    savePaymentChannelToFundSourceMapping(productId, newPaymentType, newGLAccountId, portfolioProductType);
                }
            }
        }
    }
    private void savePaymentChannelToFundSourceMapping(final Long productId, final Long paymentTypeId,
            final Long paymentTypeSpecificFundAccountId, final PortfolioProductType portfolioProductType) {
        final PaymentType paymentType = this.paymentTypeRepositoryWrapper.findOneWithNotFoundDetection(paymentTypeId);
        final GLAccount glAccount = getAccountById(LoanProductAccountingParams.FUND_SOURCE.getValue(), paymentTypeSpecificFundAccountId);
        final ProductToGLAccountMapping accountMapping = new ProductToGLAccountMapping(glAccount, productId,
                portfolioProductType.getValue(), CashAccountsForLoan.FUND_SOURCE.getValue(), paymentType);
        this.accountMappingRepository.saveAndFlush(accountMapping);
    }
    private void saveChargeToFundSourceMapping(final Long productId, final Long chargeId, final Long incomeAccountId,
            final PortfolioProductType portfolioProductType, final boolean isPenalty) {
        final Charge charge = this.chargeRepositoryWrapper.findOneWithNotFoundDetection(chargeId);
        GLAccount glAccount;
        CashAccountsForLoan placeHolderAccountType;
        if (isPenalty) {
            glAccount = getAccountByIdAndType(LoanProductAccountingParams.INCOME_ACCOUNT_ID.getValue(), GLAccountType.INCOME,
                    incomeAccountId);
            placeHolderAccountType = CashAccountsForLoan.INCOME_FROM_PENALTIES;
        } else {
            List<GLAccountType> allowedAccountTypes = getAllowedAccountTypesForFeeMapping();
            glAccount = getAccountByIdAndType(LoanProductAccountingParams.INCOME_ACCOUNT_ID.getValue(), allowedAccountTypes,
                    incomeAccountId);
            placeHolderAccountType = CashAccountsForLoan.INCOME_FROM_FEES;
        }
        final ProductToGLAccountMapping accountMapping = new ProductToGLAccountMapping(glAccount, productId,
                portfolioProductType.getValue(), placeHolderAccountType.getValue(), charge);
        this.accountMappingRepository.saveAndFlush(accountMapping);
    }
    private List<GLAccountType> getAllowedAccountTypesForFeeMapping() {
        List<GLAccountType> allowedAccountTypes = new ArrayList<>();
        allowedAccountTypes.add(GLAccountType.INCOME);
        allowedAccountTypes.add(GLAccountType.LIABILITY);
        return allowedAccountTypes;
    }
    public GLAccount getAccountByIdAndType(final String paramName, final GLAccountType expectedAccountType, final Long accountId) {
        final GLAccount glAccount = this.accountRepositoryWrapper.findOneWithNotFoundDetection(accountId);
        if (glAccount.getType().intValue() != expectedAccountType.getValue()) {
            throw new ProductToGLAccountMappingInvalidException(paramName, glAccount.getName(), accountId,
                    GLAccountType.fromInt(glAccount.getType()).toString(), expectedAccountType.toString());
        }
        return glAccount;
    }
    public GLAccount getAccountById(final String paramName, final Long accountId) {
        final GLAccount glAccount = this.accountRepositoryWrapper.findOneWithNotFoundDetection(accountId);
        return glAccount;
    }
    public GLAccount getAccountByIdAndType(final String paramName, final List<GLAccountType> expectedAccountTypes, final Long accountId) {
        final GLAccount glAccount = this.accountRepositoryWrapper.findOneWithNotFoundDetection(accountId);
        List<Integer> glAccountTypeValues = new ArrayList<>();
        for (GLAccountType glAccountType : expectedAccountTypes) {
            glAccountTypeValues.add(glAccountType.getValue());
        }
        if (!glAccountTypeValues.contains(glAccount.getType())) {
            throw new ProductToGLAccountMappingInvalidException(paramName, glAccount.getName(), accountId,
                    GLAccountType.fromInt(glAccount.getType()).toString(), glAccountTypeValues.toString());
        }
        return glAccount;
    }
    public void deleteProductToGLAccountMapping(final Long loanProductId, final PortfolioProductType portfolioProductType) {
        final List<ProductToGLAccountMapping> productToGLAccountMappings = this.accountMappingRepository
                .findByProductIdAndProductType(loanProductId, portfolioProductType.getValue());
        if (productToGLAccountMappings != null && productToGLAccountMappings.size() > 0) {
            this.accountMappingRepository.deleteAllInBatch(productToGLAccountMappings);
        }
    }
}
