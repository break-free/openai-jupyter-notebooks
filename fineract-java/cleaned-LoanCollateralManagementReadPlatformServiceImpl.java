
package org.apache.fineract.portfolio.collateralmanagement.service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.portfolio.collateralmanagement.data.LoanCollateralResponseData;
import org.apache.fineract.portfolio.collateralmanagement.domain.CollateralManagementDomain;
import org.apache.fineract.portfolio.collateralmanagement.exception.LoanCollateralManagementNotFoundException;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.apache.fineract.portfolio.loanaccount.domain.LoanCollateralManagement;
import org.apache.fineract.portfolio.loanaccount.domain.LoanCollateralManagementRepository;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepository;
import org.apache.fineract.portfolio.loanaccount.exception.LoanNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional(readOnly = true)
public class LoanCollateralManagementReadPlatformServiceImpl implements LoanCollateralManagementReadPlatformService {
    private final PlatformSecurityContext context;
    private LoanCollateralManagementRepository loanCollateralManagementRepository;
    private LoanRepository loanRepository;
    @Autowired
    public LoanCollateralManagementReadPlatformServiceImpl(final PlatformSecurityContext context,
            final LoanCollateralManagementRepository loanCollateralManagementRepository, final LoanRepository loanRepository) {
        this.context = context;
        this.loanCollateralManagementRepository = loanCollateralManagementRepository;
        this.loanRepository = loanRepository;
    }
    @Override
    public List<LoanCollateralManagement> getLoanCollaterals(Long loanId) {
        this.context.authenticatedUser();
        Loan loan = this.loanRepository.findById(loanId).orElseThrow(() -> new LoanNotFoundException(loanId));
        return this.loanCollateralManagementRepository.findByLoan(loan);
    }
    @Override
    public LoanCollateralResponseData getLoanCollateralResponseData(Long collateralId) {
        this.context.authenticatedUser();
        LoanCollateralManagement loanCollateralManagement = this.loanCollateralManagementRepository.findById(collateralId)
                .orElseThrow(() -> new LoanCollateralManagementNotFoundException(collateralId));
        final CollateralManagementDomain collateralManagementDomain = loanCollateralManagement.getClientCollateralManagement()
                .getCollaterals();
        BigDecimal quantity = loanCollateralManagement.getQuantity();
        BigDecimal total = quantity.multiply(collateralManagementDomain.getBasePrice());
        BigDecimal totalCollateral = total.multiply(collateralManagementDomain.getPctToBase()).divide(BigDecimal.valueOf(100));
        return LoanCollateralResponseData.instanceOf(loanCollateralManagement, total, totalCollateral);
    }
    @Override
    public List<LoanCollateralResponseData> getLoanCollateralResponseDataList(Long loanId) {
        this.context.authenticatedUser();
        Loan loan = this.loanRepository.findById(loanId).orElseThrow(() -> new LoanNotFoundException(loanId));
        List<LoanCollateralResponseData> loanCollateralResponseDataCollection = new ArrayList<>();
        Set<LoanCollateralManagement> loanCollateralManagements = loan.getLoanCollateralManagements();
        for (LoanCollateralManagement loanCollateralManagement : loanCollateralManagements) {
            final CollateralManagementDomain collateralManagementDomain = loanCollateralManagement.getClientCollateralManagement()
                    .getCollaterals();
            BigDecimal quantity = loanCollateralManagement.getQuantity();
            BigDecimal total = quantity.multiply(collateralManagementDomain.getBasePrice());
            BigDecimal totalCollateral = total.multiply(collateralManagementDomain.getPctToBase()).divide(BigDecimal.valueOf(100));
            loanCollateralResponseDataCollection
                    .add(LoanCollateralResponseData.instanceOf(loanCollateralManagement, total, totalCollateral));
        }
        return loanCollateralResponseDataCollection;
    }
}
