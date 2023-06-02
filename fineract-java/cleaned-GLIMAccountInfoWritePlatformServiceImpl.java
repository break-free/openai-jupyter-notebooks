
package org.apache.fineract.portfolio.loanaccount.service;
import java.math.BigDecimal;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.portfolio.group.domain.Group;
import org.apache.fineract.portfolio.loanaccount.domain.GLIMAccountInfoRepository;
import org.apache.fineract.portfolio.loanaccount.domain.GroupLoanIndividualMonitoringAccount;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class GLIMAccountInfoWritePlatformServiceImpl implements GLIMAccountInfoWritePlatformService {
    private final PlatformSecurityContext context;
    private final GLIMAccountInfoRepository glimAccountRepository;
    private final LoanRepository loanRepository;
    @Autowired
    public GLIMAccountInfoWritePlatformServiceImpl(final PlatformSecurityContext context,
            final GLIMAccountInfoRepository glimAccountRepository, final LoanRepository loanRepository) {
        this.context = context;
        this.glimAccountRepository = glimAccountRepository;
        this.loanRepository = loanRepository;
    }
    @Override
    public void addGLIMAccountInfo(String accountNumber, Group group, BigDecimal principalAmount, Long childAccountsCount,
            Boolean isAcceptingChild, Integer loanStatus, BigDecimal applicationId) {
        GroupLoanIndividualMonitoringAccount glimAccountInfo = GroupLoanIndividualMonitoringAccount.getInstance(accountNumber, group,
                principalAmount, childAccountsCount, isAcceptingChild, loanStatus, applicationId);
        this.glimAccountRepository.save(glimAccountInfo);
    }
    @Override
    public void setIsAcceptingChild(GroupLoanIndividualMonitoringAccount glimAccount) {
        glimAccount.setIsAcceptingChild(true);
        glimAccountRepository.save(glimAccount);
    }
    @Override
    public void resetIsAcceptingChild(GroupLoanIndividualMonitoringAccount glimAccount) {
        glimAccount.setIsAcceptingChild(false);
        glimAccountRepository.save(glimAccount);
    }
    @Override
    public void incrementChildAccountCount(GroupLoanIndividualMonitoringAccount glimAccount) {
        long count = glimAccount.getChildAccountsCount();
        glimAccount.setChildAccountsCount(count + 1);
        glimAccountRepository.save(glimAccount);
    }
}
